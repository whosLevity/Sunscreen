package me.combimagnetron.sunscreen.resourcepack.feature.font;

import me.combimagnetron.sunscreen.resourcepack.ResourcePackFeature;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;

public interface Font extends ResourcePackFeature<Font> {

    Identifier identifier();

    int size();

    Collection<Glyph> glyphs();

}
