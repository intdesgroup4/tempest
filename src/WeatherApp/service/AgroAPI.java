package WeatherApp.service;

import WeatherApp.model.Soil;
import WeatherApp.model.Weather;

import java.util.List;

/**
 * Make calls to the Agro API (https://www.agromonitoring.com/), with Current weather, Forecast weather and Current soil
 * data to be implemented.
 *
 * Notes:
 * - Caching will not be implemented here; these methods will primarily be used by AgroStore, which will handle this.
 * - Exceptions are likely to be added to method signatures as behaviour is implemented
 *
 * TODO return sample data
 * TODO talk to the API
 * TODO work out a nice way of storing the API key - maybe use the resources folder with .gitignore'd file?
 */
public class AgroAPI {

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
    public Weather getCurrentWeather(double lat, double lng) {
        throw new UnsupportedOperationException("Not implemented yet");
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
    public List<Weather> getForecastWeather(double lat, double lng) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Determines the current soil data of a location from AgroAPI.
     *
     * See https://www.agromonitoring.com/api/current-soil.
     * @param lat
     * @param lng
     * @return
     */
    public Soil getCurrentSoil(double lat, double lng) {
        throw new UnsupportedOperationException("Not implemented yet");
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

}
