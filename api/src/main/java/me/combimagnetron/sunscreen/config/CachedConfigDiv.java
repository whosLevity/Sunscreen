package me.combimagnetron.sunscreen.config;

import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.passport.util.condition.Condition;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public record CachedConfigDiv(Collection<Section> elements, Identifier identifier, Collection<RuntimeDefinableGeometry.GeometryBuilder<?>> geometry, @Nullable Condition condition, double order) {

}
