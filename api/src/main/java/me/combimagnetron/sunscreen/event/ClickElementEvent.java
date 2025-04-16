package me.combimagnetron.sunscreen.event;

import me.combimagnetron.passport.event.Event;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.util.Vec2d;

public record ClickElementEvent<T extends Element>(T element, Vec2d coords, Input.Type.MouseClick click) implements Event {

    public static <T extends Element> ClickElementEvent<T> create(T element, Vec2d coords, Input.Type.MouseClick click) {
        return new ClickElementEvent<>(element, coords, click);
    }

    @Override
    public Class<? extends Event> eventType() {
        return ClickElementEvent.class;
    }
}
