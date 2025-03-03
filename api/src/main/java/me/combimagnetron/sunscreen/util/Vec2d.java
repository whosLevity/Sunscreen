package me.combimagnetron.sunscreen.util;

public record Vec2d(double x, double y) {

    public int xi() {
        return (int) x;
    }

    public int yi() {
        return (int) y;
    }

    public static Vec2d of(double x, double y) {
        return new Vec2d(x, y);
    }

    public Vec2d mul(double factor) {
        return new Vec2d(this.x * factor, this.y * factor);
    }

    public Vec2d mul(double x, double y) {
        return new Vec2d(this.x * x, this.y * y);
    }

    public Vec2d mul(Vec2d vec2d) {
        return mul(vec2d.x, vec2d.y);
    }

    public Vec2d div(double factor) {
        return new Vec2d(this.x / factor, this.y / factor);
    }

    public Vec2d div(double x, double y) {
        return new Vec2d(this.x / x, this.y / y);
    }

    public Vec2d div(Vec2d vec2d) {
        return div(vec2d.x, vec2d.y);
    }

    public Vec2d add(double factor) {
        return new Vec2d(this.x + factor, this.y + factor);
    }

    public Vec2d add(double x, double y) {
        return new Vec2d(this.x + x, this.y + y);
    }

    public Vec2d add(Vec2d vec2d) {
        return add(vec2d.x, vec2d.y);
    }

    public Vec2d sub(double factor) {
        return new Vec2d(this.x - factor, this.y - factor);
    }

    public Vec2d sub(double x, double y) {
        return new Vec2d(this.x - x, this.y - y);
    }

    public Vec2d sub(Vec2d vec2d) {
        return sub(vec2d.x, vec2d.y);
    }

}