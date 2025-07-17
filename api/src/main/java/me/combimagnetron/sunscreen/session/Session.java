package me.combimagnetron.sunscreen.session;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public sealed interface Session permits Session.Impl {

    OpenedMenu menu();

    SunscreenUser<?> user();

    boolean close();

    static Session session(OpenedMenu openedMenu, SunscreenUser<?> user) {
        return new Impl(openedMenu, user);
    }

    record Impl(OpenedMenu openedMenu, SunscreenUser<?> user) implements Session {

        @Override
        public OpenedMenu menu() {
            return openedMenu;
        }

        @Override
        public boolean close() {
            if (openedMenu != null) {
                menu().close();
                SunscreenLibrary.library().sessionHandler().session(Session.session(null, user));
            }
            return true;
        }
    }

}
