package me.combimagnetron.sunscreen.element.animated;

import me.combimagnetron.sunscreen.image.Canvas;

public interface Keyframe {

    Canvas canvas();

    long holdTime();

    static Keyframe of(Canvas canvas, long holdTime) {
        return new Impl(canvas, holdTime);
    }

    record Impl(Canvas canvas, long holdTime) implements Keyframe {

    }

}
