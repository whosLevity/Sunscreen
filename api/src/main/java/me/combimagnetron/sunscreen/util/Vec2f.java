package me.combimagnetron.sunscreen.util;

public record Vec2f(float x, float y) {

    public int xi() {
        return (int) x;
    }

    public int yi() {
        return (int) y;
    }

    public static Vec2f of(float x, float y) {
        return new Vec2f(x, y);
    }

    public Vec2f mul(float factor) {
        return new Vec2f(this.x * factor, this.y * factor);
    }

    public Vec2f mul(float x, float y) {
        return new Vec2f(this.x * x, this.y * y);
    }

    public Vec2f mul(Vec2f vec2d) {
        return mul(vec2d.x, vec2d.y);
    }

    public Vec2f div(float factor) {
        return new Vec2f(this.x / factor, this.y / factor);
    }

    public Vec2f div(float x, float y) {
        return new Vec2f(this.x / x, this.y / y);
    }

    public Vec2f div(Vec2f vec2d) {
        return div(vec2d.x, vec2d.y);
    }

    public Vec2f add(float factor) {
        return new Vec2f(this.x + factor, this.y + factor);
    }

    public Vec2f add(float x, float y) {
        return new Vec2f(this.x + x, this.y + y);
    }

    public Vec2f add(Vec2f vec2d) {
        return add(vec2d.x, vec2d.y);
    }

    public Vec2f sub(float factor) {
        return new Vec2f(this.x - factor, this.y - factor);
    }

    public Vec2f sub(float x, float y) {
        return new Vec2f(this.x - x, this.y - y);
    }

    public Vec2f sub(Vec2f vec2d) {
        return sub(vec2d.x, vec2d.y);
    }

}