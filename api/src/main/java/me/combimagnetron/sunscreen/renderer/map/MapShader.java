package me.combimagnetron.sunscreen.renderer.map;

import me.combimagnetron.sunscreen.resourcepack.CodeBlock;
import me.combimagnetron.sunscreen.resourcepack.feature.shader.Shader;
import me.combimagnetron.sunscreen.resourcepack.feature.shader.ShaderOverride;

import java.util.Collection;
import java.util.List;

public class MapShader implements Shader {
    @Override
    public Collection<ShaderOverride> shaderOverrides() {
        return List.of();
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public String version() {
        return "0.1.0";
    }

    @Override
    public String description() {
        return "Contains the map renderer and protocol implementation.";
    }

    @Override
    public String author() {
        return "Combimagnetron, OmeJan";
    }

    @Override
    public Section fracture() {
        return Section.of(
                CodeBlock.shader()
                        .line("void main() {")
                ,
                CodeBlock.shader()
                        .line("#include <fracture.frag>")
                        .line("#include <fracture.vert>")
                ,
                CodeBlock.shader()
                        .line("uniform sampler2D map_texture;")
                        .line("uniform vec2 map_size;")
                        .line("uniform vec2 map_position;")
                        .line("uniform vec2 map_scale;")
                        .line("uniform vec2 map_rotation;")
                ,
                CodeBlock.shader()
                        .line("vec2 map_position = vec2(0.0, 0.0);")
                        .line("vec2 map_scale = vec2(1.0, 1.0);")
                        .line("vec2 map_rotation = vec2(0.0, 0.0);")
        );
    }

    @Override
    public Section vertex() {
        return Section.of(
                CodeBlock.shader()
                        .line("void main() {")
                ,
                CodeBlock.shader()
                        .line("#moj_import <sunscreen_map.glsl>")
                        .line("#include <fracture.vert>")
                ,
                CodeBlock.shader()
                        .line("uniform sampler2D map_texture;")
                        .line("uniform vec2 map_size;")
                        .line("uniform vec2 map_position;")
                        .line("uniform vec2 map_scale;")
                        .line("uniform vec2 map_rotation;")
                ,
                CodeBlock.shader()
                        .line("vec2 map_position = vec2(0.0, 0.0);")
                        .line("vec2 map_scale = vec2(1.0, 1.0);")
                        .line("vec2 map_rotation = vec2(0.0, 0.0);")
        );
    }

}
