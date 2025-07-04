package me.combimagnetron.sunscreen.style;

import me.combimagnetron.sunscreen.util.FileProvider;
import me.combimagnetron.sunscreen.util.Values;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public record Text(String content, Font font) {
    private static final Font Vanilla = Font.load("vanilla", FileProvider.resource().find("minecraft_font.ttf"), 8);
    private static final Font Five = Font.load("five", FileProvider.resource().find("minecraft_five.ttf"), 5.5f, -0.15f);
    public static final Values<Font> Fonts = Values.of(Vanilla, Five);

    public static Text text(String content, Font font) {
        return new Text(content, font);
    }

    public static Text text(String content) {
        return new Text(content, Vanilla);
    }


    public record Font(String name, java.awt.Font internal, float size) {

        public static Font vanilla() {
            return Vanilla;
        }

        public static Font five() {
            return Five;
        }

        public static Font load(File provider, float size) {
            try {
                return new Font(provider.getName(), java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, provider).deriveFont(size), size);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static Font load(File provider, float size, float tracking) {
            try {
                return new Font(provider.getName(), java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, provider).deriveFont(size).deriveFont(Map.of(TextAttribute.TRACKING, tracking)), size);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static Font load(String name, File provider, float size) {
            try {
                return new Font(name, java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, provider).deriveFont(size), size);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static Font load(String name, File provider, float size, float tracking) {
            try {
                return new Font(name, java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, provider).deriveFont(size).deriveFont(Map.of(TextAttribute.TRACKING, tracking)), size);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }
        }



    }

}
