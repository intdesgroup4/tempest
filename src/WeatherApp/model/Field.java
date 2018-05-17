package WeatherApp.model;

import javafx.scene.image.Image;

/**
 * A representation of a farmer's real-world field.
 *
 * Consists of its geographical position and a brief textual description. An icon Image may be optionally provided.
 *
 * DRY; the FieldStore class is provided for the management of a user's fields and their persistence.
 */
public class Field {

    /**
     * A human-readable name of the Field.
     */
    private String name;

    /**
     * The latitude of the Field's coordinates.
     */
    private double lat;

    /**
     * The longitude of the Field's coordinates.
     */
    private double lng;

    /**
     * A JavaFX image to use as an icon within a GUI.
     *
     * OPTIONAL - may be null
     */
    private Image image = null;

    public Field(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Field(String name, double lat, double lng, Image image) {
        this(name, lat, lng);
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
