package me.combimagnetron.sunscreen.event;

import me.combimagnetron.passport.event.Event;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

public record ClickElementEvent<T extends Element<?>>(T element, SunscreenUser<?> user, Vec2i coords, Input.Type.MouseClick click) implements Event {

    public static <T extends Element<?>> ClickElementEvent<T> create(T element, SunscreenUser<?> user, Vec2i coords, Input.Type.MouseClick click) {
        return new ClickElementEvent<>(element, user, coords, click);
    }

    @Override
    public Class<? extends Event> eventType() {
        return ClickElementEvent.class;
    }
}
