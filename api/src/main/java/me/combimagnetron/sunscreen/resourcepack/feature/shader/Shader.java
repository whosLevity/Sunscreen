package me.combimagnetron.sunscreen.resourcepack.feature.shader;

import me.combimagnetron.sunscreen.resourcepack.CodeBlock;

import java.util.Collection;

public interface Shader {

    Collection<ShaderOverride> shaderOverrides();

    String name();

    String version();

    String description();

    String author();

    Section fracture();

    Section vertex();

    interface Section {

        static Section of(CodeBlock main, CodeBlock imports, CodeBlock uniforms, CodeBlock functions) {
            return new Impl(main, imports, uniforms, functions);
        }

        CodeBlock main();

        CodeBlock imports();

        CodeBlock uniforms();

        CodeBlock functions();

        record Impl(CodeBlock main, CodeBlock imports, CodeBlock uniforms, CodeBlock functions) implements Section {

        }

    }

}
