package me.combimagnetron.sunscreen.style;

import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;

import java.awt.image.BufferedImage;

public class ColorStyle implements Style<Color> {

    @Override
    public Canvas edit(Canvas canvas, Position position, Color color) {
        Canvas.InternalCanvas internalCanvas = (Canvas.InternalCanvas) canvas;
        BufferedImage image = internalCanvas.image();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                if (alpha != 0) {
                    int newPixel = (alpha << 24) | (color.red() << 16) | (color.green() << 8) | color.blue();
                    image.setRGB(x, y, newPixel);
                }
            }
        }
        return Canvas.image(image);
    }

    public static ColorStyle colorStyle() {
        return new ColorStyle();
    }

}
