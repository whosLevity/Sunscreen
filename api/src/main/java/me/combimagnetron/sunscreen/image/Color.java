package me.combimagnetron.sunscreen.image;

import net.kyori.adventure.text.format.TextColor;

public interface Color {
    Color WHITE = of(255, 255, 255);
    Color LIGHT_GRAY = of(192, 192, 192);
    Color GRAY = of(128, 128, 128);
    Color DARK_GRAY = of(64, 64, 64);
    Color BLACK = of(0, 0, 0);
    Color RED = of(255, 0, 0);
    Color PINK = of(255, 175, 175);
    Color ORANGE = of(255, 200, 0);
    Color YELLOW = of(255, 255, 0);
    Color GREEN = of(0, 255, 0);
    Color MAGENTA = of(255, 0, 255);
    Color CYAN = of(0, 255, 255);
    Color BLUE = of(0, 0, 255);
    Color TRANSPARENT = of(0, 0, 0, 0);

    static Color white() {
        return WHITE;
    }

    static Color lightGray() {
        return LIGHT_GRAY;
    }

    static Color gray() {
        return GRAY;
    }

    static Color darkGray() {
        return DARK_GRAY;
    }

    static Color black() {
        return BLACK;
    }

    static Color red_() {
        return RED;
    }

    static Color pink() {
        return PINK;
    }

    static Color orange() {
        return ORANGE;
    }

    static Color yellow() {
        return YELLOW;
    }

    static Color green_() {
        return GREEN;
    }

    static Color magenta() {
        return MAGENTA;
    }

    static Color cyan() {
        return CYAN;
    }

    static Color blue_() {
        return BLUE;
    }

    static Color transparent() {
        return TRANSPARENT;
    }

    /**
     * @return the red value of this color
     */
    int red();

    /**
     * @return the green value of this color
     */
    int green();

    /**
     * @return the blue value of this color
     */
    int blue();

    /**
     * @return the alpha value of this color
     */
    int alpha();

    /**
     * @return the rgb representation of this color
     */
    int rgb();

    int rgba();

    /**
     * @return the text color representation of this color
     */
    TextColor textColor();

    /**
     * @param color the text color to convert to a color
     * @return TextColor converted to a Color
     */
    static Color of(TextColor color) {
        return Impl.of(color);
    }

    /**
     * @param red int value between 0 and 255, representing the red value of the color
     * @param green int value between 0 and 255, representing the green value of the color
     * @param blue int value between 0 and 255, representing the blue value of the color
     * @param alpha int value between 0 and 255, representing the alpha value of the color
     * @return a new color with the given red, green, blue, and alpha values
     */
    static Color of(int red, int green, int blue, int alpha) {
        return Impl.of(red, green, blue, alpha);
    }

    /**
     * @param red int value between 0 and 255, representing the red value of the color
     * @param green int value between 0 and 255, representing the green value of the color
     * @param blue int value between 0 and 255, representing the blue value of the color
     * @return a new color with the given red, green, and blue values
     */
    static Color of(int red, int green, int blue) {
        return Impl.of(red, green, blue);
    }

    /**
     * @param rgb int value representing the red, green, and blue values of the color
     * @return a new color with the given red, green, and blue values
     */
    static Color of(int rgb) {
        return Impl.of(rgb);
    }

    /**
     * @param rgb int value representing the red, green, and blue values of the color
     * @param alpha int value between 0 and 255, representing the alpha value of the color
     * @return a new color with the given red, green, blue, and alpha values
     */
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
        public int rgba() {
            return (int) (((long)alpha << 24) | (red << 16) | (green << 8) | blue);
        }

        @Override
        public TextColor textColor() {
            return TextColor.color(red, green, blue);
        }
    }

}
