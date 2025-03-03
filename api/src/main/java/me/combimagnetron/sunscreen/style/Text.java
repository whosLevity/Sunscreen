package me.combimagnetron.sunscreen.style;

import me.combimagnetron.sunscreen.util.FileProvider;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public record Text(String content, Font font) {
    private static final Font Vanilla = Font.load(FileProvider.resource().find("minecraft_font.ttf"), 8);

    public static Text text(String content, Font font) {
        return new Text(content, font);
    }

    public static Text text(String content) {
        return new Text(content, Vanilla);
    }



    public record Font(java.awt.Font internal, int size) {

        public static Font vanilla() {
            return Vanilla;
        }

        public static Font load(File provider, int size) {
            try {
                return new Font(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, provider).deriveFont((float)size), size);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
