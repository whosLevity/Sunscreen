package me.combimagnetron.sunscreen.resourcepack;

import java.util.ArrayList;
import java.util.Collection;

public interface CodeBlock {

    Collection<String> content();

    CodeBlock line(String line);

    Language language();

    static CodeBlock shader() {
        return new Impl(new ArrayList<>(), Language.GLSL);
    }

    static CodeBlock json() {
        return new Impl(new ArrayList<>(), Language.JSON);
    }

    static CodeBlock all(CodeBlock... blocks) {
        Collection<String> content = new ArrayList<>();
        Language language = Language.GLSL;
        for (CodeBlock block : blocks) {
            content.addAll(block.content());
            language = block.language();
        }
        return new Impl(content, language);
    }

    record Impl(Collection<String> content, Language language) implements CodeBlock {

        @Override
        public CodeBlock line(String line) {
            content.add(line);
            return this;
        }

    }

}
