package me.combimagnetron.sunscreen.resourcepack.meta;

import me.combimagnetron.sunscreen.resourcepack.CodeBlock;
import me.combimagnetron.sunscreen.resourcepack.sprite.Sprite;

public interface PackMeta {

    CodeBlock content();

    PackVersion version();

    String description();

    String name();

    Sprite icon();

}
