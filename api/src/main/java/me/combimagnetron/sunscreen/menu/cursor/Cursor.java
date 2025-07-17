package me.combimagnetron.sunscreen.menu.cursor;

import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

public interface Cursor {

    void show(SunscreenUser<?> user);

    void move(Vec2i vec2i);

    void hide();

    void show();

    void remove();

    Vec2d startPos(SunscreenUser<?> user);

    static Cursor client(SunscreenUser<?> user) {
        return new ClientsideCursor(user);
    }

}
