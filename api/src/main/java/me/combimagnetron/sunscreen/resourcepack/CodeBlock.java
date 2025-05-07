package me.combimagnetron.sunscreen.resourcepack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface CodeBlock<T> {

    Collection<T> content();

    CodeBlock<T> line(T line);

    Language language();

    static CodeBlock<String> shader() {
        return new Impl<>(new ArrayList<>(), Language.GLSL);
    }

    static JsonCodeBlock json() {
        return new JsonCodeBlock();
    }

    @SafeVarargs
    static <T> CodeBlock<T> all(CodeBlock<T>... blocks) {
        Collection<T> content = new ArrayList<>();
        Language language = Language.GLSL;
        for (CodeBlock<T> block : blocks) {
            content.addAll(block.content());
            language = block.language();
        }
        return new Impl<>(content, language);
    }

    class JsonCodeBlock implements CodeBlock<JsonElement> {
        JsonObject jsonObject = new JsonObject();

        @Override
        public Collection<JsonElement> content() {
            return jsonObject.entrySet().stream().map(Map.Entry::getValue).toList();
        }

        @Override
        public JsonCodeBlock line(JsonElement line) {
            jsonObject.add(line.getAsString(), line);
            return this;
        }

        public JsonCodeBlock line(String key, JsonElement value) {
            jsonObject.add(key, value);
            return this;
        }

        @Override
        public Language language() {
            return Language.JSON;
        }
    }

    record Impl<T>(Collection<T> content, Language language) implements CodeBlock<T> {

        @Override
        public CodeBlock<T> line(T line) {
            content.add(line);
            return this;
        }

    }

}
