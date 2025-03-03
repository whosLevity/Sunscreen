package me.combimagnetron.sunscreen.image;

import me.combimagnetron.sunscreen.util.Scheduler;
import me.combimagnetron.sunscreen.util.Vec2d;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;

public class SimpleCanvas {
    private static final String NAMESPACE = "sunscreen";
    private final BufferedImage bufferedImage;
    private final TextComponent[][] image;
    private final int width;
    private final int height;

    protected SimpleCanvas(int width, int height) {
        this.bufferedImage = new BufferedImage(width, height, 2);
        image = (TextComponent[][]) Array.newInstance(TextComponent.class, width, height);
        this.width = width;
        this.height = height;
    }

    public SimpleCanvas(int width, int height, BufferedImage image) {
        this.bufferedImage = image;
        this.image = (TextComponent[][]) Array.newInstance(TextComponent.class, height, width);
        this.width = width;
        this.height = height;
    }


    public Vec2d dimensions() {
        return Vec2d.of(width, height);
    }

    public SimpleCanvas splice(Vec2d size, Vec2d coords) {
        final BufferedImage spliced = bufferedImage.getSubimage((int)coords.x(), (int)coords.y(), (int)size.x(), (int)size.y());
        return new SimpleCanvas((int)size.x(), (int)size.y(), spliced);
    }

    public BufferedImage image() {
        return bufferedImage;
    }


    public static SimpleCanvas empty(Vec2d size) {
        return new SimpleCanvas((int) size.x(), (int) size.y());
    }

    public Component render() {
        Component component = Component.empty();
        for (int row = image.length - 1; row > 0; row--) {
            for (int col = 0; col < image[row].length; col++) {
                component = component.append(image[row][col]).append(Component.text("x")).font(Key.key(NAMESPACE + ":dynamic"));
            }
            component = component.append(Component.text("z").font(Key.key(NAMESPACE + ":dynamic")));
        }
        return component;
    }

    public Component renderAsync() {
        return Scheduler.async(this::render);
    }

    static SimpleCanvas image(BufferedImage image) {
        SimpleCanvas simpleCanvas = new SimpleCanvas(image.getWidth(), image.getHeight(), image);
        SampleColor lastColor = SampleColor.of(0, 0, 0);
        int i = 57344;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                java.awt.Color c = new java.awt.Color(image.getRGB(x, y), true);
                TextColor color = TextColor.color(c.getRGB());
                SampleColor currentColor = SampleColor.of(color);
                if (c.getAlpha() == 0) {
                    simpleCanvas.image[x][y] = Component.text(' ');
                /*} else if (currentColor.skip(lastColor)) {
                    simpleCanvas.image[x][y] = Component.text((char) i);*/
                } else {
                    simpleCanvas.image[x][y] = Component.text((char) i, color);
                    lastColor = currentColor;
                }
                i++;
            }
            i = 57344;
        }
        return simpleCanvas;
    }

    record SampleColor(int r, int g, int b) {

        public static SampleColor of(int r, int g, int b) {
            return new SampleColor(r, g, b);
        }

        public static SampleColor of(TextColor textColor) {
            return new SampleColor(textColor.red(), textColor.green(), textColor.blue());
        }

        public java.awt.Color color() {
            return new java.awt.Color(r, g, b);
        }

        public boolean skip(SampleColor other) {
            //return isClose(other, 0);
            if (other.r == 256 && other.g == 256 && other.b == 256) {
                return true;
            }

            return similar(color(), other.color(), 0);
        }

        private boolean isClose(SampleColor sampleColor, int threshold) {
            var external = sampleColor.color();
            var local = color();
            int r = external.getRed() - local.getRed(), g = external.getGreen() - local.getGreen(), b = external.getBlue()- local.getBlue();
            return (r*r + g*g + b*b) <= threshold*threshold;
        }

        public static boolean similar(java.awt.Color color1, java.awt.Color color2, int threshold) {
            int redDiff = Math.abs(color1.getRed() - color2.getRed());
            int greenDiff = Math.abs(color1.getGreen() - color2.getGreen());
            int blueDiff = Math.abs(color1.getBlue() - color2.getBlue());

            int totalDifference = redDiff + greenDiff + blueDiff;

            return totalDifference <= threshold;
        }

    }



}
