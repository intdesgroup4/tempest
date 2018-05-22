package WeatherApp.service;

import WeatherApp.model.Condition;
import WeatherApp.model.Field;
import WeatherApp.model.Soil;
import WeatherApp.model.Weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private Map<String, List<Weather>> weathers;

    /**
     * The soils known for a Field.
     */
    private final Map<String, Soil> soils = new HashMap<>();

    public AgroStore(Path path, AgroAPI api) {
        super(path);

        this.api = api;

        if (weathers == null) {
            // weathers may not be assumed to be initialised in deserialise - the store file may not exist yet
            weathers = new HashMap<>();
        }
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
            //return null;
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
    public List<Weather> getForecastWeather(Field field) {
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
    protected JsonElement serialise() {
    	 JsonArray wList = new JsonArray();
     	for(String fieldId: weathers.keySet()) {
     		for(Weather weather: weathers.get(fieldId)) {
     			JsonObject jWet = new JsonObject();
     			jWet.addProperty("Id", fieldId);
     			jWet.addProperty("When", weather.getWhen().getEpochSecond());
     			jWet.addProperty("Temperature", weather.getTemperature());
     			jWet.addProperty("Humidity", weather.getHumidity());
     			jWet.addProperty("WindSpeed", weather.getWindSpeed());
     			jWet.addProperty("WindDirection", weather.getWindDirection());
     			jWet.addProperty("Rainfall", weather.getRainfall());
     			
     			//store conditions labelled as c0,c1,c2 etc in the same jsonobject:
     			List<Condition> conditions = weather.getConditions();
     			int numConditions = conditions.size();
     			jWet.addProperty("numConditions",numConditions);
     			for(int i = 0; i<numConditions;i++) {
     				//conditions are stored using their ids
     				jWet.addProperty("c" + i, conditions.get(i).getId());
     			}
     			
     			wList.add(jWet);
     		}
     	}
     return wList;
    }

    @Override
    protected void deserialise(JsonElement json) {
    	//a list of all current Weather objects:
    	List<Weather> allWeather = new ArrayList<>();
    	//a parallel list of the associated field Id's for each weather element in allWeather
    	List<String> allIds = new ArrayList<>();

        if (weathers == null) {
            // owing to the weird order that code is called in on initialisation, we have to initialise the variable here
            // see the comments in FieldStore for a better explanation of this issue
            weathers = new HashMap<>();
        }

        weathers.clear();
        
        for(JsonElement jElement: json.getAsJsonArray()) {
        	JsonObject jWet = jElement.getAsJsonObject();
        	String jId = jWet.get("Id").getAsString();
        	Instant jWhen = Instant.from(Instant.ofEpochSecond(jWet.get("When").getAsLong()));
        	double jTemperature = jWet.get("Temperature").getAsDouble();
        	int jHumidity = jWet.get("Humidity").getAsInt();
        	double jWindSpeed = jWet.get("WindSpeed").getAsDouble();
        	double jWindDirection = jWet.get("WindDirection").getAsDouble();
        	double jRainfall = jWet.get("Rainfall").getAsDouble();
        	
        	int numConditions = jWet.get("numConditions").getAsInt();
        	List<Condition> jConditions = new ArrayList<>();
        	for(int i=0; i<numConditions;i++) {
        		jConditions.add(Condition.fromId(jWet.get("c" + i).getAsInt()));
        	}
        	
        	Weather jWeather = new Weather(jWhen, jTemperature, jHumidity, jWindSpeed, jWindDirection, jConditions, jRainfall);
        	allWeather.add(jWeather);
        	allIds.add(jId);
        }
        
        //iterate through the two lists to create the required hashmap (id to list of weather objects) format
        Map<String,List<Weather>> finalMap = new HashMap<>();
        for(int index = 0;index<allWeather.size()-1;index++) {
        	String Id = allIds.get(index);
        	Weather wet = allWeather.get(index);
        	if(finalMap.keySet().contains(Id)) {
        		//field Id is already in the map
        		finalMap.get(Id).add(wet);
        	}
        	else {
        		//must create the list for this unseen field id
        		List<Weather> internalList = new ArrayList<>();
        		internalList.add(wet);
        		finalMap.put(Id, internalList);
        	}
        }
        
        this.weathers = finalMap;  
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
        Soil soil;

        Weather current;
        List<Weather> forecast;

        try {
            // fetch data from API
            soil = api.getCurrentSoil(field.getLat(), field.getLng());

            current = api.getCurrentWeather(field.getLat(), field.getLng());
            forecast = api.getForecastWeather(field.getLat(), field.getLng());
        } catch (IOException io) {
            System.err.println("Failed to contact Weather API to refresh data");
            io.printStackTrace();
            return; // nothing more we can do here
        }

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
