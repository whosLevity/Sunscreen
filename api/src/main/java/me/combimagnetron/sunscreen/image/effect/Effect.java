package me.combimagnetron.sunscreen.image.effect;

import me.combimagnetron.sunscreen.image.Canvas;

public interface Effect {

    Canvas apply(Canvas canvas);

    static Effect greyscale() {
        return new GreyscaleEffect();
    }

}
