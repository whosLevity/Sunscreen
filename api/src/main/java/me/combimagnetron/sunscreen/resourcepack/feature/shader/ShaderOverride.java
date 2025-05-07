package me.combimagnetron.sunscreen.resourcepack.feature.shader;

import me.combimagnetron.sunscreen.resourcepack.CodeBlock;

public interface ShaderOverride {

    CodeBlock<String> codeBlock();

    String target();

    OverrideType type();

    static ShaderOverride before(CodeBlock<String> codeBlock, String target) {
        return new Impl(codeBlock, target, OverrideType.BEFORE);
    }

    static ShaderOverride after(CodeBlock<String> codeBlock, String target) {
        return new Impl(codeBlock, target, OverrideType.AFTER);
    }

    static ShaderOverride replace(CodeBlock<String> codeBlock, String target) {
        return new Impl(codeBlock, target, OverrideType.REPLACE);
    }

    record Impl(CodeBlock<String> codeBlock, String target, OverrideType type) implements ShaderOverride {

    }

    enum OverrideType {
        BEFORE,
        AFTER,
        REPLACE
    }

}
