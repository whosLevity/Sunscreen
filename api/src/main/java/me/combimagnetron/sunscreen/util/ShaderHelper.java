package me.combimagnetron.sunscreen.util;

import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public class ShaderHelper {

    public static int encode(Position position, SunscreenUser<?> user) {
        Vec2i screenSize = user.screenSize().pixel();
        int x = decimals(position.x().pixel()/screenSize.x());
        int y = decimals(position.y().pixel()/screenSize.y());
        x = Math.max(0, Math.min(153, x));
        y = Math.max(0, Math.min(153, y));
        return x * 154 + y;
    }

    public static int decimals(double fraction) {
        fraction = Math.max(0.0, Math.min(1.0, fraction));
        return (int) Math.floor(fraction * 100.0);
    }

}
