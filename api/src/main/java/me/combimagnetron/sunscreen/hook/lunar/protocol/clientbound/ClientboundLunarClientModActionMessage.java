package me.combimagnetron.sunscreen.hook.lunar.protocol.clientbound;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.combimagnetron.sunscreen.hook.lunar.protocol.LunarClientMessage;

import java.util.Map;

public class ClientboundLunarClientModActionMessage implements LunarClientMessage {
    private final static String TYPE = "@type.googleapis.com/lunarclient.apollo.configurable.v1.OverrideConfigurableSettingsMessage";
    private final static String MODULE = "apollo_module";
    private final Map<String, Boolean> mods;

    protected ClientboundLunarClientModActionMessage(Map<String, Boolean> mods) {
        this.mods = mods;
    }

    public static ClientboundLunarClientModActionMessage of(Map<String, Boolean> mods) {
        return new ClientboundLunarClientModActionMessage(mods);
    }

    @Override
    public void read(JsonObject reader) {

    }

    @Override
    public void write(JsonObject writer) {
        writer.addProperty("@type", TYPE);
        JsonArray properties = new JsonArray();
        for (Map.Entry<String, Boolean> entry : mods.entrySet()) {
            JsonObject settings = new JsonObject();
            settings.addProperty(MODULE, "mod_setting");
            settings.addProperty("enable", true);
            JsonObject property = new JsonObject();
            property.addProperty(entry.getKey() + ".enabled", entry.getValue());
            settings.add("properties", property);
            properties.add(settings);
        }
        writer.add("configurable_settings", properties);
    }
}
