package me.combimagnetron.sunscreen.resourcepack.feature.shader;

import me.combimagnetron.sunscreen.resourcepack.CodeBlock;

public interface ShaderOverride {

    CodeBlock codeBlock();

    String before();

    String after();

    String target();

}
