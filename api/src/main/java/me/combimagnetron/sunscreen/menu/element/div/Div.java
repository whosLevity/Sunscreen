package me.combimagnetron.sunscreen.menu.element.div;

import me.combimagnetron.passport.event.Dispatcher;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.event.ClickElementEvent;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.menu.Editable;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Interactable;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.util.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public interface Div extends Editable {
    Identifier identifier();

    Canvas canvas();

    Vec2d size();

    Vector3d scale();

    Position position();

    Div size(Vec2d size);

    Div fit();

    Div scale(Vector3d scale);

    Div position(Position pos);

    Div add(Element element);

    Div remove(Identifier identifier);

    Div remove(Element element);

    Div hide(Identifier identifier);

    Div hide(Element element);

    Div show(Identifier identifier);

    Div show(Element element);

    Collection<Element> elements();

    Canvas render(Canvas image);

    Canvas render();

    static Div div(Identifier identifier) {
        return new Impl(identifier);
    }

    static ScrollableDiv scroll(Identifier identifier) {
        return new ScrollableDiv.Impl(identifier);
    }

    static Div nonRender(Identifier identifier) {
        return new NonRenderDiv(identifier);
    }

    class Impl implements Div {
        private final HashMap<Identifier, Element> elements = new HashMap<>();
        private final HashSet<Element> hidden = new HashSet<>();
        private final Identifier identifier;
        private Vec2d size = Vec2d.of(512, 512);
        private Canvas canvas;
        private Position pos = Position.pixel(0, 0);
        private Vector3d scale = Vector3d.vec3(1);

        public Canvas render(Canvas image) {
            for (Element element : elements.values()) {
                if (!hidden.contains(element)) {
                    image = image.place(element.canvas(), Vec2d.of(element.position().x().pixel(), element.position().y().pixel()));
                }
            }
            return image;
        }

        public void handleHover(Vec2d pos) {
            for (Element element : elements.values()) {
                if (element instanceof Interactable interactable && interactable.reactiveToHover()) {
                    //System.out.println(pos);
                    //System.out.println(element.position() + " " + element.size());
                    if (!HoverHelper.isHovered(pos, ViewportHelper.fromPosition(element.position()), element.size())) {
                        //interactable.hover(null);
                        continue;
                    }
                    //Dispatcher.dispatcher().post(ClickElementEvent.class, ClickElementEvent.create());
                    interactable.hover(pos.sub(Vec2d.of(element.position().x().pixel(), element.position().y().pixel())));
                }
            }
        }

        public Canvas render() {
            return render(canvas);
        }

        Impl(Identifier identifier) {
            this.identifier = identifier;
            this.canvas = Canvas.image(size);
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }

        @Override
        public Canvas canvas() {
            return canvas;
        }

        @Override
        public Vec2d size() {
            return canvas.size();
        }

        @Override
        public Vector3d scale() {
            return scale;
        }

        @Override
        public Position position() {
            return pos;
        }

        @Override
        public Div size(Vec2d size) {
            this.size = size;
            /*if (size.x() < this.canvas.size().x() && size.y() < this.canvas.size().y()) {
                this.canvas = this.canvas.sub(size, Vec2d.of(0,0));
                return this;
            }*/
            this.canvas = Canvas.image(size);
            return this;
        }

        @Override
        public Div fit() {

            return this;
        }

        @Override
        public Div scale(Vector3d scale) {
            this.scale = scale;
            return this;
        }

        @Override
        public Div position(Position pos) {
            this.pos = pos;
            return this;
        }

        @Override
        public Div add(Element element) {
            elements.put(element.identifier(), element);
            return this;
        }

        @Override
        public Div remove(Identifier identifier) {
            elements.remove(identifier);
            return this;
        }

        @Override
        public Div remove(Element element) {
            elements.remove(element.identifier());
            return this;
        }

        @Override
        public Div hide(Identifier identifier) {
            hidden.add(elements.get(identifier));
            return this;
        }

        @Override
        public Div hide(Element element) {
            hidden.add(element);
            return this;
        }

        @Override
        public Div show(Identifier identifier) {
            hidden.remove(elements.get(identifier));
            return this;
        }

        @Override
        public Div show(Element element) {
            hidden.remove(element);
            return this;
        }

        @Override
        public Collection<Element> elements() {
            return elements.values();
        }
    }

    final class NonRenderDiv extends Impl {

        NonRenderDiv(Identifier identifier) {
            super(identifier);
        }

    }

}