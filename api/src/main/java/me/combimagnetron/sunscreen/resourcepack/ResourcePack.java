package me.combimagnetron.sunscreen.resourcepack;

import me.combimagnetron.sunscreen.resourcepack.meta.PackMeta;
import me.combimagnetron.sunscreen.resourcepack.meta.PackVersion;
import me.combimagnetron.sunscreen.resourcepack.overlay.Overlay;

import java.util.Collection;

public interface ResourcePack {

    Collection<ResourcePackFeature<?, ? extends Asset>> features();

    PackMeta meta();

    PackVersion fallback();

    Collection<Overlay> overlays();

}
