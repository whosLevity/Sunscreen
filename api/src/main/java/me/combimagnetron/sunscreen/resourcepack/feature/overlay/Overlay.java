package me.combimagnetron.sunscreen.resourcepack.feature.overlay;

import me.combimagnetron.sunscreen.resourcepack.Asset;
import me.combimagnetron.sunscreen.resourcepack.PackSection;
import me.combimagnetron.sunscreen.resourcepack.ResourcePackFeature;
import me.combimagnetron.sunscreen.resourcepack.ResourcePackPath;
import me.combimagnetron.sunscreen.resourcepack.meta.PackVersion;

import java.util.Collection;

public interface Overlay extends Asset {

    PackVersion version();

    Collection<ResourcePackFeature<?, ? extends Asset>> features();

    ResourcePackPath path();

    PackSection write();

    default String name() {
        return path().path().toAbsolutePath().toString();
    }

}
