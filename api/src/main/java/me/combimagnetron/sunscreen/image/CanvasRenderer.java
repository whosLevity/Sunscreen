package me.combimagnetron.sunscreen.image;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
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
import java.util.*;

public interface CanvasRenderer {
    OptimizedCanvasRenderer OPTIMIZED = new OptimizedCanvasRenderer();
    CachedOptimizedCanvasRenderer CACHED_OPTIMIZED = new CachedOptimizedCanvasRenderer();
    SelectiveCachedOptimizedCanvasRenderer SELECTIVE_CACHED_OPTIMIZED = new SelectiveCachedOptimizedCanvasRenderer();

    Frame render(Canvas canvas);

    record Frame(Component component, int width, int height, int r) {

        public static Frame wrap(Component component, int width, int height, int r) {
            return new Frame(component, width, height, r);
        }

    }

    record Pattern(Collection<String> pattern, Map<String, Color> colorMap) {

        public static Pattern of(Collection<String> pattern, BiMap<String, Color> colorMap) {
            return new Pattern(pattern, colorMap);
        }
    }

    class CanvasPattenizer {

        public Pattern patternize(BufferedImage image) {
            Collection<String> pattern = new ArrayList<>();
            BiMap<String, Color> colorMap = HashBiMap.create();
            for (int x = 0; x < image.getHeight(); x += 1) {
                for (int y = 0; y < image.getWidth(); y += 1) {
                    if (x + 1 > image.getHeight() || y + 1 > image.getWidth()) {
                        continue;
                    }
                    BufferedImage section = image.getSubimage(y, x, 1, 1);
                    String patterned = patternize(section, null);
                    pattern.add(patterned);
                    colorMap.put(patterned, Color.of(section.getRGB(0, 0)));
                }
            }
            return Pattern.of(pattern, colorMap);
        }

        private String patternize(BufferedImage image, BiMap<String, Color> colorMap) {
            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < image.getHeight(); x++) {
                for (int y = 0; y < image.getWidth(); y++) {
                    if (x + 1 > image.getHeight() || y + 1 > image.getWidth()) {
                        continue;
                    }
                    Color color = Color.of(image.getRGB(y, x));
                    if (colorMap != null && colorMap.containsValue(color)) {
                        builder.append(colorMap.inverse().get(color));
                    } else {
                        builder.append(" ");
                    }
                }
            }
            return builder.toString();
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
            return old(canvas);
        }

        private static Frame updated(Canvas canvas) {
            return Scheduler.async(() -> {
                Canvas.InternalCanvas internalCanvas = (Canvas.InternalCanvas) canvas;
                BufferedImage image = internalCanvas.image();
                int fontLine = 3;

                return null;
            });
        }

        private static Frame old(Canvas canvas) {
            return Scheduler.async(() -> {Canvas.InternalCanvas internalCanvas = (Canvas.InternalCanvas) canvas;
                BufferedImage image = internalCanvas.image();
                int r = 3;
                TextComponent.Builder component = Component.text();
                int realHeight = image.getHeight() - 1;
                int realWidth = image.getWidth() - 1;
                boolean isPerfect = image.getWidth() % 3 == 0;
                for (int x = 0; x <= realHeight; x += 3) {
                    int heightAdd = 3;
                    if (x + 3 > realHeight) {
                        heightAdd = realHeight - x;
                    }
                    for (int y = 0; y <= realWidth; y += 3) {
                        int widthAdd = 3;
                        if (y + 3 > realWidth) {
                            widthAdd = realWidth - y;
                        }
                        if (widthAdd <= 0 || heightAdd <= 0) {
                            continue;
                        }
                        BufferedImage section = image.getSubimage(y, x, widthAdd, heightAdd);
                        me.combimagnetron.sunscreen.util.Pair<Component, Integer> pair = PixelPattern.optimize(section, r);
                        /*if (widthAdd != 3) {
                            component.append(FontUtil.offset(-1 + (pair.v() - 3)));//.append(FontUtil.offset(1));
                        } else if (y != 0 && y + widthAdd != realWidth) {*/
                            component.append(FontUtil.offset(-1));
                        //}
                        component.append(pair.k());
                    }
                    if (r == -7) {
                        r = 2;
                        component.append(Component.newline(), Component.newline(), Component.newline());
                    } else if (!(realHeight - x <= 3)) {
                        r--;
                        component.append(FontUtil.offset(-(image.getWidth())));
                    }
                }
                Component finished = component.build();
                return Frame.wrap(finished, image.getWidth(), image.getHeight(), r);});
        }
    }

    class CachedOptimizedCanvasRenderer extends OptimizedCanvasRenderer {
        private final Cache<Integer, Component> cache = Caffeine.newBuilder().expireAfterWrite(Duration.of(30, ChronoUnit.MINUTES)).build();

        @Override
        public Frame render(Canvas canvas) {
            BufferedImage image = ((Canvas.InternalCanvas) canvas).image();
            if (cache.asMap().containsKey(canvas.hashCode())) {
                return Frame.wrap(cache.getIfPresent(canvas.hashCode()), image.getWidth(), image.getHeight(), -1);
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
                remove = remove.place(possibility.first().sub(Vec2i.of(box.size().x(), box.size().y()), Vec2i.of(box.pos().x(), box.pos().y())), Vec2i.of(box.pos().x(), box.pos().y()));
            }
            String replace = MiniMessage.miniMessage().serialize(super.render(remove).component());
            String result = find.replaceAll(replace, find);
            return Frame.wrap(MiniMessage.miniMessage().deserialize(result), image.getWidth(), image.getHeight(), -1);
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
