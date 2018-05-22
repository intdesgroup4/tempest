package WeatherApp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.file.Path;

public class SettingsStore extends Store {
    private int frequency;
    private String tempUnit;
    private String windUnit;
    private boolean notifications;

    public SettingsStore(Path path){
        super(path);
    }

    @Override
    protected JsonElement serialise() {
        JsonObject settings = new JsonObject();
        settings.addProperty("frequency", frequency);
        settings.addProperty("tempUnit", tempUnit);
        settings.addProperty("windUnit", windUnit);
        settings.addProperty("notifications", notifications);

        return settings;
    }

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