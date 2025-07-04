package me.combimagnetron.sunscreen.hook.betterhud;

import kr.toxicity.hud.api.BetterHudAPI;
import kr.toxicity.hud.api.player.HudPlayer;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import org.bukkit.Bukkit;

public class BetterHudSunscreenHook implements SunscreenHook {

    @Override
    public boolean canRun() {
        return Bukkit.getPluginManager().isPluginEnabled("BetterHud");
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void onMenuEnter(SunscreenUser<?> user, OpenedMenu menu) {
        HudPlayer hudPlayer = BetterHudAPI.inst().getPlayerManager().getHudPlayer(user.uniqueIdentifier());
        if (hudPlayer == null) {
            return;
        }
        hudPlayer.setHudEnabled(false);
    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {
        HudPlayer hudPlayer = BetterHudAPI.inst().getPlayerManager().getHudPlayer(user.uniqueIdentifier());
        if (hudPlayer == null) {
            return;
        }
        hudPlayer.setHudEnabled(true);
    }
}
