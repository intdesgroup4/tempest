package WeatherApp.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Provides convenient methods for loading/saving state from a file, provided that an extending class implements
 * serialisation and deserialisation methods.
 *
 * Note: Exceptions will likely be added to the constructor, load and save signatures soon
 *
 * TODO choose a library for storing simple data types - maybe share with the one that'll be used to decode the JSON API
 *      responses?
 */
public abstract class Store {

    /**
     * The path at which the state will be loaded from and saved to.
     */
    private final Path path;

    public Store(Path path) {
        this.path = path;

//        this.load();
    }

    /**
     * Load state from the file.
     *
     * TODO ensure nothing is left open
     */
    public void load() {
        try (Reader reader = Files.newBufferedReader(path)){
            this.deserialise(new JsonReader(reader));
        } catch (IOException e) {
            System.err.println(String.format("Couldn't open file '%s' to read; does it exist?", path.toString()));
            e.printStackTrace();
            return; // no state to gain here
        }
    }

    /**
     * Save state to the file.
     *
     * TODO ensure nothing is left open
     */
    public void save() {
        try (Writer writer = Files.newBufferedWriter(path)) {
            Gson gson = new Gson();
            gson.toJson(serialise(), writer);
        } catch (IOException e) {
            System.err.println(String.format("Couldn't open file '%s' to write", path.toString()));
            e.printStackTrace();
            return; // nothing more we can do - we won't treat this as fatal
        }
    }

    /**
     * Serialise state into a JsonObject.
     *
     * @return
     */
    protected abstract JsonObject serialise();

    /**
     * Deserialise state and load into this object.
     *
     * @param json
     */
    protected abstract void deserialise(JsonReader json);

}
