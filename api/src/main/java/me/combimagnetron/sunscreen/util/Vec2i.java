package me.combimagnetron.sunscreen.util;

public record Vec2i(int x, int y) {

    public static Vec2i of(int x, int y) {
        return new Vec2i(x, y);
    }

    public Vec2i mul(int factor) {
        return new Vec2i(this.x * factor, this.y * factor);
    }

    public Vec2i mul(int x, int y) {
        return new Vec2i(this.x * x, this.y * y);
    }

    public Vec2i mul(Vec2i vec2d) {
        return mul(vec2d.x, vec2d.y);
    }

    public Vec2i div(int factor) {
        return new Vec2i(this.x / factor, this.y / factor);
    }

    public Vec2i div(int x, int y) {
        return new Vec2i(this.x / x, this.y / y);
    }

    public Vec2i div(Vec2i vec2d) {
        return div(vec2d.x, vec2d.y);
    }

    public Vec2i add(int factor) {
        return new Vec2i(this.x + factor, this.y + factor);
    }

    public Vec2i add(int x, int y) {
        return new Vec2i(this.x + x, this.y + y);
    }

    public Vec2i add(Vec2i vec2d) {
        return add(vec2d.x, vec2d.y);
    }

    public Vec2i sub(int factor) {
        return new Vec2i(this.x - factor, this.y - factor);
    }

    public Vec2i sub(int x, int y) {
        return new Vec2i(this.x - x, this.y - y);
    }

    public Vec2i sub(Vec2i vec2d) {
        return sub(vec2d.x, vec2d.y);
    }

}