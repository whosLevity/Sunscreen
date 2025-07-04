package me.combimagnetron.sunscreen.resourcepack;

import me.combimagnetron.sunscreen.SunscreenLibrary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public interface ResourcePackFeature<F extends ResourcePackFeature<F, A>, A extends Asset> extends Writable<PackSection> {
    Path ASSETS_FOLDER = Path.of("assets/sunscreen/");

    default void write(Path path, Collection<String> content) {
        try {
            Files.write(path, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    Class<A> assetClass();

    ResourcePackFeature<F, A> asset(A asset);

    Collection<A> assets();

    FeatureWriter<F> writer();

}
