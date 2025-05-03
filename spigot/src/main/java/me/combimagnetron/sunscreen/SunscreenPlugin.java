package me.combimagnetron.sunscreen;

import co.aikar.commands.PaperCommandManager;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import me.combimagnetron.passport.Passport;
import me.combimagnetron.sunscreen.action.RunCommandAction;
import me.combimagnetron.sunscreen.command.SunscreenCommand;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.hook.betterhud.BetterHudSunscreenHook;
import me.combimagnetron.sunscreen.hook.mythichud.MythicHudSunscreenHook;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.menu.MenuTemplate;
import me.combimagnetron.sunscreen.menu.listener.AnvilListener;
import me.combimagnetron.sunscreen.menu.listener.MenuListener;
import me.combimagnetron.sunscreen.placeholder.PapiPlaceholderProvider;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.user.UserManager;
import me.combimagnetron.sunscreen.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.Collection;

public class SunscreenPlugin extends JavaPlugin {
    private SunscreenLibrary<SunscreenPlugin, Player> library;
    private UserManager userManager;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
        PacketEvents.getAPI().getEventManager().registerListener(new MenuListener(), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new AnvilListener(), PacketListenerPriority.LOWEST);
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();
        this.library = new SunscreenLibrarySpigot(this);
        SunscreenLibrary.Holder.INSTANCE = library;
        Passport.Holder.INSTANCE = library.passport();
        this.userManager = new UserManager(this);
        commands();
        menus();
        platformSpecific();
    }

    private void platformSpecific() {
        Action.ACTION_MAP.put(RunCommandAction.ActionIdentifier, new RunCommandAction());
        SunscreenHook.HOOKS.add(new MythicHudSunscreenHook());
        SunscreenHook.HOOKS.add(new BetterHudSunscreenHook());
        library.passport().placeholders().register(new PapiPlaceholderProvider());
    }

    private void menus() {
        this.getDataFolder().mkdirs();
        Collection<MenuTemplate> templates = library.menuConfigTransformer().read(getDataFolder().toPath().resolve(Path.of("menus")));
        library.logger().info("Loaded {} menu(s).", templates.size());
        for (MenuTemplate template : templates) {
            library.menuRegistry().register(template);
        }
    }

    private void commands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.getCommandContexts().registerContext(Identifier.class, (bukkitCommandExecutionContext -> Identifier.split(bukkitCommandExecutionContext.popFirstArg())));
        manager.getCommandContexts().registerContext(SunscreenUser.class, (bukkitCommandExecutionContext) -> {
            String name = bukkitCommandExecutionContext.popFirstArg();
            return userManager.users().stream().filter(user -> user.name().equalsIgnoreCase(name)).findFirst().orElseThrow(() -> new IllegalArgumentException("User not found"));
        });
        manager.enableUnstableAPI("brigadier");
        manager.getCommandCompletions().registerAsyncCompletion("users", (bukkitCommandCompletionContext) -> userManager.users().stream().map(SunscreenUser::name).filter(name -> name.startsWith(bukkitCommandCompletionContext.getInput())).toList());
        manager.getCommandCompletions().registerAsyncCompletion("menus", (bukkitCommandCompletionContext) -> library.menuRegistry().all().stream().map(menu -> menu.identifier().string()).filter(name -> name.startsWith(bukkitCommandCompletionContext.getInput())).toList());
        manager.registerCommand(new SunscreenCommand());
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    public UserManager userManager() {
        return userManager;
    }


}
