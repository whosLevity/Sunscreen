package me.combimagnetron.sunscreen.event;

import me.combimagnetron.passport.event.Event;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public record FinishTextInputEvent<T extends Element<?>>(T element, SunscreenUser<?> user, String input) implements Event {
    @Override
    public Class<? extends Event> eventType() {
        return FinishTextInputEvent.class;
    }
}
