package me.combimagnetron.sunscreen.resourcepack.feature.font;

import me.combimagnetron.sunscreen.resourcepack.Asset;
import me.combimagnetron.sunscreen.resourcepack.sprite.Sprite;
import me.combimagnetron.sunscreen.util.Pair;

public interface Glyph extends Asset {

    char character();

    Pair<Integer, Integer> heightAndAscent();

}
