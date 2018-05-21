package WeatherApp.model;

import java.time.Instant;
import java.util.List;

/**
 * A representation of the Weather of a location, whether current or a predicted forecast, including the time it is
 * relevant to.
 *
 * A wide range of parameters are exposed, with all expected to be available aside from rainfall information, which is
 * optional.
 *
 * Primarily, Weather objects will be created by the AgroAPI service but will be accessed from and stored in an
 * AgroStore instance.
 *
 * This class is derived from the weather response at https://www.openweathermap.org/current, as distributed by
 * OpenWeatherMap.org under the Creative Commons Attribution-ShareAlike 4.0 Generic License. This license is included in
 * full at resources/agro-api-icons/LICENSE.txt.
 */
public class Weather {

    /**
     * The time for which the data represents or was forecast to represent.
     */
    private final Instant when;

    /**
     * Air temperature in Kelvin.
     */
    private final double temperature;

    /**
     * Air humidity, as a percentage.
     */
    private final int humidity;

    /**
     * Wind speed, in meters/second.
     */
    private final double windSpeed;

    /**
     * Wind direction, in meteorological degrees.
     */
    private final double windDirection;

    /**
     * Rainfall over the past 3 hours, as a volume in mm.
     *
     * OPTIONAL - this isn't always returned by the API, so we'll store NaN if that's the case
     */
    private final double rainfall;

    /**
     * The Condition of the weather, as decoded from the ID provided in the API response.
     *
     * This is useful for determining the icon shown and providing textual descriptions.
     */
    private final List<Condition> conditions;

    public Weather(Instant when, double temp, int hum, double wSpeed, double wDir, List<Condition> conds) {
        this(when, temp, hum, wSpeed, wDir, conds, Double.NaN);
    }

    public Weather(Instant when, double temp, int hum, double wSpeed, double wDir, List<Condition> conds, double rainfall) {
        this.when = when;
        this.temperature = temp;
        this.humidity = hum;
        this.windSpeed = wSpeed;
        this.windDirection = wDir;
        this.conditions = conds;
        this.rainfall = rainfall;
    }

    public Instant getWhen() {
        return when;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public double getRainfall() {
        return rainfall;
    }

}
