package me.combimagnetron.sunscreen.hook.tab;

import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.bossbar.BossBar;
import me.neznamy.tab.api.bossbar.BossBarManager;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;

public class TABSunscreenHook implements SunscreenHook {
    @Override
    public boolean canRun() {
        return Bukkit.getPluginManager().isPluginEnabled("TAB");
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void onMenuEnter(SunscreenUser<?> user, OpenedMenu menu) {
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(user.uniqueIdentifier());
        if (tabPlayer == null) {
            return;
        }
        ScoreboardManager scoreboardManager = TabAPI.getInstance().getScoreboardManager();
        if (scoreboardManager == null) {
            return;
        }
        scoreboardManager.setScoreboardVisible(tabPlayer, false, false);
        BossBarManager bossBarManager = TabAPI.getInstance().getBossBarManager();
        if (bossBarManager == null) {
            return;
        }
        bossBarManager.setBossBarVisible(tabPlayer, false, false);
    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(user.uniqueIdentifier());
        if (tabPlayer == null) {
            return;
        }
        ScoreboardManager scoreboardManager = TabAPI.getInstance().getScoreboardManager();
        if (scoreboardManager == null) {
            return;
        }
        scoreboardManager.setScoreboardVisible(tabPlayer, true, false);
        BossBarManager bossBarManager = TabAPI.getInstance().getBossBarManager();
        if (bossBarManager == null) {
            return;
        }
        bossBarManager.setBossBarVisible(tabPlayer, true, false);
    }
}
