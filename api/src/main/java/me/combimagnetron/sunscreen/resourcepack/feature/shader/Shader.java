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

        static Section mojang(CodeBlock main, CodeBlock imports, CodeBlock uniforms, CodeBlock functions) {
            return MojangSpec.of(main, imports, uniforms, functions);
        }

        static Section custom(CodeBlock main, CodeBlock imports, String name) {
            return CustomSpec.of(main, imports, name);
        }

        interface MojangSpec extends Section {

            static Section of(CodeBlock main, CodeBlock imports, CodeBlock uniforms, CodeBlock functions) {
                return new Impl(main, imports, uniforms, functions);
            }

            CodeBlock main();

            CodeBlock imports();

            CodeBlock uniforms();

            CodeBlock functions();

            default CodeBlock all() {
                return CodeBlock.all(main(), imports(), uniforms(), functions());
            }

            record Impl(CodeBlock main, CodeBlock imports, CodeBlock uniforms, CodeBlock functions) implements MojangSpec {

            }

        }

        interface CustomSpec extends Section {

            static Section of(CodeBlock main, CodeBlock imports, String name) {
                return new Impl(main, imports, name);
            }

            String name();

            CodeBlock main();

            CodeBlock imports();

            default CodeBlock all() {
                return CodeBlock.all(main(), imports());
            }

            record Impl(CodeBlock main, CodeBlock imports, String name) implements CustomSpec {

            }

        }


        CodeBlock all();

        default String content() {
            return all().content().stream().reduce("", (a, b) -> a + "\n" + b);
        }

    }

}
