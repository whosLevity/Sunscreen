package me.combimagnetron.sunscreen.resourcepack.feature.model;

import me.combimagnetron.sunscreen.resourcepack.ResourcePackPath;

public interface ItemModel {

    ResourcePackPath path();



    interface Legacy extends ItemModel {

        int customModelData();

    }

    interface Modern extends ItemModel {

        String model();

    }

}
