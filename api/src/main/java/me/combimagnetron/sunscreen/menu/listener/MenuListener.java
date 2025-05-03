package me.combimagnetron.sunscreen.menu.listener;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.wrapper.play.client.*;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.menu.input.InputHandler;
import me.combimagnetron.sunscreen.menu.simulate.ChestMenuEmulator;
import me.combimagnetron.sunscreen.session.Session;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import net.kyori.adventure.audience.Audience;

import java.util.*;

public class MenuListener implements PacketListener {
    private final List<UUID> inChestEmulator = new ArrayList<>();

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        Optional<SunscreenUser<Audience>> userOptional = SunscreenLibrary.library().users().user(event.getUser().getUUID());
        if (userOptional.isEmpty()) {
            return;
        }
        SunscreenUser<?> user = userOptional.get();
        if (user.session() == null) {
            return;
        }
        switch(event.getPacketType()) {
            case PacketType.Play.Client.PLAYER_ROTATION:
                handlePlayerRotation(new WrapperPlayClientPlayerRotation(event), user);
                break;
            case PacketType.Play.Client.ENTITY_ACTION:
                handleEntityAction(new WrapperPlayClientEntityAction(event), user);
                break;
            case PacketType.Play.Client.ANIMATION:
                handleAnimation(new WrapperPlayClientAnimation(event), user);
                break;
            case PacketType.Play.Client.CHAT_MESSAGE:
                handleChatMessage(new WrapperPlayClientChatMessage(event), user);
                break;
            case PacketType.Play.Client.NAME_ITEM:
                handleItemName(new WrapperPlayClientNameItem(event), user);
                break;
            case PacketType.Play.Client.CLICK_WINDOW:
                handleClickWindow(new WrapperPlayClientClickWindow(event), user);
                break;
            case PacketType.Play.Client.HELD_ITEM_CHANGE:
                handleHeldItemChange(new WrapperPlayClientHeldItemChange(event), user);
                break;
            case PacketType.Play.Client.INTERACT_ENTITY:
                handleInteractEntity(event, user);
                break;
            case PacketType.Play.Client.DEBUG_PING:
                handleDebugPing(event, user);
                break;
            default:
                break;
        }
    }

    private void handleDebugPing(PacketReceiveEvent event, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        WrapperPlayClientDebugPing wrapperPlayClientDebugPing = new WrapperPlayClientDebugPing(event);
        long ping = System.currentTimeMillis() - wrapperPlayClientDebugPing.getTimestamp();
        if (openedMenu instanceof OpenedMenu.Base base) {
            base.handlePing(ping);
        }
    }

    private void handleInteractEntity(PacketReceiveEvent event, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        WrapperPlayClientInteractEntity wrapperPlayClientInteractEntity = new WrapperPlayClientInteractEntity(event);
        if (wrapperPlayClientInteractEntity.getEntityId() != user.entityId()) {
            return;
        }
        event.setCancelled(true);
    }

    private void handleHeldItemChange(WrapperPlayClientHeldItemChange wrapperPlayClientHeldItemChange, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        if (openedMenu instanceof OpenedMenu.Base base) {
            base.handleScroll(wrapperPlayClientHeldItemChange.getSlot());
        }
    }

    private void handleClickWindow(WrapperPlayClientClickWindow packet, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        if (openedMenu instanceof OpenedMenu.Base base && base.inputHandler().active()) {
            InputHandler inputHandler = base.inputHandler();
            inputHandler.quit();
        }
    }

    private void handleItemName(WrapperPlayClientNameItem packet, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        if (openedMenu instanceof OpenedMenu.Base base && base.inputHandler().active()) {
            InputHandler inputHandler = base.inputHandler();
            inputHandler.textInput().handle(packet.getItemName());
        }
    }

    private void handlePlayerRotation(WrapperPlayClientPlayerRotation packet, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        if (openedMenu instanceof OpenedMenu.Base base) {
            base.handleRot(packet.getYaw(), packet.getPitch());
        }
    }

    private static OpenedMenu menu(SunscreenUser<?> user) {
        Session session = user.session();
        if (session == null) {
            return null;
        }
        return session.menu();
    }

    private void handleEntityAction(WrapperPlayClientEntityAction packet, SunscreenUser<?> user) {
        if (packet.getAction() != WrapperPlayClientEntityAction.Action.STOP_SNEAKING) {
            return;
        }
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        if (openedMenu instanceof OpenedMenu.Base base) {
            base.handleSneak();
        }
    }

    private void handleAnimation(WrapperPlayClientAnimation packet, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        if (openedMenu instanceof OpenedMenu.Base base) {
            base.handleClick();
        }
    }

    private void handleChatMessage(WrapperPlayClientChatMessage packet, SunscreenUser<?> user) {
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        Optional<SunscreenUser<Audience>> userOptional = SunscreenLibrary.library().users().user(event.getUser().getUUID());
        if (userOptional.isEmpty()) {
            return;
        }
        SunscreenUser<?> user = userOptional.get();
        if (user.session() == null) {
            return;
        }
        switch (event.getPacketType()) {
            case PacketType.Play.Server.SYSTEM_CHAT_MESSAGE:
                handleSystemChatMessageSend(event, user);
                break;
            case PacketType.Play.Server.BOSS_BAR:
                //handleBossBarSend(event, user);
                break;
            case PacketType.Play.Server.CHAT_MESSAGE:
                handleChatMessageSend(event, user);
                break;
            case PacketType.Play.Server.DAMAGE_EVENT:
                handleDamageEventSend(event, user);
            case PacketType.Play.Server.TIME_UPDATE:
                handleTimeUpdateSend(event, user);
            case PacketType.Play.Server.PLAYER_POSITION_AND_LOOK, PacketType.Play.Server.PLAYER_ROTATION:
                //event.setCancelled(true);
                break;
            case PacketType.Play.Server.OPEN_WINDOW:
                handleOpenWindowSend(event, user);
                break;
            default:
                break;
        }
    }

    private void handleSystemChatMessageSend(PacketSendEvent event, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        event.setCancelled(true);
    }

    private void handleOpenWindowSend(PacketSendEvent event, SunscreenUser<?> user) {
        WrapperPlayServerOpenWindow packet = new WrapperPlayServerOpenWindow(event);
        if (!inChestEmulator.contains(user.uniqueIdentifier())) {
            return;
        }
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }

    }

    private void handleTimeUpdateSend(PacketSendEvent event, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        if (!event.getPacketType().equals(PacketType.Play.Server.TIME_UPDATE)) {
            return;
        }
        WrapperPlayServerTimeUpdate packet = new WrapperPlayServerTimeUpdate(event);
        packet.setWorldAge(-1000);
    }

    private void handleBossBarSend(PacketSendEvent event, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        event.setCancelled(true);
    }

    private void handleChatMessageSend(PacketSendEvent event, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        event.setCancelled(true);
    }

    private void handleDamageEventSend(PacketSendEvent event, SunscreenUser<?> user) {
        OpenedMenu openedMenu = menu(user);
        if (openedMenu == null) {
            return;
        }
        WrapperPlayServerDamageEvent packet = new WrapperPlayServerDamageEvent(event);
        if (packet.getEntityId() != user.entityId()) {
            return;
        }
        if (openedMenu instanceof OpenedMenu.Base base) {
            base.handleDamage();
        }
    }

}
