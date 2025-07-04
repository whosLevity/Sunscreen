package me.combimagnetron.sunscreen.resourcepack.meta;

import me.combimagnetron.sunscreen.resourcepack.CodeBlock;
import me.combimagnetron.sunscreen.resourcepack.sprite.Sprite;

import java.util.Objects;

public interface PackMeta {

    CodeBlock.JsonCodeBlock content();

    PackVersion version();

    String description();

    String name();

    Sprite icon();

    static PackMeta meta(PackVersion version, String description, String name, Sprite icon) {
        return new Impl(version, description, name, icon);
    }

    static PackMeta meta(PackVersion version, String description, String name) {
        return new Impl(version, description, name);
    }

    final class Impl implements PackMeta {
        private final PackVersion version;
        private final String description;
        private final String name;
        private Sprite icon;

        public Impl(PackVersion version, String description, String name, Sprite icon) {
            this.version = version;
            this.description = description;
            this.name = name;
            this.icon = icon;
        }

        public Impl(PackVersion version, String description, String name) {
            this(version, description, name, null);
        }

        @Override
        public CodeBlock.JsonCodeBlock content() {
            return null;
        }

        @Override
        public PackVersion version() {
            return version;
        }

        @Override
        public String description() {
            return description;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public Sprite icon() {
            return icon;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Impl) obj;
            return Objects.equals(this.version, that.version) &&
                    Objects.equals(this.description, that.description) &&
                    Objects.equals(this.name, that.name) &&
                    Objects.equals(this.icon, that.icon);
        }

        @Override
        public int hashCode() {
            return Objects.hash(version, description, name, icon);
        }

        @Override
        public String toString() {
            return "Impl[" +
                    "version=" + version + ", " +
                    "description=" + description + ", " +
                    "name=" + name + ", " +
                    "icon=" + icon + ']';
        }

        }

}
