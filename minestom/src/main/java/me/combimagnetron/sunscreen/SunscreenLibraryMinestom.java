package me.combimagnetron.sunscreen;

import me.combimagnetron.passport.Passport;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.user.UserHandler;
import me.combimagnetron.passport.util.placeholder.PlaceholderRegistry;
import me.combimagnetron.sunscreen.config.Config;
import me.combimagnetron.sunscreen.config.MenuConfigTransformer;
import me.combimagnetron.sunscreen.menu.timing.MenuTicker;
import me.combimagnetron.sunscreen.registry.MenuRegistry;
import me.combimagnetron.sunscreen.session.SessionHandler;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.user.UserManager;
import net.kyori.adventure.audience.Audience;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import org.slf4j.Logger;

import java.io.InputStream;
import java.nio.file.Path;

public class SunscreenLibraryMinestom implements SunscreenLibrary<Object, Player> {
    private final SessionHandler sessionHandler = new SessionHandler();
    private final MenuTicker menuTicker = new MenuTicker();
    private final MenuRegistry menuRegistry = MenuRegistry.create();
    private final UserManager userManager = new UserManager();

    protected SunscreenLibraryMinestom() {
        Holder.INSTANCE = this;
    }


    @Override
    public Passport<Object> passport() {
        return new Passport<>() {
            @Override
            public UserHandler<? extends Audience, ? extends User<? extends Audience>> users() {
                return userManager;
            }

            @Override
            public Path dataFolder() {
                return null;
            }

            @Override
            public PlaceholderRegistry placeholders() {
                return null;
            }

            @Override
            public SunscreenAddon plugin() {
                return null;
            }
        };
    }

    @Override
    public Path path() {
        return null;
    }

    @Override
    public Object plugin() {
        return null;
    }

    @Override
    public InputStream resource(String path) {
        return SunscreenLibraryMinestom.class.getResourceAsStream(path);
    }

    @Override
    public SessionHandler sessionHandler() {
        return sessionHandler;
    }

    @Override
    public MenuTicker menuTicker() {
        return menuTicker;
    }

    @Override
    public UserHandler<Player, SunscreenUser<Player>> users() {
        return userManager;
    }

    @Override
    public Config config() {
        return null;
    }

    @Override
    public Logger logger() {
        return MinecraftServer.LOGGER;
    }

    @Override
    public MenuRegistry menuRegistry() {
        return menuRegistry;
    }

    @Override
    public MenuConfigTransformer menuConfigTransformer() {
        return null;
    }
}
