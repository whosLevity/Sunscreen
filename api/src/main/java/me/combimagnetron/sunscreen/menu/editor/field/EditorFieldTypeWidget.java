package me.combimagnetron.sunscreen.menu.editor.field;

import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.editor.EditableType;

public abstract class EditorFieldTypeWidget<T extends EditableType> implements Interactable {
    private final WidgetContext widgetContext;

    protected EditorFieldTypeWidget(WidgetContext widgetContext) {
        this.widgetContext = widgetContext;
    }

    public abstract T value();

    public abstract Canvas render();

    public WidgetContext widgetContext() {
        return widgetContext;
    }

}
