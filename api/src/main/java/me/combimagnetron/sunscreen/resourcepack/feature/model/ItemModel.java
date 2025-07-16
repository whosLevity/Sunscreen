package me.combimagnetron.sunscreen.resourcepack.feature.model;

import me.combimagnetron.sunscreen.resourcepack.ResourcePackPath;

public interface ItemModel {

    ResourcePackPath path();

    interface Modern extends ItemModel {

        String model();

    }

}
