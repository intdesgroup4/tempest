package WeatherApp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.file.Path;

public class SettingsStore extends Store {
    private Integer frequency;
    private String tempUnit;
    private String windUnit;
    private Boolean notifications;

    public SettingsStore(Path path){
        super(path);

        // create defaults if deserialise didn't populate the variables
        if (frequency == null) {
            // every 12 hours
            frequency = 12;
        }

        if (tempUnit == null) {
            // celsius
            tempUnit = "C";
        }

        if (windUnit == null) {
            // metres per second
            windUnit = "m/s";
        }

        if (notifications == null) {
            // no notifications
            notifications = false;
        }
    }

    /**
     * Serialise global settings state into JSON
     */
    @Override
    protected JsonElement serialise() {
        JsonObject settings = new JsonObject();
        settings.addProperty("frequency", frequency);
        settings.addProperty("tempUnit", tempUnit);
        settings.addProperty("windUnit", windUnit);
        settings.addProperty("notifications", notifications);

        return settings;
    }

    /**
     * Deserialize global settings from JSON
     * @param json
     */
    @Override
    protected void deserialise(JsonElement json) {
        JsonObject settings = json.getAsJsonObject();

        frequency = settings.get("frequency").getAsInt();
        tempUnit = settings.get("tempUnit").getAsString();
        windUnit = settings.get("windUnit").getAsString();
        notifications = settings.get("notifications").getAsBoolean();
    }

    public int getFrequency(){
        return frequency;
    }

    public String getTempUnit() {
        return tempUnit;
    }

    public String getTempUnitIcon() {
        if (tempUnit.equals("C"))
            return "°C";
        else if (tempUnit.equals("F"))
            return  "°F";
        else
            return  "K";
    }

    /**
     * Nice way of getting temperature unit
     * @return
     */
    public String getTempUnitFull() {
        if (tempUnit.equals("C"))
            return "Celsius (°C)";
        else if (tempUnit.equals("F"))
            return  "Fahrenheit (°F)";
        else
            return  "Kelvin (K)";
    }

    public String getWindUnit() {
        return windUnit;
    }

    public boolean getNotifications() {
        return notifications;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
    }

    /**
     * Nice way of setting temperature unit
     * @param tempUnitFull
     */
    public void setTempUnitFull(String tempUnitFull) {
        if(tempUnitFull.equals("Celsius (°C)"))
            tempUnit = "C";
        else if (tempUnitFull.equals("Fahrenheit (°F)"))
            tempUnit = "F";
        else
            tempUnit = "K";

    }

    public void setWindUnit(String windUnit) {
        this.windUnit = windUnit;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}