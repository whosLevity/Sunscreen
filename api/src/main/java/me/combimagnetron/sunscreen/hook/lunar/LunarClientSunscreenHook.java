package me.combimagnetron.sunscreen.hook.lunar;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPluginMessage;
import com.google.gson.JsonObject;
import me.combimagnetron.sunscreen.hook.ClientHook;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.hook.lunar.protocol.clientbound.ClientboundLunarClientModActionMessage;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;

import java.util.Map;

public class LunarClientSunscreenHook implements SunscreenHook, ClientHook {
    private final static String CHANNEL = "apollo:json";
    private final static Map<String, Boolean> MODS_DISABLE = Map.of(
            "menu-blur", false,
            "direction-hud", false,
            "freelook", false,
            "minimap", false,
            "coordinates", false);
    private final static Map<String, Boolean> MODS_ENABLE = Map.of(
            "menu-blur", true,
            "direction-hud", true,
            "freelook", true,
            "minimap", true,
            "coordinates", true);

    @Override
    public boolean canRun() {
        return true;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void onMenuEnter(SunscreenUser<?> user, OpenedMenu menu) {
        JsonObject object = new JsonObject();
        ClientboundLunarClientModActionMessage message = ClientboundLunarClientModActionMessage.of(MODS_DISABLE);
        message.write(object);
        user.connection().send(new WrapperPlayServerPluginMessage(CHANNEL, object.toString().getBytes()));
    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {
        JsonObject object = new JsonObject();
        ClientboundLunarClientModActionMessage message = ClientboundLunarClientModActionMessage.of(MODS_ENABLE);
        message.write(object);
        user.connection().send(new WrapperPlayServerPluginMessage(CHANNEL, object.toString().getBytes()));
    }

}
