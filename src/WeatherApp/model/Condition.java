package WeatherApp.model;

import javafx.scene.image.Image;

import java.io.File;

/**
 * Represents the condition in an API response describing weather.
 *
 * The matching of ids to the relevant textual descriptions and icons are derived from
 * https://www.openweathermap.org/weather-conditions, as distributed by OpenWeatherMap.org under the Creative Commons
 * Attribution-ShareAlike 4.0 Generic License. This license is included in full at resources/agro-api-icons/LICENSE.txt.
 *
 * This should aid depiction of the weather condition in the UI.
 */
public enum Condition {

    THUNDERSTORM_WITH_LIGHT_RAIN(200, "thunderstorm with light rain", "11d"),
    THUNDERSTORM_WITH_RAIN(201, "thunderstorm with rain", "11d"),
    THUNDERSTORM_WITH_HEAVY_RAIN(202, "thunderstorm with heavy rain", "11d"),
    LIGHT_THUNDERSTORM(210, "light thunderstorm", "11d"),
    THUNDERSTORM(211, "thunderstorm", "11d"),
    HEAVY_THUNDERSTORM(212, "heavy thunderstorm", "11d"),
    RAGGED_THUNDERSTORM(221, "ragged thunderstorm", "11d"),
    THUNDERSTORM_WITH_LIGHT_DRIZZLE(230, "thunderstorm with light drizzle", "11d"),
    THUNDERSTORM_WITH_DRIZZLE(231, "thunderstorm with drizzle", "11d"),
    THUNDERSTORM_WITH_HEAVY_DRIZZLE(232, "thunderstorm with heavy drizzle", "11d"),
    LIGHT_INTENSITY_DRIZZLE(300, "light intensity drizzle", "09d"),
    DRIZZLE(301, "drizzle", "09d"),
    HEAVY_INTENSITY_DRIZZLE(302, "heavy intensity drizzle", "09d"),
    LIGHT_INTENSITY_DRIZZLE_RAIN(310, "light intensity drizzle rain", "09d"),
    DRIZZLE_RAIN(311, "drizzle rain", "09d"),
    HEAVY_INTENSITY_DRIZZLE_RAIN(312, "heavy intensity drizzle rain", "09d"),
    SHOWER_RAIN_AND_DRIZZLE(313, "shower rain and drizzle", "09d"),
    HEAVY_SHOWER_RAIN_AND_DRIZZLE(314, "heavy shower rain and drizzle", "09d"),
    SHOWER_DRIZZLE(321, "shower drizzle", "09d"),
    LIGHT_RAIN(500, "light rain", "10d"),
    MODERATE_RAIN(501, "moderate rain", "10d"),
    HEAVY_INTENSITY_RAIN(502, "heavy intensity rain", "10d"),
    VERY_HEAVY_RAIN(503, "very heavy rain", "10d"),
    EXTREME_RAIN(504, "extreme rain", "10d"),
    FREEZING_RAIN(511, "freezing rain", "13d"),
    LIGHT_INTENSITY_SHOWER_RAIN(520, "light intensity shower rain", "09d" ),
    SHOWER_RAIN(521, "shower rain", "09d"),
    HEAVY_INTENSITY_SHOWER_RAIN(522, "heavy intensity shower rain", "09d"),
    RAGGED_SHOWER_RAIN(531, "ragged shower rain", "09d"),
    LIGHT_SNOW(600, "light snow", "13d"),
    SNOW(601, "snow", "13d"),
    HEAVY_SNOW(602, "heavy snow", "13d"),
    SLEET(611, "sleet", "13d"),
    SHOWER_SLEET(612, "shower sleet", "13d"),
    LIGHT_RAIN_AND_SNOW(615, "light rain and snow", "13d"),
    RAIN_AND_SNOW(616, "rain and snow", "13d"),
    LIGHT_SHOWER_SNOW(620, "light shower snow", "13d"),
    SHOWER_SNOW(621, "shower snow", "13d"),
    HEAVY_SHOWER_SNOW(622, "heavy shower snow", "13d"),
    MIST(701, "mist", "50d"),
    SMOKE(711, "smoke", "50d"),
    HAZE(721, "haze", "50d"),
    SAND_DUST_WHIRLS(731, "sand, dust whirls", "50d"),
    FOG(741, "fog", "50d"),
    SAND(751, "sand", "50d"),
    DUST(761, "dust", "50d"),
    VOLCANIC_ASH(762, "volcanic ash", "50d"),
    SQUALLS(771, "squalls", "50d"),
    TORNADO(781, "tornado", "50d"),
    CLEAR_SKY(800, "clear sky", "01d", "01n"),
    FEW_CLOUDS(801, "few clouds", "02d", "02n"),
    SCATTERED_CLOUDS(802, "scattered clouds", "03d", "03n"),
    BROKEN_CLOUDS(803, "broken clouds", "04d", "04n"),
    OVERCAST_CLOUDS(804, "overcast clouds", "04d", "04n");

    Condition(int id, String description, String dayIconPath) {
        this(id, description, dayIconPath, null);
    }

    /**
     * See field docs for detailed parameter descriptions.
     *
     * @param id
     * @param description
     * @param dayIconPath
     * @param nightIconPath This is optional; if null is provided, the day icon will be used for both day and night.
     */
    Condition(int id, String description, String dayIconPath, String nightIconPath) {
        this.id = id;
        this.description = description;

        // load icons from name
        this.dayIcon = loadIcon(dayIconPath);
        this.nightIcon = nightIconPath == null ? dayIcon : loadIcon(nightIconPath);
    }

    /**
     * The id, as represented by the API.
     */
    private final int id;

    /**
     * A human-readable description.
     */
    private final String description;

    /**
     * The day, fetched from the API.
     */
    private final Image dayIcon;

    /**
     * The night, fetched from the API.
     */
    private final Image nightIcon;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Image getDayIcon() {
        return dayIcon;
    }

    public Image getNightIcon() {
        return nightIcon;
    }

    /**
     * Utility function for loading an icon's Image from its id from the JAR's resources.
     *
     * @param name The name of the file, not including extension or hierarchical position
     * @return The Image represented by the icon of the provided name
     */
    private static Image loadIcon(String name) {
        return new Image(String.format(File.pathSeparator + "agro-api-icons" + File.pathSeparator + "%s.png", name));
    }

    /**
     * A helper function to perform the potentially common operation of getting a Condition by its id.
     *
     * @param id The id of the Condition.
     * @return The corresponding Condition for provided id, else null if one is not found.
     */
    public static Condition fromId(int id) {
        for (Condition cond : Condition.values()) {
            if (cond.getId() == id) {
                return cond;
            }
        }

        return null;
    }

}
