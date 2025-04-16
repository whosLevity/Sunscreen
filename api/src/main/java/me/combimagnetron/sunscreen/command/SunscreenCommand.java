package me.combimagnetron.sunscreen.command;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.config.MenuConfigTransformer;
import me.combimagnetron.sunscreen.menu.*;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.registry.MenuRegistry;
import me.combimagnetron.sunscreen.menu.timing.DebugElement;
import me.combimagnetron.sunscreen.session.Session;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.CommandPlaceholder;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.command.CommandActor;

import java.nio.file.Path;
import java.util.Collection;

@Command({"sunscreen", "ss", "screen"})
public class SunscreenCommand {

    @CommandPlaceholder
    public void helpP(CommandActor actor) {
        sendHelp(actor);
    }

    private void sendHelp(CommandActor actor) {
        SunscreenUser<Audience> sunscreenUser = SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null);
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        ComponentBuilder<TextComponent, TextComponent.Builder> builder = Component.text();
        builder.append(Component.text("Sunscreen Commands").style(Style.style().decorate(TextDecoration.BOLD)).color(TextColor.color(230, 144, 78)));
        builder.append(Component.newline());
        builder.append(Component.text("/sunscreen resolution|aspectratio").style(Style.style().decorate(TextDecoration.UNDERLINED).color(TextColor.color(77, 155, 230))
                .clickEvent(ClickEvent.runCommand("/sunscreen resolution " + sunscreenUser.name()))
                .hoverEvent(HoverEvent.showText(Component.text("Click to run").color(NamedTextColor.GRAY)))));
        builder.append(Component.space());
        builder.append(Component.text("Opens the aspect ratio openedMenu.", TextColor.color(143, 211, 255)));
        sunscreenUser.message(builder.build());
    }

    @Subcommand("debug")
    public void debug(CommandActor actor) {
        SunscreenUser<Audience> sunscreenUser = SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null);
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        Session session = sunscreenUser.session();
        if (session == null || session.menu() == null) {
            actor.reply("You don't have a openedMenu opened.");
            return;
        }
        OpenedMenu openedMenu = session.menu();
        if (!(openedMenu instanceof OpenedMenu.FloatImpl impl)) {
            actor.reply("You don't the right openedMenu opened. Contact and administrator.");
            return;
        }
        DebugElement element = new DebugElement(Size.pixel(100, 100), Identifier.of("debug"), Position.pixel(0,0), sunscreenUser);
        Div debugDiv = Div.div(Identifier.of("internal", "debug")).position(Position.position().x().percentage(50).back().y().percentage(50).back());
        debugDiv.add(element);
        //impl.div(debugDiv);
        impl.render(debugDiv);
    }

    @Subcommand("help")
    public void help(CommandActor actor) {
        sendHelp(actor);
    }

    @Subcommand("reload")
    public void reload(CommandActor actor) {
        SunscreenUser<Audience> sunscreenUser = SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null);
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        MenuRegistry menuRegistry = SunscreenLibrary.library().menuRegistry();
        menuRegistry.clear();
        MenuConfigTransformer menuConfigTransformer = SunscreenLibrary.library().menuConfigTransformer();
        Collection<MenuTemplate> templates = SunscreenLibrary.library().menuConfigTransformer().read(SunscreenLibrary.library().path().resolve(Path.of("menus")));
        SunscreenLibrary.library().logger().info("Loaded {} menus", templates.size());
        for (MenuTemplate template : templates) {
            SunscreenLibrary.library().menuRegistry().register(template);
        }
        sunscreenUser.message(Component.text(menuRegistry.all().size() + " menus loaded."));
    }

    @Subcommand("open")
    public void open(CommandActor actor, Identifier identifier) {
        SunscreenUser<Audience> sunscreenUser = SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null);
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        MenuTemplate template = SunscreenLibrary.library().menuRegistry().get(identifier);
        sunscreenUser.open(template);
    }

    @Subcommand({"editor", "edit", "e", "maker", "create"})
    public void editor(CommandActor actor, @Optional Identifier identifier) {
        SunscreenUser<Audience> sunscreenUser = SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null);
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        if (sunscreenUser.screenSize() == null) {
            TextComponent.Builder builder = Component.text();
            builder.append(Component.text("You need to set a screen size before opening the editor ", TextColor.color(232, 59, 59)));
            builder.append(Component.text("/sunscreen resolution").style(Style.style().decorate(TextDecoration.UNDERLINED).color(TextColor.color(77, 155, 230))
                            .clickEvent(ClickEvent.runCommand("/sunscreen resolution " + sunscreenUser.name()))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to run").color(NamedTextColor.GRAY)))));
            sunscreenUser.message(
                    builder.build()
            );
            return;
        }
        if (sunscreenUser.session() != null) {
            if (sunscreenUser.session().menu() != null) {
                actor.reply("You already have a openedMenu opened.");
                return;
            }
        }
        OpenedMenu.FloatImpl menu = new EditorMenu(sunscreenUser);
        SunscreenLibrary.library().menuTicker().start(menu);
        Session session = Session.session(menu, sunscreenUser);
        SunscreenLibrary.library().sessionHandler().session(session);
    }

    @Subcommand("list")
    public void list(CommandActor actor) {
        SunscreenUser<Audience> sunscreenUser = SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null);
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        MenuRegistry menuRegistry = SunscreenLibrary.library().menuRegistry();
        sunscreenUser.message(Component.text(menuRegistry.all().size() + " menus loaded."));
    }

    @Subcommand({"aspectratio", "resolution"})
    public void aspectRatio(CommandActor actor, @Optional SunscreenUser<?> user) {
        SunscreenUser<Audience> sunscreenUser = user == null ? SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null) : (SunscreenUser<Audience>) user;
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        if (sunscreenUser.session() != null) {
            actor.reply("You already have a openedMenu opened.");
            return;
        }
        new AspectRatioMenu(sunscreenUser);
    }

}
