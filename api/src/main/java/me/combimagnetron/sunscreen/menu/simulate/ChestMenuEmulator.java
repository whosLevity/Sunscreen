package me.combimagnetron.sunscreen.menu.simulate;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.internal.menu.ChestMenu;
import me.combimagnetron.passport.util.Pos2D;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentIteratorFlag;
import net.kyori.adventure.text.ComponentIteratorType;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.Iterator;

public interface ChestMenuEmulator {

    ChestMenu chestMenu();

    void click(int slot);

    Settings settings();

    void close();

    default void test() {
        Item item = chestMenu().contents().get(Pos2D.of(3, 2));
        Item found = chestMenu().contents().all().stream().filter(i -> i.lore().stream().anyMatch(s -> s.contains(Component.text("Hello")))).findFirst().get();
        Iterator<Component> iterator = item.name().iterator(ComponentIteratorType.DEPTH_FIRST, ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS);
        while (iterator.hasNext()) {
            Component next = iterator.next();
            System.out.println(PlainTextComponentSerializer.plainText().serialize(next));
        }
    }

    static ChestMenuEmulator of(ChestMenu chestMenu, Settings settings) {
        return new Impl(chestMenu, settings);
    }

    class Impl implements ChestMenuEmulator {
        private final ChestMenu chestMenu;
        private final Settings settings;

        public Impl(ChestMenu chestMenu, Settings settings) {
            this.chestMenu = chestMenu;
            this.settings = settings;
        }

        @Override
        public ChestMenu chestMenu() {
            return chestMenu;
        }

        @Override
        public Settings settings() {
            return settings;
        }

        @Override
        public void click(int slot) {
            int x = slot % 9;
            int y = slot / 9;
            chestMenu.click(Pos2D.of(x, y));
        }

        @Override
        public void close() {
            chestMenu.close();
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
