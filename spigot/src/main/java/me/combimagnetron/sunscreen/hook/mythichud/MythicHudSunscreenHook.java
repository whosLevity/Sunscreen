package me.combimagnetron.sunscreen.hook.mythichud;

import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import org.bukkit.event.Listener;

public class MythicHudSunscreenHook implements SunscreenHook, Listener {
    @Override
    public boolean canRun() {
        return false;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void onMenuEnter(SunscreenUser<?> user, OpenedMenu menu) {

    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {

    }
    /*private final Map<UUID, List<String>> previouslyActive = new HashMap<>();

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

    public MythicHudSunscreenHook() {
        Bukkit.getPluginManager().registerEvents(this, (Plugin) SunscreenLibrary.library().plugin());
    }

    public void onLayoutApply(HUDLayoutAddEvent event) {
        Player player = event.getPlayer();
        SunscreenUser<?> user = SunscreenLibrary.library().users().user(player);
        if (SunscreenLibrary.library().sessionHandler().session(user).menu() == null) {
            return;
        }
        event.setCancelled(true);
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
    }*/
}
