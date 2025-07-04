package me.combimagnetron.sunscreen.resourcepack;

import me.combimagnetron.sunscreen.resourcepack.meta.PackVersion;
import org.jetbrains.annotations.Nullable;

public interface FeatureWriter<T extends ResourcePackFeature<?, ? extends Asset>> {

    PackSection write(T feature, ResourcePack pack, @Nullable PackVersion version);

    default PackSection write(T feature, ResourcePack pack) {
        return write(feature, pack, null);
    }

}
