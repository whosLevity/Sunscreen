package me.combimagnetron.sunscreen.hook.nexo;

import com.nexomc.nexo.api.NexoPack;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import org.bukkit.Bukkit;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.metadata.overlays.OverlayEntry;
import team.unnamed.creative.metadata.overlays.OverlaysMeta;
import team.unnamed.creative.overlay.Overlay;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NexoSunscreenHook implements SunscreenHook {
    @Override
    public boolean canRun() {
        return Bukkit.getPluginManager().isPluginEnabled("Nexo");
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void onMenuEnter(SunscreenUser<?> user, OpenedMenu menu) {
        ResourcePack pack = NexoPack.resourcePack();
        OverlaysMeta overlaysMeta = pack.overlaysMeta();
        if (overlaysMeta == null) {
            return;
        }
        for (OverlayEntry entry : overlaysMeta.entries()) {
            Overlay overlay = pack.overlay(entry.directory());
            if (overlay == null) {
                continue;
            }
            Map<String, Writable> files = overlay.unknownFiles();
            files.forEach((path, writable) -> {
                String shader;
                try {
                    shader = writable.toUTF8String();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (shader == null) {
                    return;
                }

            });
        }
    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {

    }
}
