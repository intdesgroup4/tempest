package WeatherApp.service;

import WeatherApp.model.Field;
import WeatherApp.model.Soil;
import WeatherApp.model.Weather;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Access API data on a given Field where available, online or offline.
 *
 * This class handles:
 * - Caching API requests
 * - Persistence of Field objects (see Store for more info)
 * ... and should be the main way of accessing weather data of Fields throughout UI code.
 *
 * TODO consider storing times here instead of with the actual weather data classes
 * TODO write gson code to serialise/deserialise
 * TODO find a way to garbage collect irrelevant data (e.g. deleted Fields, past weather data)
 */
public class AgroStore extends Store {

    /**
     * The amount of time to wait before desiring new data from the API.
     */
    public static final TemporalAmount CACHE_TIME = Duration.ofMinutes(1);

    /**
     * Time after which data is considered too old to be of use.
     */
    public static final TemporalAmount EXPIRY = Duration.ofHours(3);

    /**
     * Agro API instance to fetch data from.
     */
    private final AgroAPI api;

    /**
     * The Instant at which the API was last accessed.
     */
    private final Map<String, Instant> lastFetched = new HashMap<>();

    /**
     * The weathers known and forecasted for a Field; known has index 0.
     */
    private final Map<String, List<Weather>> weathers = new HashMap<>();

    /**
     * The soils known for a Field.
     */
    private final Map<String, Soil> soils = new HashMap<>();

    public AgroStore(Path path, AgroAPI api) {
        super(path);

        this.api = api;
    }

    /**
     * Get the current Weather at a field.
     *
     * Data is guaranteed to be based on actual, not forecasted, data and shall be no older than EXPIRY.
     *
     * @param field
     * @return null if no data available, else the current Weather
     */
    public Weather getCurrentWeather(Field field) {
        // get new data if we feel it's appropriate
        considerRefresh(field);

        // no data, maybe we're offline?
        if (!weathers.containsKey(getKey(field))) {
            return null;
        }

        // our actual non-forecasted weather
        Weather known = weathers.get(getKey(field)).get(0);

        if (Instant.now().isAfter(known.getWhen().plus(EXPIRY))) {
            // data's use of representing the current weather has past
            return null;
        }

        return known;
    }

    /**
     * Get the current Soil at a field.
     *
     * Data is guaranteed to be based on actual, not forecasted, data and shall be no older than EXPIRY.
     *
     * @param field
     * @return null if no data available, else the current Soil
     */
    public Soil getCurrentSoil(Field field) {
        // get new data if we feel it's appropriate
        considerRefresh(field);

        // no data, maybe we're offline?
        if (!soils.containsKey(getKey(field))) {
            return null;
        }

        Soil known = soils.get(getKey(field));

        if (Instant.now().isAfter(known.getWhen().plus(EXPIRY))) {
            // data's use of representing the current soil has past
            return null;
        }

        return known;
    }

    /**
     * Get forecasted weather for a field.
     *
     * Data from past dates is not included.
     *
     * @param field
     * @return an empty list if no data available, else the current Soil
     */
    public List<Weather> getForecastedWeather(Field field) {
        // get new data if we feel it's appropriate
        considerRefresh(field);

        // no data, maybe we're offline?
        if (!weathers.containsKey(getKey(field))) {
            return new ArrayList<>();
        }

        List<Weather> forecast = weathers.get(getKey(field));

        // remove the non-forecasted data and, crucially, clone the List
        forecast = forecast.subList(1, forecast.size());

        // remove data from the past
        return forecast.stream()
                .filter(w -> Instant.now().isBefore(w.getWhen()))
                .collect(Collectors.toList());
    }

    @Override
    protected JsonObject serialise() {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    protected void deserialise(JsonReader json) {
        throw new IllegalStateException("Not yet implemented");
    }

    /**
     * Attempt to refresh the data for a field if we're online and either:
     * - There is no data currently stored OR
     * - The age of the cached data exceeds CACHE_TIME
     *
     * @param field
     */
    private void considerRefresh(Field field) {
        if (!api.isOnline()) {
            // there is nothing we can do
            return;
        }

        if (lastFetched.containsKey(getKey(field))) {
            Instant refreshWhen = lastFetched.get(getKey(field)).plus(CACHE_TIME);
            if (Instant.now().isAfter(refreshWhen)) {
                this.refresh(field);
            }
        } else {
            this.refresh(field);
        }
    }

    /**
     * Contact the API and get and store up-to-date info on a Field.
     *
     * @param field
     */
    private void refresh(Field field) {
        // fetch data from API
        Soil soil = api.getCurrentSoil(field.getLat(), field.getLng());

        Weather current = api.getCurrentWeather(field.getLat(), field.getLng());
        List<Weather> forecast = api.getForecastWeather(field.getLat(), field.getLng());

        // merge weather into one list
        forecast.add(0, current);

        // store
        weathers.put(getKey(field), forecast);
        soils.put(getKey(field), soil);

        // keep track of when we did this so we know when to refresh the cache
        lastFetched.put(getKey(field), Instant.now());
    }

    /**
     * Within the class, Fields are represented by a String derived from their coordinates.
     *
     * This accounts for name changes and persisting the relation between Fields and data. Further, it logical sense,
     * as the data gathered is actually relevant to a given set of coordinates, not the abstract concept of a Field.
     *
     * @param field
     * @return a unique String key based on latitude and longitude
     */
    private String getKey(Field field) {
        return field.getLat() + "," + field.getLng();
    }

}
