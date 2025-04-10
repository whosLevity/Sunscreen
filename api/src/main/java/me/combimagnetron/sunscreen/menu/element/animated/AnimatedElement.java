package me.combimagnetron.sunscreen.menu.element.animated;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.element.Element;

import java.util.Collection;

public interface AnimatedElement extends Element<Canvas> {

    Collection<Keyframe> keyframes();

    AnimatedElement keyframe(Keyframe keyframe);

    AnimatedElement keyframes(Collection<Keyframe> keyframes);

    AnimatedElement keyframes(Keyframe... keyframes);

}
