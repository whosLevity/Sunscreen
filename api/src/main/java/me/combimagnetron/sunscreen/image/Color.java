package me.combimagnetron.sunscreen.image;

import net.kyori.adventure.text.format.TextColor;

public interface Color {

    int red();

    int green();

    int blue();

    int alpha();

    int rgb();

    TextColor textColor();

    static Color of(int red, int green, int blue, int alpha) {
        return Impl.of(red, green, blue, alpha);
    }

    static Color of(int red, int green, int blue) {
        return Impl.of(red, green, blue);
    }

    static Color of(int rgb) {
        return Impl.of(rgb);
    }

    static Color of(int rgb, int alpha) {
        return Impl.of(rgb, alpha);
    }

    record Impl(int red, int green, int blue, int alpha) implements Color {

        public static Color of(int red, int green, int blue, int alpha) {
            return new Impl(red, green, blue, alpha);
        }

        public static Color of(int red, int green, int blue) {
            return new Impl(red, green, blue, 255);
        }

        public static Color of(int rgb) {
            return new Impl((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, 255);
        }

        public static Color of(int rgb, int alpha) {
            return new Impl((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, alpha);
        }

        public static Color of(TextColor color) {
            return new Impl(color.red(), color.green(), color.blue(), 255);
        }

        @Override
        public int rgb() {
            return (red << 16) | (green << 8) | blue;
        }

        @Override
        public TextColor textColor() {
            return TextColor.color(red, green, blue);
        }
    }

}
