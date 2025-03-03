package me.combimagnetron.sunscreen.image;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import me.combimagnetron.passport.util.Pair;
import me.combimagnetron.sunscreen.util.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public interface CanvasRenderer {
    OptimizedCanvasRenderer OPTIMIZED = new OptimizedCanvasRenderer();
    CachedOptimizedCanvasRenderer CACHED_OPTIMIZED = new CachedOptimizedCanvasRenderer();
    SelectiveCachedOptimizedCanvasRenderer SELECTIVE_CACHED_OPTIMIZED = new SelectiveCachedOptimizedCanvasRenderer();

    Frame render(Canvas canvas);

    record Frame(Component component, int width, int height) {

        public static Frame wrap(Component component, int width, int height) {
            return new Frame(component, width, height);
        }

    }

    static CanvasRenderer optimized() {
        return OPTIMIZED;
    }

    static CanvasRenderer cachedOptimized() {
        return CACHED_OPTIMIZED;
    }

    static CanvasRenderer selectiveCachedOptimized() {
        return SELECTIVE_CACHED_OPTIMIZED;
    }

    class OptimizedCanvasRenderer implements CanvasRenderer {
        @Override
        public Frame render(Canvas canvas) {
            return Scheduler.async(() -> {Canvas.InternalCanvas internalCanvas = (Canvas.InternalCanvas) canvas;
            BufferedImage image = internalCanvas.image();
            int r = 3;
            TextComponent.Builder component = Component.text();
            for (int x = 0; x < image.getHeight(); x += 3) {
                if (image.getHeight() -x < 3) {
                    component.append(FontUtil.offset((image.getWidth() - (image.getWidth() % 3))));
                    component.append(Component.newline(), Component.newline(), Component.newline());
                }
                for (int y = 0; y < image.getWidth(); y += 3) {
                    if (x + 3 > image.getHeight() || y + 3 > image.getWidth()) {
                        continue;
                    }
                    BufferedImage section = image.getSubimage(y, x, 3, 3);
                    component.append(FontUtil.offset(-1), PixelPattern.optimize(section, r));
                }
                if (r == -7) {
                    r = 2;
                    component.append(Component.newline(), Component.newline(), Component.newline());
                } else if (!(image.getHeight() -x < 3)) {
                    r--;
                    component.append(FontUtil.offset(-(image.getWidth() - (image.getWidth() % 3))));
                }
            }
            Component finished = component.build();
            return Frame.wrap(finished, image.getWidth(), image.getHeight());});
        }
    }

    class CachedOptimizedCanvasRenderer extends OptimizedCanvasRenderer {
        private final Cache<Integer, Component> cache = Caffeine.newBuilder().expireAfterWrite(Duration.of(30, ChronoUnit.MINUTES)).build();

        @Override
        public Frame render(Canvas canvas) {
            BufferedImage image = ((Canvas.InternalCanvas) canvas).image();
            if (cache.asMap().containsKey(canvas.hashCode())) {
                return Frame.wrap(cache.getIfPresent(canvas.hashCode()), image.getWidth(), image.getHeight());
            }
            Frame frame = super.render(canvas);
            cache.put(frame.component.hashCode(), frame.component);
            return frame;
        }

    }

    class SelectiveCachedOptimizedCanvasRenderer extends OptimizedCanvasRenderer {
        private final Cache<Integer, Pair<Canvas, String>> cache = Caffeine.newBuilder().expireAfterWrite(Duration.of(30, ChronoUnit.MINUTES)).build();

        @Override
        public Frame render(Canvas canvas) {
            BufferedImage image = ((Canvas.InternalCanvas) canvas).image();
            List<Pair<Canvas, String>> possibilities = cache.asMap().values().stream().filter(pair -> pair.first().size().equals(canvas.size())).toList();
            if (possibilities.isEmpty()) {
                Frame frame = super.render(canvas);
                cache.put(frame.component.hashCode(), Pair.of(canvas, MiniMessage.miniMessage().serialize(frame.component())));
                return frame;
            }
            Pair<Canvas, String> possibility = possibilities.getFirst();
            Result difference = differences(canvas, possibility.first());
            BufferedImage changed = difference.difference();
            String find = MiniMessage.miniMessage().serialize(super.render(Canvas.image(changed)).component());
            Canvas remove = Canvas.image(canvas.size());
            for (Box box : difference.changes()) {
                remove = remove.place(possibility.first().sub(Vec2d.of(box.size().x(), box.size().y()), Vec2d.of(box.pos().x(), box.pos().y())), Vec2d.of(box.pos().x(), box.pos().y()));
            }
            String replace = MiniMessage.miniMessage().serialize(super.render(remove).component());
            String result = find.replaceAll(replace, find);
            System.out.println(replace);
            return Frame.wrap(MiniMessage.miniMessage().deserialize(result), image.getWidth(), image.getHeight());
        }

        //TODO Use boundingboxes to find the differences and only rerender the changed parts and replace them in the cached component
        private static Result differences(Canvas first, Canvas second) {
            BufferedImage firstImage = ((Canvas.InternalCanvas) first).image();
            BufferedImage secondImage = ((Canvas.InternalCanvas) second).image();
            BufferedImage difference = new BufferedImage(firstImage.getWidth(), firstImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Collection<Box> boxes = new ArrayList<>();
            for (int x = 0; x < firstImage.getWidth(); x++) {
                for (int y = 0; y < firstImage.getHeight(); y++) {
                    if (firstImage.getRGB(x, y) != secondImage.getRGB(x, y)) {
                        difference.setRGB(x, y, secondImage.getRGB(x, y));
                        resize(boxes, Vec2i.of(x, y));
                    } else {
                        difference.setRGB(x, y, 0);
                    }
                }
            }
            return Result.of(difference, boxes);
        }

        record Result(BufferedImage difference, Collection<Box> changes) {

            static Result of(BufferedImage difference, Collection<Box> changes) {
                return new Result(difference, changes);
            }

        }

        private static void resize(Collection<Box> boxes, Vec2i pos) {
            for (Box box : boxes) {
                if (box.inBox(pos)) {
                    box.extend(pos);
                    return;
                }
            }
            boxes.add(Box.of(pos, Vec2i.of(1, 1)));
        }



    }

}
