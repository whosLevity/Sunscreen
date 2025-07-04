package me.combimagnetron.sunscreen.resourcepack;

import java.util.Collection;

public interface PackSection {

    Collection<WrittenAsset> files();

    ResourcePackPath path();

    static PackSection of(Collection<WrittenAsset> files, ResourcePackPath path) {
        return new Impl(files, path);
    }

    record Impl(Collection<WrittenAsset> files, ResourcePackPath path) implements PackSection {

    }

}
