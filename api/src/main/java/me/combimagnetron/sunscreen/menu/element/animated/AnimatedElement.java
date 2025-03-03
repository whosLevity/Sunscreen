package me.combimagnetron.sunscreen.menu.element.animated;

import me.combimagnetron.sunscreen.menu.element.Element;

import java.util.Collection;

public interface AnimatedElement extends Element {

    Collection<Keyframe> keyframes();

    AnimatedElement keyframe(Keyframe keyframe);

    AnimatedElement keyframes(Collection<Keyframe> keyframes);

    AnimatedElement keyframes(Keyframe... keyframes);

}
