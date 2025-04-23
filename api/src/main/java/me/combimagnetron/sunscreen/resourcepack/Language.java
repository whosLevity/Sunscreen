package me.combimagnetron.sunscreen.resourcepack;

import me.combimagnetron.sunscreen.resourcepack.feature.font.FontFeature;
import me.combimagnetron.sunscreen.resourcepack.feature.shader.ShaderFeature;

public interface Language {
    Language JSON = of("json", FontFeature.class);
    Language GLSL = of("glsl", ShaderFeature.class);

    String language();

    Class<? extends ResourcePackFeature<?>> applicableTo();

    static Language of(String language, Class<? extends ResourcePackFeature<?>> applicableTo) {
        return new Language() {
            @Override
            public String language() {
                return language;
            }

            @Override
            public Class<? extends ResourcePackFeature<?>> applicableTo() {
                return applicableTo;
            }
        };
    }

}
