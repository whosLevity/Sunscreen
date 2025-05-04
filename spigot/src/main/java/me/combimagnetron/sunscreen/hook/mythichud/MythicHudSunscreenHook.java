package me.combimagnetron.sunscreen.hook.mythichud;

import io.lumine.mythichud.api.HudHolder;
import io.lumine.mythichud.api.MythicHUD;
import io.lumine.mythichud.api.element.layout.HudLayout;
import io.lumine.mythichud.api.hud.active.element.ActiveLayout;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Reflect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class MythicHudSunscreenHook implements SunscreenHook {
    private final Map<UUID, List<String>> previouslyActive = new HashMap<>();

    @Override
    public boolean canRun() {
        return Bukkit.getPluginManager().isPluginEnabled("MythicHUD");
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void onMenuEnter(SunscreenUser<?> user, OpenedMenu menu) {
        HudHolder holder = MythicHUD.getInstance().holders().getPlayer((Player) user.platformSpecificPlayer());
        if (holder == null) return;
        Map<String, ActiveLayout> layoutsMap = Reflect.field(holder, "layouts");
        for (ActiveLayout value : layoutsMap.values()) {
            HudLayout layout = value.getParent();
            if (!holder.removeLayout(layout)) {
                continue;
            }
            if (previouslyActive.containsKey(user.uniqueIdentifier())) {
                previouslyActive.get(user.uniqueIdentifier()).add(layout.getKey());
            } else {
                previouslyActive.put(user.uniqueIdentifier(), new ArrayList<>(List.of(layout.getKey())));
            }
        }
        holder.send();
    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {
        HudHolder holder = MythicHUD.getInstance().holders().getPlayer((Player) user.platformSpecificPlayer());
        if (holder == null) return;
        List<String> previouslyActiveLayouts = previouslyActive.get(user.uniqueIdentifier());
        if (previouslyActiveLayouts == null) {
            return;
        }
        Collection<HudLayout> layouts = MythicHUD.getInstance().layouts().getLayouts();
        for (String layout : previouslyActiveLayouts) {
            layouts.stream().filter(hudLayout -> hudLayout.getKey().equals(layout)).findFirst().ifPresent(holder::addLayout);
        }
        previouslyActive.remove(user.uniqueIdentifier());
    }
}
