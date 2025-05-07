package me.combimagnetron.sunscreen.resourcepack.feature.overlay;

import me.combimagnetron.sunscreen.resourcepack.FeatureWriter;
import me.combimagnetron.sunscreen.resourcepack.PackSection;
import me.combimagnetron.sunscreen.resourcepack.ResourcePackFeature;

import java.util.ArrayList;
import java.util.Collection;

public class OverlayFeature implements ResourcePackFeature<OverlayFeature, Overlay> {
    private static final OverlayFeatureWriter WRITER = new OverlayFeatureWriter();
    private final Collection<Overlay> overlays = new ArrayList<>();

    @Override
    public Class<Overlay> assetClass() {
        return Overlay.class;
    }

    @Override
    public ResourcePackFeature<OverlayFeature, Overlay> asset(Overlay asset) {
        overlays.add(asset);
        return this;
    }

    @Override
    public Collection<Overlay> assets() {
        return overlays;
    }

    @Override
    public FeatureWriter<OverlayFeature> writer() {
        return WRITER;
    }

    @Override
    public PackSection write() {
        return WRITER.write(this, null, null);
    }
}
