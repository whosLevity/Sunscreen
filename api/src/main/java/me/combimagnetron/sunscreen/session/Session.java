package me.combimagnetron.sunscreen.session;

import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public interface Session {

    Menu menu();

    SunscreenUser<?> user();

    boolean close();

    static Session session(Menu menu, SunscreenUser<?> user) {
        return new Impl(menu, user);
    }

    record Impl(Menu menu, SunscreenUser<?> user) implements Session {

        @Override
            public boolean close() {
                return true;
            }
        }

}
