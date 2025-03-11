package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.sunscreen.user.SunscreenUser;

public interface ResolutionFinder {

    ScreenSize find(SunscreenUser<?> user);

}
