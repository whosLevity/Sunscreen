package me.combimagnetron.sunscreen;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.component.ComponentTypes;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemModel;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.nbt.*;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.blockentity.BlockEntityTypes;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientNameItem;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUpdateSign;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.menu.AnvilMenu;
import me.combimagnetron.passport.internal.menu.Title;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ModDetector {

    @NotNull
    Collection<String> mods();

    @NotNull
    static ModDetector sign(@NotNull SunscreenUser<?> user) {
        return new SignModDetector(user);
    }

    @NotNull
    static ModDetector anvil(@NotNull SunscreenUser<?> user) {
        return new AnvilInputDetector(user);
    }

    final class SignModDetector implements ModDetector {
        private static final NBTString EMPTY_STRING_COMPOUND = new NBTString("");
        private final List<String> mods = new ArrayList<>();
        private final SunscreenUser<?> user;
        private final Vector3d currentPosition;
        private PacketListenerCommon listener;

        private SignModDetector(@NotNull SunscreenUser<?> user) {
            this.user = user;
            this.currentPosition = user.position();
            this.listener = PacketEvents.getAPI().getEventManager().registerListener(new PacketListener() {
                @Override
                public void onPacketReceive(@NotNull PacketReceiveEvent event) {
                    if (!event.getPacketType().equals(PacketType.Play.Client.EDIT_BOOK)) {
                        return;
                    }
                    WrapperPlayClientUpdateSign clientUpdateSign = new WrapperPlayClientUpdateSign(event);
                    mods.add(clientUpdateSign.getTextLines()[0]);
                    cancel();
                }
            }, PacketListenerPriority.NORMAL);

        }

        private void cancel() {
            PacketEvents.getAPI().getEventManager().unregisterListener(listener);
        }

        public void sendSign(String id) {
            Vector3i blockPosition = new Vector3i((int) currentPosition.x(), (int) currentPosition.y(), (int) currentPosition.z());
            NBTCompound nbtCompound = new NBTCompound();
            NBTCompound frontText = new NBTCompound();
            System.out.println(id);
            frontText.setTag("has_glowing_text", new NBTByte((byte)0));
            frontText.setTag("color", new NBTString("black"));
            frontText.setTag("messages", new NBTList<>(NBTType.STRING, List.of(new NBTString(GsonComponentSerializer.gson().serialize(Component.translatable(id))), EMPTY_STRING_COMPOUND, EMPTY_STRING_COMPOUND, EMPTY_STRING_COMPOUND)));
            nbtCompound.setTag("front_text", frontText);
            Player player = (Player) user.platformSpecificPlayer();
            player.sendBlockChange(player.getLocation(), Material.ACACIA_SIGN.createBlockData());
            WrapperPlayServerBlockEntityData serverBlockEntityData = new WrapperPlayServerBlockEntityData(blockPosition, BlockEntityTypes.SIGN, nbtCompound);
            WrapperPlayServerOpenSignEditor serverOpenSignEditor = new WrapperPlayServerOpenSignEditor(blockPosition, true);
            user.connection().send(serverBlockEntityData);
            user.connection().send(serverOpenSignEditor);
        }

        @Override
        public @NotNull Collection<String> mods() {
            return mods;
        }

    }

    final class AnvilInputDetector implements ModDetector {
        private final List<String> strings = new ArrayList<>();
        private final SunscreenUser<?> user;
        private final PacketListenerCommon listener;

        public AnvilInputDetector(SunscreenUser<?> user) {
            this.user = user;
            AnvilMenu menu = AnvilMenu.of(user);
            menu.title(Title.fixed(Component.empty()));
            this.listener = PacketEvents.getAPI().getEventManager().registerListener(new PacketListener() {
                @Override
                public void onPacketReceive(@NotNull PacketReceiveEvent event) {
                    if (!event.getPacketType().equals(PacketType.Play.Client.NAME_ITEM)) {
                        return;
                    }
                    WrapperPlayClientNameItem clientUpdateSign = new WrapperPlayClientNameItem(event);
                    System.out.println(clientUpdateSign.getItemName());
                    //cancel();
                }
            }, PacketListenerPriority.NORMAL);
            ArrayList<ItemStack> items = new ArrayList<>();
            for (int i = 0; i < 39; i++) {
                if (i == 0) {
                    items.add(ItemStack.builder().type(ItemTypes.PAPER).component(ComponentTypes.ITEM_NAME, Component.translatable("sodium.option_impact.high")).amount(1).build());
                    continue;
                }
                items.add(ItemStack.EMPTY);
            }
            user.connection().send(new WrapperPlayServerWindowItems(menu.windowId(), 0, items, ItemStack.EMPTY));
            user.connection().send(new WrapperPlayServerCloseWindow());
        }

        private void cancel() {
            PacketEvents.getAPI().getEventManager().unregisterListener(listener);
        }

        @Override
        public @NotNull Collection<String> mods() {
            return strings;
        }
    }

}
