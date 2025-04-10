package me.combimagnetron.sunscreen;


import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import me.combimagnetron.passport.Passport;
import me.combimagnetron.passport.config.Config;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.sunscreen.command.SunscreenCommand;
import me.combimagnetron.sunscreen.command.condition.CustomCondition;
import me.combimagnetron.sunscreen.command.parameter.IdentifierParameterType;
import me.combimagnetron.sunscreen.command.parameter.UserParameterType;
import me.combimagnetron.sunscreen.menu.ScreenSize;
import me.combimagnetron.sunscreen.menu.AspectRatioMenu;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.menu.listener.MenuListener;
import me.combimagnetron.sunscreen.menu.timing.MenuTicker;
import me.combimagnetron.sunscreen.placeholder.PapiPlaceholderProvider;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.user.UserManager;
import me.combimagnetron.sunscreen.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;

import java.io.File;
import java.nio.ByteBuffer;

public class SunscreenPlugin extends JavaPlugin {
    private SunscreenLibrary<SunscreenPlugin, Player> library;
    private UserManager userManager;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
        PacketEvents.getAPI().getEventManager().registerListener(new MenuListener(), PacketListenerPriority.LOWEST);
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();
        this.library = new SunscreenLibrarySpigot(this);
        SunscreenLibrary.Holder.INSTANCE = library;
        Passport.Holder.INSTANCE = library.passport();
        this.userManager = new UserManager(this);
        Lamp<?> lamp = BukkitLamp.builder(this)
                .commandCondition(CustomCondition.INSTANCE)
                .parameterTypes(builder -> {
                    builder.addParameterType(SunscreenUser.class, new UserParameterType());
                    builder.addParameterType(Identifier.class, new IdentifierParameterType());
                })
                .suggestionProviders(builder -> builder.addProvider(SunscreenUser.class, (context) -> userManager.users().stream().map(User::name).toList()))
                .build();
        lamp.register(new SunscreenCommand());
        this.getDataFolder().mkdirs();
        library.passport().placeholders().register(new PapiPlaceholderProvider());
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    public UserManager userManager() {
        return userManager;
    }


}
