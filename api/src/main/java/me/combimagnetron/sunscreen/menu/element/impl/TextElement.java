package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.style.Text;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TextElement extends SimpleBufferedElement {
    private final Options options;
    private Text text;

    public static TextElement textElement(Identifier identifier, Position position, Text text) {
        return new TextElement(identifier, position, Vec2d.of(256, 256), text, Options.options());
    }

    public static TextElement textElement(Identifier identifier, Position position, Text text, Options options) {
        return new TextElement(identifier, position, Vec2d.of(256, 256), text, options);
    }

    private TextElement(Identifier identifier, Position position, Vec2d size, Text text, Options options) {
        super(size, identifier, position);
        this.text = text;
        this.options = options;
        text(text);
    }

    public Element text(Text text) {
        this.text = text;
        render();
        return this;
    }

    private void render() {
        BufferedImage image = ((Canvas.StaticImpl) canvas).image();
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(text.font().internal());
        if (options.lineBreaks && text.content().contains("\n")) {
            String[] lines = text.content().split("\n");
            for (int i = 0; i < lines.length; i++) {
                graphics.drawString(lines[i], 0, 10 + i * 10);
            }
        } else if (options.wordWrap && text.font().internal().getStringBounds(text.content(), new FontRenderContext(new AffineTransform(), true, true)).getWidth() > size().x()) {
            String[] words = text.content().split(" ");
            StringBuilder line = new StringBuilder();
            int y = 10;
            for (String word : words) {
                if (text.font().internal().getStringBounds(line + word, new FontRenderContext(new AffineTransform(), true, true)).getWidth() > size().x()) {
                    graphics.drawString(line.toString(), 0 , 11 * y);
                    line = new StringBuilder();
                    y++;
                }
                line.append(word).append(" ");
            }
        } else {
            graphics.drawString(text.content(), 0, 10);
        }
        graphics.dispose();
        canvas = Canvas.image(image).trim();
    }

    public Text text() {
        return text;
    }

    @Override
    public Element position(Position pos) {
        return null;
    }

    @Override
    public <T> Element style(Style<T> style, Position pos2D, T t) {
        this.canvas = style.edit(canvas, pos2D, t);
        return this;
    }

    @Override
    public <T> Element style(Style<T> style, T t) {
        this.canvas = style.edit(canvas, Position.pixel(0, 0), t);
        return this;
    }

    @Override
    public Canvas canvas() {
        return canvas;
    }

    public record Options(boolean wordWrap, boolean lineBreaks, boolean shadow) {
        public static Options options(boolean wordWrap, boolean lineBreaks, boolean shadow) {
            return new Options(wordWrap, lineBreaks, shadow);
        }

        public static Options options(boolean wordWrap, boolean lineBreaks) {
            return new Options(wordWrap, lineBreaks, false);
        }

        public static Options options(boolean wordWrap) {
            return new Options(wordWrap, false, false);
        }

        public static Options options() {
            return new Options(true, true, false);
        }

        public Options wordWrap(boolean wordWrap) {
            return new Options(wordWrap, lineBreaks, shadow);
        }

        public Options lineBreaks(boolean lineBreaks) {
            return new Options(wordWrap, lineBreaks, shadow);
        }

        public Options shadow(boolean shadow) {
            return new Options(wordWrap, lineBreaks, shadow);
        }

    }

}
