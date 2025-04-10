package me.combimagnetron.sunscreen.command;

import me.combimagnetron.generated.R1_21_4.item.Material_1_21_4;
import me.combimagnetron.passport.internal.entity.impl.display.ItemDisplay;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.menu.AspectRatioMenu;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.div.Div;
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
        builder.append(Component.text("Opens the aspect ratio menu.", TextColor.color(143, 211, 255)));
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
            actor.reply("You don't have a menu opened.");
            return;
        }
        Menu menu = session.menu();
        if (!(menu instanceof Menu.FloatImpl impl)) {
            actor.reply("You don't the right menu opened. Contact and administrator.");
            return;
        }
        DebugElement element = new DebugElement(Vec2d.of(100, 100), Identifier.of("debug"), Position.pixel(0,0), sunscreenUser);
        Div debugDiv = Div.div(Identifier.of("internal", "debug")).position(Position.position(sunscreenUser).x().percentage(50).back().y().percentage(50).back().finish());
        debugDiv.add(element);
        impl.div(debugDiv);
        impl.render(debugDiv);
    }

    @Subcommand("help")
    public void help(CommandActor actor) {
        sendHelp(actor);
    }

    @Subcommand("reload")
    public void reload(CommandActor actor) {

    }

    @Subcommand("open")
    public void open(CommandActor actor, Identifier identifier) {
        SunscreenUser<Audience> sunscreenUser = SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null);
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
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
                actor.reply("You already have a menu opened.");
                return;
            }
        }
        SunscreenLibrary.library().menuTicker().start(new EditorMenu(sunscreenUser));
    }

    @Subcommand("list")
    public void list(CommandActor actor) {

    }

    @Subcommand({"aspectratio", "resolution"})
    public void aspectRatio(CommandActor actor, @Optional SunscreenUser<?> user) {
        SunscreenUser<Audience> sunscreenUser = user == null ? SunscreenLibrary.library().users().user(actor.uniqueId()).orElse(null) : (SunscreenUser<Audience>) user;
        if (sunscreenUser == null) {
            actor.reply("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        if (sunscreenUser.session() != null) {
            actor.reply("You already have a menu opened.");
            return;
        }
        new AspectRatioMenu(sunscreenUser);
    }

}
