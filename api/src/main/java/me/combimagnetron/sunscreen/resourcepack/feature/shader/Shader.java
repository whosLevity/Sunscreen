package me.combimagnetron.sunscreen.resourcepack.feature.shader;

import me.combimagnetron.sunscreen.resourcepack.Asset;
import me.combimagnetron.sunscreen.resourcepack.CodeBlock;
import me.combimagnetron.sunscreen.resourcepack.meta.PackVersion;

import java.util.Collection;

public interface Shader extends Asset {

    Collection<ShaderOverride> shaderOverrides();

    PackVersion version();

    String name();

    String description();

    String author();

    Section fragment();

    Section vertex();

    Section customFragment();

    Section customVertex();

    interface Section {

        static Section mojang(CodeBlock<String> main, CodeBlock<String> imports, CodeBlock<String> uniforms, CodeBlock<String> functions) {
            return MojangSpec.of(main, imports, uniforms, functions);
        }

        static Section custom(CodeBlock<String> main, CodeBlock<String> imports, String name, ShaderOverride.OverrideType type, String target) {
            return CustomSpec.of(main, imports, name, type, target);
        }

        interface MojangSpec extends Section {

            static Section of(CodeBlock<String> main, CodeBlock<String> imports, CodeBlock<String> uniforms, CodeBlock<String> functions) {
                return new Impl(main, imports, uniforms, functions);
            }

            CodeBlock<String> main();

            CodeBlock<String> imports();

            CodeBlock<String> uniforms();

            CodeBlock<String> functions();

            default CodeBlock<String> all() {
                return CodeBlock.all(main(), imports(), uniforms(), functions());
            }

            record Impl(CodeBlock<String> main, CodeBlock<String> imports, CodeBlock<String> uniforms, CodeBlock<String> functions) implements MojangSpec {

            }

        }

        interface CustomSpec extends Section {

            static Section of(CodeBlock<String> main, CodeBlock<String> imports, String name, ShaderOverride.OverrideType type, String target) {
                return new Impl(main, imports, name, type, target);
            }

            String name();

            CodeBlock<String> main();

            CodeBlock<String> imports();

            ShaderOverride.OverrideType override();

            String target();

            default CodeBlock<String> all() {
                return CodeBlock.all(main(), imports());
            }

            default ShaderOverride get() {
                return new ShaderOverride.Impl(CodeBlock.shader().line("#moj_import <include/" + name() + ".glsl>"), target(), override());
            }

            record Impl(CodeBlock<String> main, CodeBlock<String> imports, String name, ShaderOverride.OverrideType override, String target) implements CustomSpec {

            }

        }


        CodeBlock<String> all();

        default String content() {
            return all().content().stream().reduce("", (a, b) -> a + "\n" + b);
        }

    }

}
