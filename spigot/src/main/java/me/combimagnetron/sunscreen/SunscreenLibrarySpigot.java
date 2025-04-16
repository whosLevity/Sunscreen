package me.combimagnetron.sunscreen;

import me.combimagnetron.passport.Passport;
import me.combimagnetron.passport.user.UserHandler;
import me.combimagnetron.passport.util.placeholder.PlaceholderRegistry;
import me.combimagnetron.sunscreen.config.Config;
import me.combimagnetron.sunscreen.config.MenuConfigTransformer;
import me.combimagnetron.sunscreen.registry.MenuRegistry;
import me.combimagnetron.sunscreen.menu.timing.MenuTicker;
import me.combimagnetron.sunscreen.session.SessionHandler;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.io.InputStream;
import java.nio.file.Path;

public class SunscreenLibrarySpigot implements SunscreenLibrary<SunscreenPlugin, Player> {
    private final MenuTicker menuTicker = new MenuTicker();
    private final SessionHandler sessionHandler = new SessionHandler();
    private final PlaceholderRegistry placeholderRegistry = new PlaceholderRegistry.Impl();
    private final MenuRegistry menuRegistry = MenuRegistry.create();
    private final MenuConfigTransformer menuConfigTransformer = new MenuConfigTransformer.Impl();
    private final SunscreenPlugin plugin;

    public SunscreenLibrarySpigot(SunscreenPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public me.combimagnetron.passport.Passport<SunscreenPlugin> passport() {
        return new Passport<>() {
            @Override
            public UserHandler<Player, SunscreenUser<Player>> users() {
                return plugin.userManager();
            }

            @Override
            public Path dataFolder() {
                return path();
            }

            @Override
            public PlaceholderRegistry placeholders() {
                return placeholderRegistry;
            }

            @Override
            public SunscreenPlugin plugin() {
                return plugin;
            }
        };
    }

    @Override
    public Path path() {
        return plugin.getDataFolder().toPath();
    }

    @Override
    public SunscreenPlugin plugin() {
        return plugin;
    }

    @Override
    public InputStream resource(String path) {
        return plugin.getResource(path);
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
        return plugin.userManager();
    }

    @Override
    public Config config() {
        return new Config(0, true, false, "");
    }

    @Override
    public Logger logger() {
        return plugin.getComponentLogger();
    }

    @Override
    public MenuConfigTransformer menuConfigTransformer() {
        return menuConfigTransformer;
    }

    @Override
    public MenuRegistry menuRegistry() {
        return (MenuRegistry) menuRegistry;
    }

}
