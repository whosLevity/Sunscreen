package me.combimagnetron.sunscreen.resourcepack;

import me.combimagnetron.sunscreen.SunscreenLibrary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public interface ResourcePackFeature<T> {
    Path ASSETS_FOLDER = SunscreenLibrary.library().path().resolve("pack/assets/sunscreen/");

    default void write(Path path, Collection<String> content) {
        try {
            Files.write(path, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

}
