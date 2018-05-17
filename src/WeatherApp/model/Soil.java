package WeatherApp.model;

import java.time.Instant;

/**
 * A representation of the Soil of a location, including the time it is relevant to.
 *
 * Primarily, Soil objects will be created by the AgroAPI service but will be accessed from and stored in an AgroStore
 * instance.
 *
 * This class is derived from the soil response at https://agromonitoring.com/api/current-soil, as distributed by
 * agromonitoring.com under the Creative Commons Attribution-ShareAlike 4.0 Generic License. This license is included in
 * full at resources/agro-api-icons/LICENSE.txt.
 */
public class Soil {

    /**
     * The time for which the data represents.
     *
     * TODO should this be decoupled?
     */
    private final Instant when;

    /**
     * Temperature on the surface, in Kelvin.
     */
    private final double surfaceTemp;

    /**
     * Temperature on the 10cm depth, in Kelvin.
     */
    private final double undergroundTemp;

    /**
     * Soil moisture - this is likely as a proportion, though this is yet to be confirmed.
     */
    private final double moisture;

    public Soil(Instant when, double surfaceTemp, double undergroundTemp, double moisture) {
        this.when = when;
        this.surfaceTemp = surfaceTemp;
        this.undergroundTemp = undergroundTemp;
        this.moisture = moisture;
    }

    public Instant getWhen() {
        return when;
    }

    public double getSurfaceTemp() {
        return surfaceTemp;
    }

    public double getUndergroundTemp() {
        return undergroundTemp;
    }

    public double getMoisture() {
        return moisture;
    }

}
