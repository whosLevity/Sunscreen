package me.combimagnetron.sunscreen.event;

import me.combimagnetron.passport.event.Event;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.util.Vec2d;

public record HoverElementEvent<T extends Element>(T element, Vec2d coords) implements Event {
    @Override
    public Class<? extends Event> eventType() {
        return HoverElementEvent.class;
    }
}
