package me.combimagnetron.sunscreen.menu.input;

import me.combimagnetron.sunscreen.resourcepack.CodeBlock;
import me.combimagnetron.sunscreen.resourcepack.ResourcePackPath;
import me.combimagnetron.sunscreen.resourcepack.feature.shader.Shader;
import me.combimagnetron.sunscreen.resourcepack.feature.shader.ShaderOverride;
import me.combimagnetron.sunscreen.resourcepack.meta.PackVersion;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Range;

import java.util.Collection;
import java.util.List;

public class InputBackgroundShader implements Shader {
    @Override
    public Collection<ShaderOverride> shaderOverrides() {
        return List.of();
    }

    @Override
    public PackVersion version() {
        return PackVersion.version(Range.of(48, 59));
    }

    @Override
    public String name() {
        return "gui";
    }

    @Override
    public String description() {
        return "Removes background from the input anvil.";
    }

    @Override
    public String author() {
        return "Combimagnetron";
    }

    @Override
    public Section fragment() {
        return Section.mojang(
                CodeBlock.shader()
                        .line("void main() {")
                        .line("    vec4 color = vertexColor;")
                        .line("    if (color.a == 0.0) {")
                        .line("        discard;")
                        .line("    }")
                        .line("    fragColor = color * ColorModulator;")
                        .line("}"),
                CodeBlock.shader()
                        .line("in vec4 vertexColor;")
                        .line("in float vertexDistance;")
                        .line("in vec3 xyzPos;"),
                CodeBlock.shader()
                        .line("uniform float GameTime;")
                        .line("uniform float GameTime;")
                        .line("uniform vec4 ColorModulator;")
                        .line("uniform vec2 u_resolution;")
                        .line("uniform vec2 ScreenSize;")
                        .line("out vec4 fragColor;"),
                CodeBlock.shader()
        );
    }

    @Override
    public Section vertex() {
        return null;
    }

    @Override
    public Section customFragment() {
        return Section.custom(
                CodeBlock.shader().line("void main() {}"),
                CodeBlock.shader(),
                "input_background_fragment", ShaderOverride.OverrideType.BEFORE, "fragColor = color * ColorModulator;");
    }

    @Override
    public Section customVertex() {
        return null;
    }

    @Override
    public Identifier identifier() {
        return null;
    }

    @Override
    public ResourcePackPath path() {
        return null;
    }
}
