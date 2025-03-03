package me.combimagnetron.sunscreen.user;

import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.util.Pos2D;
import me.combimagnetron.sunscreen.menu.ScreenSize;
import me.combimagnetron.sunscreen.session.Session;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;
import net.kyori.adventure.audience.Audience;

public interface SunscreenUser<T extends Audience> extends User<T> {

    ScreenSize screenSize();

    void screenSize(ScreenSize screenSize);

    float fov();

    void fov(float fov);

    boolean permission(String permission);

    Session session();

}
