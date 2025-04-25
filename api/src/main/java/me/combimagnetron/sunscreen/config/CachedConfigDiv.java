package me.combimagnetron.sunscreen.config;

import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;

public record CachedConfigDiv(Collection<Section> elements, Identifier identifier, Collection<RuntimeDefinableGeometry.GeometryBuilder<?>> geometry) {

}
