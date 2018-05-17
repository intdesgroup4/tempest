package WeatherApp.service;

import WeatherApp.model.Field;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

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
