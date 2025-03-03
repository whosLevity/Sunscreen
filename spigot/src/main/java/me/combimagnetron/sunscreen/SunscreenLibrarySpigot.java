package me.combimagnetron.sunscreen;

import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import me.combimagnetron.passport.Passport;
import me.combimagnetron.passport.event.EventBus;
import me.combimagnetron.passport.user.UserHandler;
import me.combimagnetron.sunscreen.config.Config;
import me.combimagnetron.sunscreen.menu.timing.MenuTicker;
import me.combimagnetron.sunscreen.session.SessionHandler;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.user.UserManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.io.InputStream;
import java.nio.file.Path;

public class SunscreenLibrarySpigot implements SunscreenLibrary<SunscreenPlugin, Player> {
    private final MenuTicker menuTicker = new MenuTicker();
    private final SessionHandler sessionHandler = new SessionHandler();
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
                return null;
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
        return ComponentLogger.logger();
    }

}
