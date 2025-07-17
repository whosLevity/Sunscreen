package me.combimagnetron.sunscreen.menu.editor.menu.element.toolbar;

import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.util.Identifier;

public abstract class ToolbarWidgetElement extends SimpleBufferedElement implements Interactable {

    public ToolbarWidgetElement(Size size, Identifier identifier, Position position) {
        super(size, identifier, position);
    }

}
