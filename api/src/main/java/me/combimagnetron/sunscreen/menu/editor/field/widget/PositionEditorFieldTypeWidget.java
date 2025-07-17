package me.combimagnetron.sunscreen.menu.editor.field.widget;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.editor.field.EditorFieldTypeWidget;
import me.combimagnetron.sunscreen.menu.editor.field.WidgetContext;
import me.combimagnetron.sunscreen.menu.editor.menu.EditorMenu;
import me.combimagnetron.sunscreen.util.HoverHelper;
import me.combimagnetron.sunscreen.util.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PositionEditorFieldTypeWidget extends EditorFieldTypeWidget<Position.PositionBuilder> {
    private Position.PositionBuilder builder;

    protected PositionEditorFieldTypeWidget(WidgetContext widgetContext) {
        super(widgetContext);
    }

    @Override
    public Position.PositionBuilder value() {
        return builder;
    }

    @Override
    public Canvas render() {
        Size size = widgetContext().size();
        Canvas canvas = Canvas.image(size);
        canvas = canvas.fill(Vec2i.of(0, 0), size.vec2i(), EditorMenu.Colors.Secondary);
        canvas = canvas.fill(Vec2i.of(0, 0), Vec2i.of(size.x().pixel(), 10), EditorMenu.Colors.Tertiary);
        canvas = canvas.fill(Vec2i.of(1, 11), Vec2i.of(size.x().pixel() - 2, 10), EditorMenu.Colors.Tertiary);
        return canvas;
    }

    @Override
    public boolean reactiveToHover() {
        return true;
    }

    @Override
    public boolean reactiveToClick() {
        return true;
    }

    @Override
    public boolean hover(Vec2i pos) {
        if (pos == null) {
            return false;
        }
        if (!HoverHelper.isHovered(pos, Vec2i.zero(), widgetContext().size())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean click(Vec2i pos) {
        return false;
    }

    @Override
    public @NotNull Map<ActionType, ActionWrapper> actions() {
        return Map.of();
    }
}
