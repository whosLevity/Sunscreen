package me.combimagnetron.sunscreen.resourcepack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class ResourcePackPath implements Comparable<ResourcePackPath> {
    private final Path path;

    protected ResourcePackPath(Path path) {
        this.path = path;
    }


    @Override
    public int compareTo(@NotNull ResourcePackPath o) {
        return o.path().compareTo(path);
    }

    public Path path() {
        return path;
    }

    public Path resolve(ResourcePackFeature<?, ?> feature) {
        return path;
    }

}
