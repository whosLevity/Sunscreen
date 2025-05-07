package me.combimagnetron.sunscreen.resourcepack.feature.shader;

import me.combimagnetron.sunscreen.resourcepack.FeatureWriter;
import me.combimagnetron.sunscreen.resourcepack.PackSection;
import me.combimagnetron.sunscreen.resourcepack.ResourcePackFeature;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

public class ShaderFeature implements ResourcePackFeature<ShaderFeature, Shader> {
    private static final ShaderFeatureWriter WRITER = new ShaderFeatureWriter();
    private final Collection<Shader> shaders = new LinkedList<>();

    @Override
    public ResourcePackFeature<ShaderFeature, Shader> asset(@NotNull Shader asset) {
        if (shaders.contains(asset)) {
            return this;
        }
        shaders.add(asset);
        return this;
    }

    @Override
    public Class<Shader> assetClass() {
        return Shader.class;
    }

    @Override
    public Collection<Shader> assets() {
        return shaders;
    }

    @Override
    public FeatureWriter<ShaderFeature> writer() {
        return WRITER;
    }


    @Override
    public PackSection write() {
        return WRITER.write(this, null, null);
    }

}
