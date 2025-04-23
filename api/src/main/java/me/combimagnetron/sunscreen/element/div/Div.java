package me.combimagnetron.sunscreen.element.div;

import me.combimagnetron.passport.event.Dispatcher;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.condition.Condition;
import me.combimagnetron.sunscreen.event.ClickElementEvent;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.*;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface Div<T> extends Editable {

    /**
     * The identifier of the div.
     * @return identifier of the div.
     */
    Identifier identifier();

    /**
     * The canvas of the div.
     * @return current render iteration canvas of the div.
     */
    T canvas();

    /**
     * The size of the div.
     * @return Vec2d representing size of the div, always in rounded integers.
     */
    Vec2d size();

    /**
     * The scale of the div.
     * @return Vector3d representing scale of the div.
     */
    Vector3d scale();

    /**
     * The position of the div.
     * @return Position representing position of the div, can be in pixels, percentages or a mix.
     */
    Position position();

    /**
     * The condition under which the div renders.
     * @return Condition representing the condition under which the div renders.
     */
    Condition condition();

    Collection<RuntimeDefinableGeometry.GeometryBuilder<?>> definables();

    boolean hidden();

    /**
     * The order of the div.
     * @return double representing the order of the div.
     */
    double order();

    default @Nullable Element<T> element(@NotNull Identifier identifier) {
        return elements().stream().filter(e -> e.identifier().equals(identifier)).findFirst().orElse(null);
    }

    /**
     * Sets the size of the div.
     * @param size new size of the div.
     * @return current div.
     */
    Div<T> size(Size size);

    default Div<T> size(Size.SizeBuilder builder) {
        return geometry(builder);
    }

    /**
     * Sets the condition of the div.
     * @param condition new condition of the div.
     * @return current div.
     */
    Div<T> condition(Condition condition);

    /**
     * Fits the div to the size of the canvas.
     * @return current div.
     */
    Div<T> fit();

    /**
     * Sets the scale of the div.
     * @param scale new scale of the div.
     * @return current div.
     */
    Div<T> scale(Vector3d scale);

    /**
     * Sets the position of the div.
     * @param pos new position of the div.
     * @return current div.
     */
    Div<T> position(Position pos);

    default Div<T> position(RuntimeDefinableGeometry.GeometryBuilder<?> builder) {
        return geometry(builder);
    }

    Div<T> geometry(Geometry builder);

    Div<T> geometry(RuntimeDefinableGeometry.GeometryBuilder<?> builder);

    /**
     * Adds an element to the div.
     * @param element element to add.
     * @return current div.
     */
    Div<T> add(Element<T> element);

    /**
     * Removes an element from the div.
     * @param identifier identifier of the element to remove.
     * @return current div.
     */
    Div<T> remove(Identifier identifier);

    /**
     * Removes an element from the div.
     * @param element element to remove.
     * @return current div.
     */
    Div<T> remove(Element<T> element);

    /**
     * Hides an element from the div.
     * @param identifier identifier of the element to hide.
     * @return current div.
     */
    Div<T> hide(Identifier identifier);

    /**
     * Hides an element from the div.
     * @param element element to hide.
     * @return current div.
     */
    Div<T> hide(Element<T> element);

    /**
     * Shows an element from the div.
     * @param identifier identifier of the element to show.
     * @return current div.
     */
    Div<T> show(Identifier identifier);

    /**
     * Shows an element from the div.
     * @param element element to show.
     * @return current div.
     */
    Div<T> show(Element<T> element);

    /**
     * Sets the order of the div.
     * @param order new order of the div.
     * @return current div.
     */
    Div<T> order(double order);

    /**
     * The elements of the div.
     * @return collection of elements in the div.
     */
    Collection<Element<T>> elements();

    /**
     * Renders the div to an image.
     * @param image canvas to render to.
     * @return rendered canvas.
     */
    Canvas render(Canvas image, SunscreenUser<?> user);

    /**
     * Renders the div.
     */
    Canvas render(SunscreenUser<?> user);

    /**
     * Constructs and returns a new div.
     * @param identifier identifier of the to-be-constructed div
     * @return new div.
     */
    static Div<Canvas> div(Identifier identifier) {
        return new Impl(identifier);
    }

    /**
     * Constructs and returns a new scrollable div.
     * @param identifier identifier of the to-be-constructed scrollable div
     * @return new scrollable div.
     */
    static ScrollableDiv scroll(Identifier identifier) {
        return new ScrollableDiv.Impl(identifier);
    }

    /**
     * Constructs and returns a new non-rendering div.
     * @param identifier identifier of the to-be-constructed non-rendering div
     * @return new non-rendering div.
     */
    static Div<Canvas> nonRender(Identifier identifier) {
        return new NonRenderDiv(identifier);
    }

    class Impl implements Div<Canvas> {
        private final LinkedHashMap<Identifier, Element<Canvas>> elements = new LinkedHashMap<>();
        private final HashSet<Element<Canvas>> hidden = new HashSet<>();
        private final List<RuntimeDefinableGeometry.GeometryBuilder<?>> geometryBuilders = new ArrayList<>();
        private final Identifier identifier;
        private Size size = Size.pixel(512, 512);
        private Position pos = Position.pixel(0, 0);
        private Vector3d scale = Vector3d.vec3(1);
        private double order = 0;
        private boolean isHidden = false;
        private Condition condition;
        private Canvas canvas;

        public Canvas render(Canvas image, SunscreenUser<?> user) {
            for (Element<Canvas> element : elements.values()) {
                for (RuntimeDefinableGeometry definable : element.definables().stream().filter(runtimeDefinable -> runtimeDefinable instanceof RuntimeDefinableGeometry).map(runtimeDefinable -> (RuntimeDefinableGeometry) runtimeDefinable).toList()) {
                    definable.build(user.screenSize().pixel());
                }
                if (!hidden.contains(element)) {
                    if (element.canvas() == null) {
                        continue;
                    }
                    image = image.place(element.canvas(), Vec2d.of(element.position().x().pixel(), element.position().y().pixel()));
                }
            }
            return image;
        }

        protected HashSet<Element<Canvas>> hiddenElements() {
            return hidden;
        }

        @Override
        public boolean hidden() {
            return isHidden;
        }

        public void hide(boolean isHidden) {
            this.isHidden = isHidden;
        }

        public boolean handleClick(Vec2d pos, Input.Type.MouseClick click) {
            boolean update = false;
            for (Element<Canvas> element : elements.values()) {
                if (element instanceof Interactable interactable && interactable.reactiveToClick()) {
                    if (!HoverHelper.isHovered(pos, ViewportHelper.fromPosition(element.position()), element.size())) {
                        boolean a = interactable.click(null);
                        if (a) {
                            update = true;
                        }
                        continue;
                    }
                    Dispatcher.dispatcher().post(ClickElementEvent.create(element, pos.sub(Vec2d.of(element.position().x().pixel(), element.position().y().pixel())), click));
                    boolean keep = update;
                    update = interactable.click(pos.sub(Vec2d.of(element.position().x().pixel(), element.position().y().pixel())));
                    if (keep) {
                        update = true;
                    }
                }
            }
            return update;
        }

        public boolean handleHover(Vec2d pos) {
            boolean update = false;
            for (Element<Canvas> element : elements.values()) {
                if (element instanceof Interactable interactable && interactable.reactiveToHover()) {
                    if (!HoverHelper.isHovered(pos, ViewportHelper.fromPosition(element.position()), element.size())) {
                        boolean a = interactable.hover(null);
                        if (a) {
                            update = true;
                        }
                        continue;
                    }
                    //Dispatcher.dispatcher().post(ClickElementEvent.class, ClickElementEvent.create(element, pos, new Input.Type.MouseClick(false)));
                    boolean keep = update;
                    update = interactable.hover(pos.sub(Vec2d.of(element.position().x().pixel(), element.position().y().pixel())));
                    if (keep) {
                        update = true;
                    }
                }
            }
            return update;
        }

        public Canvas render(SunscreenUser<?> user) {
            return render(canvas, user);
        }

        Impl(Identifier identifier) {
            this.identifier = identifier;
            this.canvas = Canvas.image(Vec2d.of(size.x().pixel(), size.y().pixel()));
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
        public Condition condition() {
            return condition;
        }

        @Override
        public Collection<RuntimeDefinableGeometry.GeometryBuilder<?>> definables() {
            return geometryBuilders;
        }

        @Override
        public double order() {
            return order;
        }

        @Override
        public Div<Canvas> size(Size size) {
            this.size = size;
            /*if (size.x() < this.canvas.size().x() && size.y() < this.canvas.size().y()) {
                this.canvas = this.canvas.sub(size, Vec2d.of(0,0));
                return this;
            }*/
            this.canvas = Canvas.image(Vec2d.of(size.x().pixel(), size.y().pixel()));
            return this;
        }

        @Override
        public Div<Canvas> condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        @Override
        public Div<Canvas> fit() {

            return this;
        }

        @Override
        public Div<Canvas> scale(Vector3d scale) {
            this.scale = scale;
            return this;
        }

        @Override
        public Div<Canvas> position(Position pos) {
            this.pos = pos;
            return this;
        }

        @Override
        public Div<Canvas> geometry(Geometry geometry) {
            if (geometry instanceof Position position) {
                this.pos = position;
            } else if (geometry instanceof Size size) {
                this.size = size;
                this.canvas = Canvas.image(size);
            }
            return this;
        }

        @Override
        public Div<Canvas> geometry(RuntimeDefinableGeometry.GeometryBuilder<?> builder) {
            this.geometryBuilders.add(builder);
            return this;
        }

        @Override
        public Div<Canvas> add(Element<Canvas> element) {
            elements.put(element.identifier(), element);
            return this;
        }

        @Override
        public Div<Canvas> remove(Identifier identifier) {
            elements.remove(identifier);
            return this;
        }

        @Override
        public Div<Canvas> remove(Element<Canvas> element) {
            elements.remove(element.identifier());
            return this;
        }

        @Override
        public Div<Canvas> hide(Identifier identifier) {
            hidden.add(elements.get(identifier));
            return this;
        }

        @Override
        public Div<Canvas> hide(Element<Canvas> element) {
            hidden.add(element);
            return this;
        }

        @Override
        public Div<Canvas> show(Identifier identifier) {
            hidden.remove(elements.get(identifier));
            return this;
        }

        @Override
        public Div<Canvas> show(Element<Canvas> element) {
            hidden.remove(element);
            return this;
        }

        @Override
        public Div<Canvas> order(double order) {
            this.order = order;
            return this;
        }

        @Override
        public Collection<Element<Canvas>> elements() {
            return elements.values();
        }
    }

    final class NonRenderDiv extends Impl {

        NonRenderDiv(Identifier identifier) {
            super(identifier);
        }

    }

}