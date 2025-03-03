package me.combimagnetron.sunscreen.desktop;

import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.menu.element.div.Div;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Vec2d;

public final class WindowedChestMenu implements Menu {
    private final Menu menu;
    private final Vec2d position;
    private final Vec2d size;

    public WindowedChestMenu(Menu menu, Vec2d position, Vec2d size) {
        this.menu = menu;
        this.position = position;
        this.size = size;
    }

    public void relay(Input<?> input) {

    }

    public Menu menu() {
        return menu;
    }

    public Vec2d position() {
        return position;
    }

    public Vec2d size() {
        return size;
    }

    @Override
    public String toString() {
        return "WindowedChestMenu[" +
                "menu=" + menu + ", " +
                "position=" + position + ", " +
                "size=" + size + ']';
    }


    @Override
    public Menu apply(Draft draft) {
        return this;
    }

    @Override
    public void open(SunscreenUser<?> user) {

    }

    @Override
    public Menu div(Div div) {
        return null;
    }

    @Override
    public boolean close() {
        return false;
    }

    public User<?> user() {
        return null;
    }

}
