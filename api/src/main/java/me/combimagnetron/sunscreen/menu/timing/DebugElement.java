package me.combimagnetron.sunscreen.menu.timing;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public class DebugElement extends SimpleBufferedElement implements Tickable {
    private final SunscreenUser<?> user;
    private Position position = Position.pixel(0, 0);

    public DebugElement(Vec2d size, Identifier identifier, Position position, SunscreenUser<?> user) {
        super(size, identifier, position);
        this.user = user;
    }

    @Override
    public Canvas canvas() {
        Menu menu = user.session().menu();
        if (menu == null) {
            return Canvas.image(size());
        }
        if (!(menu instanceof Menu.FloatImpl floatImpl)) {
            return Canvas.image(size());
        }
        MenuTicker.ExecutingTickable executingTickable = SunscreenLibrary.library().menuTicker().get(floatImpl);
        if (executingTickable == null) {
            return Canvas.image(size());
        }
        Canvas canvas = Canvas.image(size());
        canvas.fill(Vec2d.of(0, 0), size(), EditorMenu.Colors.Background);
        canvas.fill(Vec2d.of(1, 1), size().sub(1, 1), EditorMenu.Colors.Secondary);
        canvas.text(Text.text("Time: " + executingTickable.time() + "ms", Text.Font.vanilla()), Vec2d.of(3, 2), EditorMenu.Colors.PrimaryText);
        canvas.text(Text.text("Time since last tick: " + executingTickable.timeSinceLastTick() + "ms", Text.Font.vanilla()), Vec2d.of(3, 10), EditorMenu.Colors.PrimaryText);
        return canvas;
    }

    @Override
    public Element<Canvas> position(Position pos) {
        this.position = pos;
        return this;
    }

    @Override
    public <T> Element<Canvas> style(Style<T> style, Position pos2D, T t) {
        return this;
    }

    @Override
    public <T> Element<Canvas> style(Style<T> style, T t) {
        return this;
    }

    @Override
    public boolean tick(Tick tick) throws TickFailException {
        canvas();
        return true;
    }
}
