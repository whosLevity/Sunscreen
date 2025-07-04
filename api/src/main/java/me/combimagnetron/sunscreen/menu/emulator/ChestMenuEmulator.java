package me.combimagnetron.sunscreen.menu.emulator;

import com.github.retrooper.packetevents.protocol.component.ComponentType;
import com.github.retrooper.packetevents.protocol.component.StaticComponentType;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.internal.menu.ChestMenu;
import me.combimagnetron.passport.util.Pos2D;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentIteratorFlag;
import net.kyori.adventure.text.ComponentIteratorType;

import java.util.Iterator;

public interface ChestMenuEmulator {

    ChestMenu chestMenu();

    void click(int slot);

    Settings settings();

    void close();

    default void test() {
        Item item = chestMenu().contents().get(Pos2D.of(3, 3));
        Item found = chestMenu().contents().all().stream().filter(i -> i.lore().stream().anyMatch(s -> s.contains(Component.text("Hello")))).findFirst().get();
        Iterator<Component> iterator = item.name().iterator(ComponentIteratorType.DEPTH_FIRST, ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS);
        while (iterator.hasNext()) {
            Component next = iterator.next();
        }
    }

    record Settings(int size, Component title, boolean hide) {
        public static Settings settings(int size, Component title, boolean hide) {
            return new Settings(size, title, hide);
        }

        public static Builder settings() {
            return new Builder();
        }

        public static class Builder {
            private int size;
            private Component title;
            private boolean hide;

            public Builder size(int size) {
                this.size = size;
                return this;
            }

            public Builder title(Component title) {
                this.title = title;
                return this;
            }

            public Builder hide(boolean hide) {
                this.hide = hide;
                return this;
            }

            public Settings build() {
                return new Settings(size, title, hide);
            }
        }

    }

}
