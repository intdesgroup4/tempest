package WeatherApp.service;

import WeatherApp.model.Field;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the prioritising and persistence of Field objects.
 *
 * DRY; the contained List<Field> should be manipulated to add/remove/provide ordering.
 *
 * See the Store class for greater insight into how persistence is achieved.
 *
 * TODO write gson code to serialise/deserialise
 */
public class FieldStore extends Store {

    private final List<Field> fields = new ArrayList<>();  // TODO initialise in overridden abstract Store methods

    public FieldStore(Path path) {
        super(path);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path.toFile()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
                JsonParser parser = new JsonParser();
                JsonObject fieldJson = (JsonObject) parser.parse(line);

                Field field = new Field(fieldJson.get("name").getAsString(),
                        fieldJson.get("latitude").getAsDouble(),
                        fieldJson.get("longitude").getAsDouble());

                fields.add(field);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fields.size());
    }

    /**
     * This should be the primary way of interacting with the Fields - adding, removing, reordering, etc. No point in
     * reinventing the wheel.
     *
     * @return all Fields in the store
     */
    public List<Field> getFields() {
        return fields;
    }

    @Override
    protected JsonObject serialise() {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    protected void deserialise(JsonReader json) {
        throw new IllegalStateException("Not yet implemented");
    }

}
