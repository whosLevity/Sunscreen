package me.combimagnetron.sunscreen.element.animated;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.element.Element;

import java.util.Collection;

public interface AnimatedElement extends Element<Canvas> {

    Collection<Keyframe> keyframes();

    void next();

    Keyframe current();

    Keyframe keyframe(int index);

    void loopMode(LoopMode mode);

    LoopMode loopMode();

    AnimatedElement keyframe(Keyframe keyframe);

    AnimatedElement keyframes(Collection<Keyframe> keyframes);

    AnimatedElement keyframes(Keyframe... keyframes);

    enum LoopMode {
        NONE,
        HOLD,
        LOOP,
        REVERSE
    }

}
