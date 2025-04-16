package me.combimagnetron.sunscreen.element.impl;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public abstract class ShapeElement extends SimpleBufferedElement {

    public ShapeElement(Size size, Identifier identifier, Position position) {
        super(size, identifier, position);
    }

    @Override
    public abstract Canvas canvas();

    @Override
    public <T> Element<Canvas> style(Style<T> style, Position pos2D, T t) {
        this.canvas = style.edit(canvas, pos2D, t);
        return this;
    }

    @Override
    public <T> Element<Canvas> style(Style<T> style, T t) {
        this.canvas = style.edit(canvas, Position.pixel(0, 0) ,t);
        return this;
    }

    public static Circle circle(Size size, Identifier identifier, Position position) {
        return new Circle(size, identifier, position);
    }

    public static Rectangle rectangle(Size size, Identifier identifier, Position position, Color color) {
        return new Rectangle(size, identifier, position, color);
    }

    public static class Circle extends ShapeElement {
        public Circle(Size size, Identifier identifier, Position position) {
            super(size, identifier, position);
        }

        @Override
        public Canvas canvas() {
            double r = 0.5*size().x().pixel();

            return canvas;
        }
    }

    public static class Rectangle extends ShapeElement {
        private final Color color;
        public Rectangle(Size size, Identifier identifier, Position position, Color color) {
            super(size, identifier, position);
            this.color = color;
        }

        @Override
        public Canvas canvas() {
            canvas = canvas.fill(Vec2d.of(0, 0), size().vec2d(), color);
            return canvas;
        }
    }

}
