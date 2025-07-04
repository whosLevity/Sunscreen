package me.combimagnetron.sunscreen.resourcepack.feature.shader;

import me.combimagnetron.sunscreen.resourcepack.FeatureWriter;
import me.combimagnetron.sunscreen.resourcepack.PackSection;
import me.combimagnetron.sunscreen.resourcepack.ResourcePack;
import me.combimagnetron.sunscreen.resourcepack.WrittenAsset;
import me.combimagnetron.sunscreen.resourcepack.meta.PackVersion;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShaderFeatureWriter implements FeatureWriter<ShaderFeature> {

    @Override
    public PackSection write(ShaderFeature feature, @Nullable ResourcePack pack, @Nullable PackVersion version) {
        if (version == null) {
            version = pack.fallback();
        }
        Collection<Shader> shaders = feature.assets();
        PackVersion finalVersion = version;
        Shader shader = shaders.stream()
                .filter(s -> s.version().equals(finalVersion))
                .findFirst()
                .orElse(null);
        if (shader == null) {
            return null;
        }
        Shader.Section.MojangSpec fragment = (Shader.Section.MojangSpec) shader.fragment();
        Shader.Section.MojangSpec vertex = (Shader.Section.MojangSpec) shader.vertex();
        insert(shader.shaderOverrides(), fragment);
        insert(shader.shaderOverrides(), vertex);
        WrittenAsset.WrittenFile customFragment = WrittenAsset.file(
                shader.path().path().resolve(shader.name() + ".fsh"),
                shader.customFragment().all()
        );
        WrittenAsset.WrittenFile customVertex = WrittenAsset.file(
                shader.path().path().resolve(shader.name() + ".vsh"),
                shader.customVertex().all()
        );
        WrittenAsset.WrittenFile mojangFragment = WrittenAsset.file(
                shader.path().path().resolve(shader.name() + ".fsh"),
                fragment.all()
        );
        WrittenAsset.WrittenFile mojangVertex = WrittenAsset.file(
                shader.path().path().resolve(shader.name() + ".vsh"),
                vertex.all()
        );
        List.of(shader.vertex(), shader.fragment(), shader.customVertex(), shader.customFragment()).forEach(shaderEntry -> {
            switch (shaderEntry) {
                case null -> {}
                case Shader.Section.CustomSpec customSpec -> shader.shaderOverrides().add(customSpec.get());
                case Shader.Section.MojangSpec mojangSpec -> insert(shader.shaderOverrides(), mojangSpec);
                default -> {
                }
            }

        });
        return PackSection.of(
                List.of(customFragment, customVertex, mojangFragment, mojangVertex),
                shader.path()
        );
    }

    private void insert(Collection<ShaderOverride> shaderOverrides, Shader.Section.MojangSpec fragment) {
        if (fragment == null) {
            return;
        }
        for (ShaderOverride shaderOverride : shaderOverrides) {
            ShaderOverride.OverrideType type = shaderOverride.type();
            String target = shaderOverride.target();
            ArrayList<String> content = new ArrayList<>(fragment.all().content().stream().map(String::trim).toList());
            int index = content.indexOf(target);
            if (type == ShaderOverride.OverrideType.BEFORE) {
                for (int i = 0; i < shaderOverride.codeBlock().content().size(); i++) {
                    content.add(index - i, ((ArrayList<String>) shaderOverride.codeBlock().content()).get(i));
                }
            } else if (type == ShaderOverride.OverrideType.REPLACE) {
                content.remove(index);
                for (int i = 0; i < shaderOverride.codeBlock().content().size(); i++) {
                    content.add(index + i,((ArrayList<String>) shaderOverride.codeBlock().content()).get(i));
                }
            } else if (type == ShaderOverride.OverrideType.AFTER) {
                for (int i = 0; i < shaderOverride.codeBlock().content().size(); i++) {
                    content.add(index + i + 1, ((ArrayList<String>) shaderOverride.codeBlock().content()).get(i));
                }
            }
        }
    }

}
