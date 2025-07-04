package me.combimagnetron.sunscreen.resourcepack.feature.font;

import me.combimagnetron.sunscreen.resourcepack.Asset;
import me.combimagnetron.sunscreen.resourcepack.CodeBlock;
import me.combimagnetron.sunscreen.resourcepack.ResourcePackFeature;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;

public interface Font extends Asset {

    Identifier identifier();

    int size();

    Collection<Glyph> glyphs();

    CodeBlock codeBlock();

}
