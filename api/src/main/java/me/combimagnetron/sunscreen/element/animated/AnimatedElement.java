package me.combimagnetron.sunscreen.element.animated;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.element.Element;

import java.util.Collection;

public interface AnimatedElement extends Element<Canvas> {

    Collection<Keyframe> keyframes();

    AnimatedElement keyframe(Keyframe keyframe);

    AnimatedElement keyframes(Collection<Keyframe> keyframes);

    AnimatedElement keyframes(Keyframe... keyframes);

}
