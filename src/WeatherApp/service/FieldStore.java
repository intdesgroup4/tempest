package WeatherApp.service;

import WeatherApp.model.Field;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

    private List<Field> fields;  // TODO initialise in overridden abstract Store methods

    public FieldStore(Path path) {
        super(path);

        // deserialise may not be called on initial run as the file may not exist
        // we can't initialise in the variable definition itself as, bizarrely, it is called before this code but after the super constructor
        if (fields == null) {
            fields = new ArrayList<>();
        }
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
    protected JsonElement serialise() {
        JsonArray jFields = new JsonArray();

        for (Field field : fields) {
            JsonObject jField = new JsonObject();
            jField.addProperty("name", field.getName());
            jField.addProperty("latitude", field.getLat());
            jField.addProperty("longitude", field.getLng());
            jFields.add(jField);
        }

        return jFields;
    }

    @Override
    protected void deserialise(JsonElement json) {
        if (fields == null) {
            fields = new ArrayList<>();
        } else {
            fields.clear();
        }

        for (JsonElement jElement : json.getAsJsonArray()) {
            JsonObject jField = jElement.getAsJsonObject();
            Field field = new Field(jField.get("name").getAsString(),
                    jField.get("latitude").getAsDouble(),
                    jField.get("longitude").getAsDouble());

            fields.add(field);
        }
    }

}
