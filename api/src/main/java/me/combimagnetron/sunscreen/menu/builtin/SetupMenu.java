package me.combimagnetron.sunscreen.menu.builtin;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.div.Div;
import me.combimagnetron.sunscreen.menu.element.impl.ButtonElement;
import me.combimagnetron.sunscreen.menu.element.impl.ImageElement;
import me.combimagnetron.sunscreen.menu.element.impl.SelectorElement;
import me.combimagnetron.sunscreen.menu.element.impl.TextElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public class SetupMenu extends Menu.Float {
    private final Canvas spriteSheet = Canvas.image(Canvas.ImageProvider.url("https://i.imgur.com/6tJNlmq.png"));
    private final SunscreenUser<?> viewer;

    public SetupMenu(SunscreenUser<?> viewer) {
        super(viewer);
        this.viewer = viewer;
        build();
        open(viewer);
    }

    private void build() {
        background(Color.of(62, 53, 70));
        Div test = Div.div(Identifier.of("test")).size(Vec2d.of(76, 76)).position(Position.position(viewer).x().pixel(0).back().y().pixel(0).back().finish())
                .add(TextElement.textElement(Identifier.of("test", "label"), Position.pixel(0, 0), Text.text("Test"))
                        .style(Style.color(), Color.of(255, 255, 255))
                );
        div(test);
        Div main = Div.div(Identifier.of("main")).size(Vec2d.of(240, 140))
                .add(
                        ImageElement.imageElement(
                                spriteSheet.sub(Vec2d.of(240, 140), Vec2d.of(0, 0)),
                                Identifier.of("main", "bg"),
                                Position.pixel(0, 0)
                        )
                ).add(
                        TextElement.textElement(
                                Identifier.of("main", "label"),
                                Position.pixel(3, 3),
                                Text.text("Please select your\nlanguage", Text.Font.vanilla())
                        ).style(Style.color(), Color.of(155, 171, 178))
                ).add(
                        TextElement.textElement(
                                Identifier.of("main", "instructions"),
                                Position.pixel(129, 3),
                                Text.text("Welcome\n" + viewer.name() + "!\nBefore you can start\nplaying you'll have to\nadjust some settings\nto make your stay\neven better! You'll be\nguided through this\nprocess by clicking\non continue..")
                        ).style(Style.color(), Color.of(155, 171, 178))
                ).add(
                        TextElement.textElement(
                                Identifier.of("main", "continue"),
                                Position.pixel(129, 125),
                                Text.text("Continue")
                        ).style(Style.color(), Color.of(155, 171, 178))
                ).add(SelectorElement.selectorElement(Vec2d.of(108, 12), Identifier.of("main", "selector"), Position.pixel(6, 26))
                        .button(
                                ButtonElement.buttonElement(Vec2d.of(115, 12), Identifier.of("selector", "english"), Position.pixel(0, 0)).hover(spriteSheet.sub(Vec2d.of(115, 12), Vec2d.of(115, 140))).standard(spriteSheet.sub(Vec2d.of(115, 12), Vec2d.of(0, 140))).build()
                        )
                        .button(
                                ButtonElement.buttonElement(Vec2d.of(115, 12), Identifier.of("selector", "spanish"), Position.pixel(0, 14)).hover(spriteSheet.sub(Vec2d.of(115, 12), Vec2d.of(115, 154))).standard(spriteSheet.sub(Vec2d.of(115, 12), Vec2d.of(0, 154))).build()
                        )
                        .button(
                                ButtonElement.buttonElement(Vec2d.of(115, 12), Identifier.of("selector", "german"), Position.pixel(0, 28)).hover(spriteSheet.sub(Vec2d.of(115, 12), Vec2d.of(115, 168))).standard(spriteSheet.sub(Vec2d.of(115, 12), Vec2d.of(0, 168))).build()
                        )
                        .button(
                                ButtonElement.buttonElement(Vec2d.of(115, 12), Identifier.of("selector", "dutch"), Position.pixel(0, 42)).hover(spriteSheet.sub(Vec2d.of(115, 12), Vec2d.of(115, 182))).standard(spriteSheet.sub(Vec2d.of(115, 12), Vec2d.of(0, 182))).build()
                        ).build()
                );
        Position position = Position.position(viewer).x().percentage(50).pixel(-main.size().x() * 0.5).back().y().percentage(50).pixel(-main.size().y() * 0.5).back().finish();
        main.position(position);
        div(main);
    }

}
