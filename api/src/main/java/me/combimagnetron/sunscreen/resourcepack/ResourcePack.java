package me.combimagnetron.sunscreen.resourcepack;

import me.combimagnetron.sunscreen.resourcepack.feature.shader.ShaderFeature;
import me.combimagnetron.sunscreen.resourcepack.meta.PackMeta;
import me.combimagnetron.sunscreen.resourcepack.meta.PackVersion;
import me.combimagnetron.sunscreen.resourcepack.feature.overlay.Overlay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ResourcePack {

    Collection<ResourcePackFeature<?, ? extends Asset>> features();

    default <T extends Asset, F extends ResourcePackFeature<F, T>> F feature(Class<T> assetClass) {
        return (F) features().stream().filter(f -> f.assetClass().equals(assetClass)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No feature found for asset class: " + assetClass));
    }

    PackMeta meta();

    PackVersion fallback();

    Collection<Overlay> overlays();

    ResourcePack overlay(Overlay overlay);

    ResourcePack meta(PackMeta meta);

    ResourcePack fallback(PackVersion fallback);

    WrittenPack write();

    static ResourcePack with(PackMeta meta) {
        return new Impl(meta, meta.version());
    }

    class Impl implements ResourcePack {
        private final ArrayList<Overlay> overlays = new ArrayList<>();
        private final List<ResourcePackFeature<?, ? extends Asset>> features = List.of(
                new ShaderFeature()
        );
        private PackMeta meta;
        private PackVersion fallback;

        Impl(PackMeta meta, PackVersion fallback) {
            this.meta = meta;
            this.fallback = fallback;
        }

        @Override
        public Collection<ResourcePackFeature<?, ? extends Asset>> features() {
            return features;
        }

        @Override
        public PackMeta meta() {
            return meta;
        }

        @Override
        public PackVersion fallback() {
            return fallback;
        }

        @Override
        public Collection<Overlay> overlays() {
            return overlays;
        }

        @Override
        public ResourcePack overlay(Overlay overlay) {
            overlays.add(overlay);
            return this;
        }

        @Override
        public ResourcePack meta(PackMeta meta) {
            this.meta = meta;
            return this;
        }

        @Override
        public ResourcePack fallback(PackVersion fallback) {
            this.fallback = fallback;
            return this;
        }

        @Override
        public WrittenPack write() {
            return null;
        }
    }

}
