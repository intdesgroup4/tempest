package WeatherApp.service;

import WeatherApp.model.Condition;
import WeatherApp.model.Soil;
import WeatherApp.model.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Make calls to the Agro API (https://www.agromonitoring.com/), with Current weather, Forecast weather and Current soil
 * data to be implemented.
 *
 * Note, caching is not implemented here; these methods will primarily be used by AgroStore, which will handle this.
 *
 * TODO work out a nice way of storing the API key - maybe use the resources folder with .gitignore'd file?
 */
public class AgroAPI {

    /**
     * The endpoint to request JSON from. Task (e.g. weather, weather/forecast, soil) must be substituted in, before
     * the api key, latitude and then longitude.
     */
    public static final String ENDPOINT = "http://api.agromonitoring.com/agro/1.0/%s?appid=%s&lat=%s&lon=%s";

    /**
     * The API key this client should use to authenticate with the API.
     */
    private final String apiKey;

    public AgroAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Determines the current weather of a location from AgroAPI.
     *
     * See https://www.agromonitoring.com/api/current-weather.
     *
     * @param lat
     * @param lng
     * @return
     */
    public Weather getCurrentWeather(double lat, double lng) throws IOException {
        return parseApiWeather(request("weather", lat, lng).getAsJsonObject());
    }

    /**
     * Determines the forecasted weather of a location from AgroAPI.
     *
     * Times are provided in the user's local timezone, as converted from the UTC the API returns.
     *
     * See https://www.agromonitoring.com/api/forecast-weather.
     *
     * @param lat
     * @param lng
     * @return
     */
    public List<Weather> getForecastWeather(double lat, double lng) throws IOException {
        JsonArray jarray = request("weather/forecast", lat, lng).getAsJsonArray();

        List<Weather> forecasts = new ArrayList<>();
        for (JsonElement jelement : jarray) {
            forecasts.add(parseApiWeather(jelement.getAsJsonObject()));
        }

        return forecasts;
    }

    /**
     * Determines the current soil data of a locationweather/forecast from AgroAPI.
     *
     * See https://www.agromonitoring.com/api/current-soil.
     * @param lat
     * @param lng
     * @return
     */
    public Soil getCurrentSoil(double lat, double lng) throws IOException {
        JsonObject json = request("soil", lat, lng).getAsJsonObject();

        Instant when = Instant.ofEpochSecond(json.get("dt").getAsLong());
        double surfaceTemp = json.get("t0").getAsDouble();
        double undergroundTemp = json.get("t10").getAsDouble();
        double moisture = json.get("moisture").getAsDouble();

        return new Soil(when, surfaceTemp, undergroundTemp, moisture);
    }

    /**
     * Get whether the API may be accessed or not at this time, in these conditions.
     *
     * This may be useful for other classes that wish to fall back on cached data when offline.
     *
     * @return true if Online, else false
     */
    public boolean isOnline() {
        // TODO work out a sane way to determine or otherwise emulate this
        return true;
    }

    /**
     * Make a request to the endpoint.
     *
     * @param task The sort of request we are asking (e.g. weather, weather/forecast)
     * @param lat The latitude
     * @param lng The longitude
     * @return A JsonObject representing the returned Json
     * @throws IOException
     */
    private JsonElement request(String task, double lat, double lng) throws IOException {
        URL url = new URL(String.format(ENDPOINT, task, apiKey, lat, lng));
        URLConnection conn = url.openConnection();
        return new JsonParser().parse(new InputStreamReader(conn.getInputStream()));
    }

    /**
     * Create a Weather object from its representation in an API response.
     *
     * @param json
     * @return
     */
    private Weather parseApiWeather(JsonObject json) {
        Instant when = Instant.ofEpochSecond(json.get("dt").getAsLong());
        double temp = json.getAsJsonObject("main").get("temp").getAsDouble();
        int hum  = json.getAsJsonObject("main").get("humidity").getAsInt();
        double wSpeed = json.getAsJsonObject("wind").get("speed").getAsDouble();
        double wDir = json.getAsJsonObject("wind").get("deg").getAsDouble();

        List<Condition> conds = new ArrayList<>();
        for (JsonElement jelement : json.getAsJsonArray("weather")) {
            conds.add(Condition.fromId(jelement.getAsJsonObject().get("id").getAsInt()));
        }

        if (!json.has("rain") || !json.getAsJsonObject("rain").has("3h")) {
            return new Weather(when, temp, hum, wSpeed, wDir, conds);
        }

        double rainfall = json.getAsJsonObject("rain").get("3h").getAsDouble();
        return new Weather(when, temp, hum, wSpeed, wDir, conds, rainfall);
    }

}
