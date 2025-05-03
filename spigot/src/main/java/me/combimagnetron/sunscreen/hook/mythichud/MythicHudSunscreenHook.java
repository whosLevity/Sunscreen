package me.combimagnetron.sunscreen.hook.mythichud;

import io.lumine.mythichud.api.MythicHUD;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MythicHudSunscreenHook implements SunscreenHook {
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
        MythicHUD.getInstance().holders().getPlayer((Player) user.platformSpecificPlayer()).getBarHandler().disable();
    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {
        MythicHUD.getInstance().holders().getPlayer((Player) user.platformSpecificPlayer()).getBarHandler().init();
    }
}
