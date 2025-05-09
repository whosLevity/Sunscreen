package me.combimagnetron.sunscreen.menu;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.impl.display.Display;
import me.combimagnetron.passport.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.condition.Condition;
import me.combimagnetron.passport.util.condition.Supplier;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.element.div.Edit;
import me.combimagnetron.sunscreen.element.impl.TextInputElement;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.CanvasRenderer;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.element.div.ScrollableDiv;
import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.menu.input.InputHandler;
import me.combimagnetron.sunscreen.menu.simulate.Simulator;
import me.combimagnetron.sunscreen.renderer.div.DivRenderer;
import me.combimagnetron.sunscreen.renderer.div.Reference;
import me.combimagnetron.sunscreen.renderer.div.ReferenceHolder;
import me.combimagnetron.sunscreen.menu.timing.Tick;
import me.combimagnetron.sunscreen.menu.timing.Tickable;
import me.combimagnetron.sunscreen.session.Session;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.*;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public sealed interface OpenedMenu permits OpenedMenu.Base, AspectRatioMenu {

    boolean close();

    InputHandler inputHandler();

    Simulator simulator();

    OpenedMenu apply(Draft draft);

    sealed abstract class Base implements OpenedMenu permits FloatImpl {
        protected final HashMap<Identifier, Div<?>> divHashMap = new HashMap<>();

        public abstract void handleClick();

        public abstract void handleRot(float yaw, float pitch);

        public abstract void handleSneak();

        public abstract void handleScroll(int slot);

        public abstract void handleDamage();

        public abstract void handlePing(long ping);

        protected void forceDivGeometry(SunscreenUser<?> viewer) {
            for (Map.Entry<Identifier, Div<?>> entry : divHashMap.entrySet()) {
                Div<?> div = entry.getValue();
                for (Element<?> element : div.elements()) {
                    if (!(element instanceof SimpleBufferedElement)) {
                        continue;
                    }
                    for (RuntimeDefinableGeometry.GeometryBuilder<?> definable : div.definables()) {
                        div.geometry(definable.finish(viewer.screenSize().pixel(), div.size()));
                    }
                    for (RuntimeDefinable.Type<?, ?> definable : element.definables().stream().sorted(Comparator.comparingInt(RuntimeDefinable.Type::priority)).toList()) {
                        if (definable instanceof RuntimeDefinableGeometry.GeometryBuilder<?> geometry) {
                            element.geometry(geometry.finish(div.size(), div.size()));
                        }
                        if (definable.type() == InputHandler.class) {
                            RuntimeDefinable.Type<InputHandler, OpenedMenu> inputHandler = (RuntimeDefinable.Type<InputHandler, OpenedMenu>) definable;
                            TextInputElement textInputElement = (TextInputElement) element;
                            textInputElement.inputHandler(inputHandler.finish(this));
                        }
                    }
                }
            }
        }

    }

    non-sealed abstract class FloatImpl extends Base implements Tickable {
        private static final int Y_OFFSET = 8;
        private final UUID uuid = UUID.randomUUID();
        private final DivRenderer<TextDisplay> renderer = DivRenderer.font();
        private final ReferenceHolder<TextDisplay> referenceHolder = renderer.referenceHolder();
        private final SunscreenUser<?> viewer;
        private final InputHandler inputHandler;
        private final HashMap<Identifier, Boolean> lastPasses = new HashMap<>();
        private final List<Integer> riding = new ArrayList<>();
        private final TextDisplay cursorDisplay;
        private final TextDisplay background;
        private final Vector3d rotation;
        private final Simulator simulator;
        private Identifier focused = null;
        private TextDisplay camera;
        private Color backgroundColor = Color.of(0, 0, 0, 255);
        private Vec2d lastInput = Vec2d.of(0, 0);
        private int damageTick = 0;

        public FloatImpl(SunscreenUser<?> viewer, MenuTemplate template) {
            this.rotation = viewer.rotation();
            this.viewer = viewer;
            this.inputHandler = new InputHandler.Impl(viewer);
            MenuTemplate.Simple menuTemplate = (MenuTemplate.Simple) template;
            divHashMap.putAll(menuTemplate.create());
            forceDivGeometry(viewer);
            this.cursorDisplay = TextDisplay.textDisplay(viewer.position());
            this.background = TextDisplay.textDisplay(viewer.position());
            Collection<SunscreenHook> hooks = SunscreenHook.HOOKS.stream().filter(SunscreenHook::canRun).toList();
            for (SunscreenHook hook : hooks) {
                hook.onMenuEnter(viewer, this);
            }
            this.simulator = null;
        }

        @Override
        public InputHandler inputHandler() {
            return inputHandler;
        }

        public UUID uuid() {
            return uuid;
        }

        /**
         * Tick method for the openedMenu, called every tick.
         * @param tick Tick to calculate generation time.
         */
        @Override
        public boolean tick(@NotNull Tick tick) {
            if (damageTick > 0) {
                damageTick--;
                background.backgroundColor(Color.of(backgroundColor.red(), backgroundColor.green(), backgroundColor.blue(), 123).rgb());
                MenuHelper.send(viewer, background);
            } else {
                background(backgroundColor);
            }
            for (Div<?> div : divHashMap.values()) {
                boolean update = false;
                if (div instanceof Tickable tickable) {
                    update = tickable.tick(tick);
                }
                for (Element<?> element : div.elements()) {
                    if (element instanceof Tickable tickable) {
                        update = tickable.tick(tick);
                    }
                }
                if (update && !div.hidden()) {
                    update(div);
                }
                if (div.condition() == null) {
                    continue;
                }
                Condition.Result result = div.condition().eval(Supplier.of(viewer.platformSpecificPlayer(), viewer));
                if (result == null) {
                    continue;
                }
                if (lastPasses.get(div.identifier()) == result.value()) {
                    continue;
                }
                if (!result.value()) {
                    hide(div);
                } else {
                    show(div);
                }
                lastPasses.put(div.identifier(), result.value());
            }
            return true;
        }

        protected void hideCursor() {
            cursorDisplay.text(Component.empty());
            List<EntityData> entityData = cursorDisplay.type().metadata().entityData();
            WrapperPlayServerEntityMetadata clientEntityMetadata = new WrapperPlayServerEntityMetadata(cursorDisplay.id().intValue(), entityData);
            viewer.connection().send(clientEntityMetadata);
        }

        protected void showCursor() {
            cursorDisplay.text(Component.text("e").font(Key.key("comet:arrow")));
            cursorDisplay.backgroundColor(0);
            cursorDisplay.transformationDuration(1);
            cursorDisplay.teleportationDuration(1);
            cursorDisplay.billboard(Display.Billboard.CENTER);
            cursorDisplay.brightness(15, 15);
            Display.Transformation transformation = Display.Transformation.transformation().translation(Vector3d.vec3(0, 0, -0.24999)).scale(Vector3d.vec3((double) 1/24, (double) 1/24, (double) 1/24));
            cursorDisplay.transformation(transformation);
            viewer.show(cursorDisplay);
        }

        @Override
        public void handleRot(float yaw, float pitch) {
            lastInput = Vec2d.of(yaw, -pitch).sub(lastInput).div(350);
            move();
        }

        @Override
        public void handleSneak() {
            close();
        }

        @Override
        public void handlePing(long ping) {
            int offset = Math.clamp((int) Math.ceil((double) ping /50), 1, 10);
            cursorDisplay.transformationDuration(offset);
            cursorDisplay.teleportationDuration(offset);
            MenuHelper.send(viewer, cursorDisplay);
        }

        @Override
        public void handleClick() {
            for (Reference<TextDisplay> reference : referenceHolder.references()) {
                Div div = reference.div();
                if (focused != null && !div.identifier().equals(focused)) {
                    continue;
                }
                if (div instanceof Div.NonRenderDiv) continue;
                if (div.hidden()) continue;
                Vec2i divPos = Vec2i.of((int) div.position().x().pixel(), (int) div.position().y().pixel());
                Vector3d cursorTranslation = cursorDisplay.transformation().translation();
                Vec2i cursorPos = ViewportHelper.toScreen(cursorTranslation, viewer.screenSize());
                cursorPos = cursorPos.add(0, Y_OFFSET);
                if (HoverHelper.isHovered(cursorTranslation, viewer, divPos, div.size())) {
                    boolean update = ((Div.Impl)div).handleClick(cursorPos.sub(divPos), new Input.Type.MouseClick(false), viewer);
                    if (!update) {
                        continue;
                    }
                    reference.t().text(CanvasRenderer.optimized().render(div.render(viewer)).component());
                    MenuHelper.send(viewer, reference.t());
                }
            }
        }

        @Override
        public Simulator simulator() {
            return simulator;
        }

        @Override
        public void handleScroll(int slot) {
            for (Reference<TextDisplay> entry : referenceHolder.references()) {
                Div div = entry.div();
                if (div instanceof ScrollableDiv scrollableDiv) {
                    scrollableDiv.scroll(slot);
                    entry.t().text(CanvasRenderer.optimized().render(div.render(viewer)).component());
                    MenuHelper.send(viewer, entry.t());
                }
            }
        }

        @Override
        public void handleDamage() {
            damageTick += 20;
        }

        /**
         * Takes Color argument and sets the background accordingly, defaults to black.
         * @param color Color to set the background to, can be transparent.
         */
        public void background(Color color) {
            background.backgroundColor(color.rgb());
            background.text(Component.text(" "));
            this.backgroundColor = color;
            MenuHelper.send(viewer, background);
        }

        public void update(Div div) {
            TextDisplay display = referenceHolder.reference(div.identifier()).t();
            display.text(CanvasRenderer.optimized().render(div.render(viewer)).component());
            MenuHelper.send(viewer, display);
        }

        public void hide(Div div) {
            TextDisplay display = referenceHolder.reference(div.identifier()).t();
            display.text(Component.empty());
            if (div instanceof Div.Impl impl) {
                impl.hide(true);
            }
            MenuHelper.send(viewer, display);
        }

        public void hide(Identifier identifier) {
            Reference<TextDisplay> reference = referenceHolder.reference(identifier);
            TextDisplay display = reference.t();
            Div div = reference.div();
            display.text(Component.empty());
            if (div instanceof Div.Impl impl) {
                impl.hide(true);
            }
            MenuHelper.send(viewer, display);
        }

        public void show(Identifier identifier) {
            Reference<TextDisplay> reference = referenceHolder.reference(identifier);
            TextDisplay display = reference.t();
            Div div = reference.div();
            display.text(CanvasRenderer.optimized().render(divHashMap.get(identifier).render(viewer)).component());
            if (div instanceof Div.Impl impl) {
                impl.hide(false);
            }
            MenuHelper.send(viewer, display);
        }

        public void update(Identifier identifier) {
            TextDisplay display = referenceHolder.reference(identifier).t();
            display.text(CanvasRenderer.optimized().render(divHashMap.get(identifier).render(viewer)).component());
            MenuHelper.send(viewer, display);
        }

        public void render(Div div) {
            Reference<TextDisplay> reference = renderer.render(div, viewer);
            TextDisplay display = reference.t();
            //display.text(CanvasRenderer.optimized().render(div.render(viewer)).component());
            riding.add(display.id().intValue());
            viewer.connection().send(new WrapperPlayServerSetPassengers(camera.id().intValue(), ArrayUtils.toPrimitive(riding.toArray(new Integer[0]))));
        }

        public void show(Div div) {
            TextDisplay display = referenceHolder.reference(div.identifier()).t();
            display.text(CanvasRenderer.optimized().render(div.render(viewer)).component());
            if (div instanceof Div.Impl impl) {
                impl.hide(false);
            }
            MenuHelper.send(viewer, display);
        }

        public DivRenderer<TextDisplay> renderer() {
            return renderer;
        }

        public void focus(Identifier identifier) {
            this.focused = identifier;
        }

        public void unfocus() {
            this.focused = null;
        }

        private void move() {
            Vec2d translation = lastInput.mul(1);
            translation = translation.add(0.003, -0.010);
            viewer.message(Component.text("a"));
            Display.Transformation transformation = Display.Transformation.transformation().translation(Vector3d.vec3(translation.x(), translation.y(), -0.24999)).scale(Vector3d.vec3((double) 1/24, (double) 1/24, (double) 1/24));
            cursorDisplay.transformation(transformation);
            MenuHelper.send(viewer, cursorDisplay);
            for (Reference<TextDisplay> reference: referenceHolder.references()) {
                Div div = reference.div();
                if (focused != null && !div.identifier().equals(focused)) {
                    continue;
                }
                if (div.hidden()) continue;
                if (div instanceof Div.NonRenderDiv) continue;
                Vec2i divPos = Vec2i.of((int) div.position().x().pixel(), (int) div.position().y().pixel());
                Vector3d cursorTranslation = cursorDisplay.transformation().translation();
                Vec2i cursorPos = ViewportHelper.toScreen(cursorTranslation, viewer.screenSize());
                cursorPos = cursorPos.add(0, Y_OFFSET);
                if (HoverHelper.isHovered(cursorTranslation, viewer, divPos.mul(ViewportHelper.fromVector3d(div.scale())), div.size().mul(ViewportHelper.fromVector3d(div.scale())))) {
                    boolean render = ((Div.Impl)div).handleHover(cursorPos.sub(divPos), viewer);
                    if (!render) {
                        continue;
                    }
                    reference.t().text(CanvasRenderer.optimized().render(div.render(viewer)).component());
                    MenuHelper.send(viewer, reference.t());
                }
            }
        }

        /**
         * Closes the openedMenu and sends the necessary packets to the viewer.
         * @return True if the openedMenu was closed successfully.
         */
        @Override
        public boolean close() {
            leave();
            SunscreenLibrary.library().menuTicker().stop(this);
            viewer.resendInv();
            Collection<SunscreenHook> hooks = SunscreenHook.HOOKS.stream().filter(SunscreenHook::canRun).toList();
            for (SunscreenHook hook : hooks) {
                hook.onMenuLeave(viewer, this);
            }
            SunscreenLibrary.library().sessionHandler().session(Session.session(null, viewer));
            return true;
        }

        private void leave() {
            viewer.connection().send(new WrapperPlayServerChangeGameState(WrapperPlayServerChangeGameState.Reason.CHANGE_GAME_MODE, viewer.gameMode()));
            viewer.connection().send(new WrapperPlayServerSetPassengers(cursorDisplay.id().intValue(), new int[]{}));
            viewer.connection().send(new WrapperPlayServerPlayerInfoUpdate(WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_GAME_MODE, new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(new UserProfile(viewer.uniqueIdentifier(), viewer.name()), true, 0, GameMode.getById(viewer.gameMode()), Component.empty(), null)));
            viewer.connection().send(new WrapperPlayServerCamera(viewer.entityId()));
            viewer.connection().send(new WrapperPlayServerDestroyEntities(cursorDisplay.id().intValue(), background.id().intValue(), camera.id().intValue()));
            viewer.connection().send(new WrapperPlayServerPlayerRotation((float) rotation.x(), (float) rotation.y()));
            viewer.connection().send(new WrapperPlayServerPlayerPositionAndLook(viewer.position().x(), viewer.position().y(), viewer.position().z(), (float) rotation.x(), (float) rotation.y(), (byte)0, 0, false));
            referenceHolder.references().forEach(reference -> viewer.connection().send(new WrapperPlayServerDestroyEntities(reference.t().id().intValue())));
        }

        @Override
        public OpenedMenu apply(Draft draft) {
            Draft.Impl draftImpl = (Draft.Impl) draft;
            for (Edit<?> edit : draftImpl.edits()) {
                if (edit.type() == Div.class) {
                    Edit<Div> divEdit = (Edit<Div>) edit;
                    Div div = divHashMap.get(edit.identifier());
                    for (Function<Div, Div> draftEdit : divEdit.edits()) {
                        div = draftEdit.apply(div);
                    }
                    divHashMap.put(edit.identifier(), div);
                    update(div);
                } else if (edit.type() == Element.class) {
                    Edit<Element<?>> elementEdit = (Edit<Element<?>>) edit;
                    Element<?> element = divHashMap.get(edit.identifier()).elements().stream().filter(e -> e.identifier().equals(edit.identifier())).findFirst().orElse(null);
                    for (Function<Element<?>, Element<?>> draftEdit : elementEdit.edits()) {
                        element = draftEdit.apply(element);
                    }
                    Div div = divHashMap.get(edit.identifier());
                    div.remove(edit.identifier());
                    div.add(element);
                    divHashMap.put(edit.identifier(), div);
                    update(div);
                }
            }
            return this;
        }

        public Div<Canvas> div(Identifier identifier) {
            return (Div<Canvas>) divHashMap.get(identifier);
        }

        /**
         * Opens the openedMenu and sends the necessary packets to the viewer.
         * @param user User to open the openedMenu for.
         */
        public void open(SunscreenUser<?> user) {
            user.connection().send(new WrapperPlayServerPlayerRotation(0, -180));
            camera = TextDisplay.textDisplay(user.position().add(Vector3d.vec3(0, 1.6, 0)));
            Vector3d rotation = Vector3d.vec3(user.rotation().y(), user.rotation().x(), user.rotation().z());
            camera.rotation(rotation);
            user.show(camera);
            if (SunscreenLibrary.library().config().forceShaderFov()) {
                user.fov(70);
            }
            user.connection().send(new WrapperPlayServerCamera(camera.id().intValue()));
            user.connection().send(new WrapperPlayServerChangeGameState(WrapperPlayServerChangeGameState.Reason.CHANGE_GAME_MODE, 3));
            user.connection().send(new WrapperPlayServerPlayerInfoUpdate(WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_GAME_MODE, new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(new UserProfile(user.uniqueIdentifier(), user.name()), true, 0, GameMode.CREATIVE, Component.empty(), null)));
            List<Integer> entityIds = new ArrayList<>();
            entityIds.add(user.entityId());
            showCursor();
            entityIds.add(cursorDisplay.id().intValue());
            initBackground();
            entityIds.add(background.id().intValue());
            user.show(cursorDisplay);
            Scheduler.async(() -> {
                divHashMap.values().forEach(d -> renderer.render((Div<TextDisplay>) d, user));
                entityIds.addAll(referenceHolder.references().stream().map(Reference::t).map(TextDisplay::id).map(Entity.EntityId::intValue).toList());
                riding.addAll(entityIds);
                user.connection().send(new WrapperPlayServerSetPassengers(camera.id().intValue(), ArrayUtils.toPrimitive(entityIds.toArray(new Integer[0]))));
                return null;
            });
            user.connection().send(new WrapperPlayServerSystemChatMessage(true, Component.text(" ")));
        }

        private void initBackground() {
            Display.Transformation tempTransformation = Display.Transformation.transformation().translation(Vector3d.vec3(0, -60, -0.27)).scale(Vector3d.vec3(500, 500, (double) 1/24));
            background.billboard(Display.Billboard.CENTER);
            background.text(Component.text(" "));
            background.transformation(tempTransformation);
            background.brightness(15, 15);
            background.backgroundColor(-16184812);
            viewer.show(background);
        }

    }

    class Float extends FloatImpl {
        public Float(SunscreenUser<?> viewer, MenuTemplate template) {
            super(viewer, template);
        }

        public Float(SunscreenUser<?> viewer) {
            super(viewer, new MenuTemplate.Simple(MenuTemplate.Type.FLOAT, null));
            MenuTemplate template = template();
            if (template == null) {
                throw new IllegalStateException("template() in Float menu isn't overridden, please change or contact devs if you did override.");
            }
            divHashMap.putAll(((MenuTemplate.Simple)template()).create());
            forceDivGeometry(viewer);
            open(viewer);
        }

        public MenuTemplate template() {
            //OVERRIDE
            return null;
        }

    }

    class MenuHelper {
        public static void send(SunscreenUser<?> viewer, TextDisplay textDisplay) {
            List<EntityData> entityData = textDisplay.type().metadata().entityData();
            WrapperPlayServerEntityMetadata clientEntityMetadata = new WrapperPlayServerEntityMetadata(textDisplay.id().intValue(), entityData);
            viewer.connection().send(clientEntityMetadata);
        }

    }

}
