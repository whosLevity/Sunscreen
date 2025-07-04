package me.combimagnetron.sunscreen.hook.featherclient;

import me.combimagnetron.sunscreen.hook.ClientHook;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public class FeatherClientSunscreenHook implements SunscreenHook, ClientHook {
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

    }

    @Override
    public void onMenuLeave(SunscreenUser<?> user, OpenedMenu menu) {

    }
}
