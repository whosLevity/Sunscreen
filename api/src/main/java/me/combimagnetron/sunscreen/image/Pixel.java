package me.combimagnetron.sunscreen.image;

import me.combimagnetron.sunscreen.util.Vec2d;

public record Pixel(int x, int y, Color color) {

    public static Pixel of(int x, int y, Color color) {
        return new Pixel(x, y, color);
    }

    public static Pixel of(int x, int y, int rgb) {
        return new Pixel(x, y, Color.of(rgb));
    }

    public static Pixel of(Vec2d pos, Color color) {
        return new Pixel(pos.xi(), pos.yi(), color);
    }

}
