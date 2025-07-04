package me.combimagnetron.sunscreen.menu.input;

import com.github.retrooper.packetevents.protocol.component.ComponentTypes;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemCustomModelData;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import me.combimagnetron.passport.internal.menu.AnvilMenu;
import me.combimagnetron.passport.internal.menu.Title;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.ShaderHelper;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public interface TextInput {

    void open();

    TextInput defaultValue(Component component);

    TextInput position(Position position);

    String input();

    void handle(String input);

    int encoded();

    boolean changed();

    void quit();

    class Impl implements TextInput {
        private final SunscreenUser<?> viewer;
        public final AnvilMenu menu;
        private Position position = Position.pixel(0, 0);
        private String lastInput = "";
        private String input = "";

        protected Impl(SunscreenUser<?> viewer) {
            this.viewer = viewer;
            this.menu = AnvilMenu.of(viewer);
            open();
        }

        @Override
        public void open() {
            menu.title(Title.fixed(Component.empty()));
            ArrayList<ItemStack> items = new ArrayList<>();
            for (int i = 0; i < 39; i++) {
                if (i == 0) {
                    items.add(ItemStack.builder().type(ItemTypes.PAPER).component(ComponentTypes.CUSTOM_MODEL_DATA_LISTS, new ItemCustomModelData(List.of(2000f), List.of(), List.of(), List.of())).component(ComponentTypes.ITEM_NAME, Component.empty()).amount(1).build());
                    continue;
                }
                items.add(ItemStack.EMPTY);
            }
            viewer.connection().send(new WrapperPlayServerWindowItems(menu.windowId(), 0, items, ItemStack.EMPTY));
        }

        @Override
        public TextInput defaultValue(Component component) {
            return null;
        }

        @Override
        public TextInput position(Position position) {
            this.position = position;
            return this;
        }

        @Override
        public String input() {
            return input;
        }

        @Override
        public void handle(String input) {
            this.lastInput = this.input;
            this.input = input;
        }

        @Override
        public int encoded() {
            return -ShaderHelper.encode(position, viewer);
        }

        @Override
        public boolean changed() {
            return !input.equals(lastInput);
        }

        @Override
        public void quit() {
            menu.close();
        }
    }

}
