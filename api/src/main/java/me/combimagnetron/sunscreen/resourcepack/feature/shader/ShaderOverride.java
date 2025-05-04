package me.combimagnetron.sunscreen.resourcepack.feature.shader;

import me.combimagnetron.sunscreen.resourcepack.CodeBlock;

public interface ShaderOverride {

    CodeBlock codeBlock();

    String target();

    OverrideType type();

    enum OverrideType {
        BEFORE,
        AFTER,
        REPLACE
    }

}
