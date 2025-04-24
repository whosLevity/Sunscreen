package me.combimagnetron.sunscreen.hook.labymod;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPluginMessage;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.hook.ClientHook;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.hook.labymod.protocol.clientbound.ClientboundLabyModAddonDisableMessage;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import java.util.List;

public class LabyModSunscreenHook implements SunscreenHook, ClientHook {
    private static final int id = 24;
    private static final String CHANNEL = "labymod:neo";

    @Override
    public boolean canRun() {
        return true;
    }

    @Override
    public void enable() {
        // No implementation needed
    }

    @Override
    public void disable() {
        // No implementation needed
    }

    @Override
    public void onMenuEnter(SunscreenUser<?> user, OpenedMenu menu) {
        List<String> modsToDisable = List.of(
                "menu-blur",
                "direction-hud",
                "freelook",
                "minimap",
                "coordinates"
        );
        ClientboundLabyModAddonDisableMessage message = ClientboundLabyModAddonDisableMessage.of(modsToDisable, ClientboundLabyModAddonDisableMessage.Action.DISABLE);
        ByteBuffer buffer = ByteBuffer.empty();
        message.write(buffer);
        user.connection().send(new WrapperPlayServerPluginMessage(CHANNEL, buffer.bytes()));
    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {
        // No implementation needed
    }
}
