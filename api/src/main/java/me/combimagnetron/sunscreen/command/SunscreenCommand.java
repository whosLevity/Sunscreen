package me.combimagnetron.sunscreen.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.*;
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

import java.nio.file.Path;
import java.util.Collection;

@CommandAlias("sunscreen|screen|ss")
public class SunscreenCommand extends BaseCommand {

    @Default
    @Syntax("help")
    public void helpP(CommandIssuer actor) {
        sendHelp(actor);
    }

    private static SunscreenUser<Audience> sunscreenUser(CommandIssuer actor) {
        SunscreenUser<Audience> sunscreenUser = SunscreenLibrary.library().users().user(actor.getUniqueId()).orElse(null);
        if (sunscreenUser == null) {
            actor.sendMessage("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        return sunscreenUser;
    }

    private void sendHelp(CommandIssuer actor) {
        SunscreenUser<Audience> sunscreenUser = sunscreenUser(actor);
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
    public void debug(CommandIssuer actor) {
        SunscreenUser<Audience> sunscreenUser = sunscreenUser(actor);
        Session session = sunscreenUser.session();
        if (session == null || session.menu() == null) {
            actor.sendMessage("You don't have a menu opened.");
            return;
        }
        OpenedMenu openedMenu = session.menu();
        if (!(openedMenu instanceof OpenedMenu.FloatImpl impl)) {
            actor.sendMessage("You don't the have the right menu type specified. Contact and administrator.");
            return;
        }
        DebugElement element = new DebugElement(Size.pixel(100, 100), Identifier.of("debug"), Position.pixel(0,0), sunscreenUser);
        Div debugDiv = Div.div(Identifier.of("internal", "debug")).position(Position.position().x().percentage(50).back().y().percentage(50).back());
        debugDiv.add(element);
        //impl.div(debugDiv);
        impl.render(debugDiv);
    }

    @Subcommand("reload")
    public void reload(CommandIssuer actor) {
        SunscreenUser<Audience> sunscreenUser = sunscreenUser(actor);
        MenuRegistry menuRegistry = SunscreenLibrary.library().menuRegistry();
        menuRegistry.clear();
        MenuConfigTransformer menuConfigTransformer = SunscreenLibrary.library().menuConfigTransformer();
        Collection<MenuTemplate> templates = menuConfigTransformer.read(SunscreenLibrary.library().path().resolve(Path.of("menus")));
        SunscreenLibrary.library().logger().info("Loaded {} menu(s).", templates.size());
        for (MenuTemplate template : templates) {
            SunscreenLibrary.library().menuRegistry().register(template);
        }
        sunscreenUser.message(Component.text(menuRegistry.all().size() + " menu(s) loaded.").color(TextColor.color(143, 211, 255)));
    }

    @Subcommand("open")
    @Syntax("<menu>")
    @CommandCompletion("@menus")
    public void open(CommandIssuer actor, Identifier identifier) {
        SunscreenUser<Audience> sunscreenUser = sunscreenUser(actor);
        MenuTemplate template = SunscreenLibrary.library().menuRegistry().get(identifier);
        sunscreenUser.open(template);
    }

    @Subcommand("editor")
    @CommandAlias("edit")
    public void editor(CommandIssuer actor, @Optional Identifier identifier) {
        SunscreenUser<Audience> sunscreenUser = sunscreenUser(actor);
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
                actor.sendMessage("You already have a openedMenu opened.");
                return;
            }
        }
        OpenedMenu.FloatImpl menu = new EditorMenu(sunscreenUser);
        SunscreenLibrary.library().menuTicker().start(menu);
        Session session = Session.session(menu, sunscreenUser);
        SunscreenLibrary.library().sessionHandler().session(session);
    }
    
    @Subcommand("list")
    public void list(CommandIssuer actor) {
        SunscreenUser<Audience> sunscreenUser = sunscreenUser(actor);
        MenuRegistry menuRegistry = SunscreenLibrary.library().menuRegistry();
        final Component line = Component.text("-".repeat(15), Style.style(TextColor.color(230, 144, 78), TextDecoration.STRIKETHROUGH));
        ComponentBuilder<TextComponent, TextComponent.Builder> builder = Component.text();
        builder.append(Component.text(menuRegistry.all().size() + " menu(s) loaded.").style(Style.style().decoration(TextDecoration.BOLD, true)).color(TextColor.color(143, 211, 255)), Component.newline(), line);
        for (MenuTemplate template : menuRegistry.all()) {
            builder.append(Component.newline()).append(Component.text(template.identifier().string())
                    .style(Style.style().decorate(TextDecoration.UNDERLINED).color(TextColor.color(77, 155, 230))
                            .clickEvent(ClickEvent.runCommand("/sunscreen open " + template.identifier().string()))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to open").color(NamedTextColor.GRAY)))));
        }
        builder.append(Component.newline(), line, Component.newline(), Component.text("Open overview of all menus.", Style.style(TextColor.color(205, 104, 61), TextDecoration.UNDERLINED).hoverEvent(HoverEvent.showText(Component.text("Click to open").color(NamedTextColor.GRAY))).clickEvent(ClickEvent.runCommand("/sunscreen overview"))));
        sunscreenUser.message(builder.build());
    }

    @Subcommand("aspectratio")
    @CommandAlias("resolution")
    @CommandCompletion("@users")
    @Syntax("<user>")
    public void aspectRatio(CommandIssuer actor, @Optional SunscreenUser<?> user) {
        SunscreenUser<Audience> sunscreenUser = user == null ? SunscreenLibrary.library().users().user(actor.getUniqueId()).orElse(null) : (SunscreenUser<Audience>) user;
        if (sunscreenUser == null) {
            actor.sendMessage("Something went wrong, please contact support.");
            throw new IllegalArgumentException("User not found");
        }
        if (sunscreenUser.session().menu() != null) {
            actor.sendMessage("You already have a menu opened.");
            return;
        }
        new AspectRatioMenu(sunscreenUser);
    }

}
