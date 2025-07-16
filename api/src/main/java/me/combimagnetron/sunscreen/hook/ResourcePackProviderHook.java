package me.combimagnetron.sunscreen.hook;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.resourcepack.ResourcePack;

public interface ResourcePackProviderHook extends SunscreenHook {

    Canvas font(char ch);

    boolean merge(ResourcePack resourcePack);

}
