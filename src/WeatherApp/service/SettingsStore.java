package WeatherApp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.file.Path;

public class SettingsStore extends Store {
    private int textSize;
    private String tempUnit;
    private String theme;
    private boolean notifications;

    public SettingsStore(Path path){
        super(path);
    }

    @Override
    protected JsonElement serialise() {
        JsonObject settings = new JsonObject();
        settings.addProperty("textSize", textSize);
        settings.addProperty("tempUnit", tempUnit);
        settings.addProperty("theme", theme);
        settings.addProperty("notifications", notifications);

        return settings;
    }

    @Override
    protected void deserialise(JsonElement json) {
        JsonObject settings = json.getAsJsonObject();

        textSize = settings.get("textSize").getAsInt();
        tempUnit = settings.get("tempUnit").getAsString();
        theme = settings.get("theme").getAsString();
        notifications = settings.get("notifications").getAsBoolean();
    }

    public int getTextSize(){ return textSize; }
    public String getTempUnit() { return tempUnit; }
    public String getTheme() {return theme; }
    public boolean getNotifications() {return notifications; }
}
