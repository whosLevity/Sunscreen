package me.combimagnetron.sunscreen.image.effect;

import me.combimagnetron.sunscreen.image.Canvas;

import java.awt.image.BufferedImage;

public class GreyscaleEffect implements Effect {

    @Override
    public Canvas apply(Canvas canvas) {
        if (!(canvas instanceof Canvas.StaticImpl impl)) {
            throw new IllegalArgumentException("Canvas must be a StaticImpl");
        }
        BufferedImage original = impl.image();
        BufferedImage greyscale = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        greyscale.getGraphics().drawImage(original, 0, 0, null);
        return Canvas.image(greyscale);
    }
}
