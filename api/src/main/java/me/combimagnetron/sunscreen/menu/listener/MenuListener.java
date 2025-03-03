package me.combimagnetron.sunscreen.menu.listener;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientAnimation;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChatMessage;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerRotation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDamageEvent;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.session.Session;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public class MenuListener implements PacketListener {

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        SunscreenUser<?> user;
        try {
            user = SunscreenLibrary.library().users().user(event.getUser().getUUID()).orElse(null);
        } catch (NullPointerException e) {
            return;
        }
        if (user == null) {
            return;
        }
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
            default:
                break;
        }
    }

    private void handlePlayerRotation(WrapperPlayClientPlayerRotation packet, SunscreenUser<?> user) {
        Menu menu = menu(user);
        if (menu == null) {
            return;
        }
        if (menu instanceof Menu.Base base) {
            base.handleRot(packet.getYaw(), packet.getPitch());
        }
    }

    private static Menu menu(SunscreenUser<?> user) {
        Session session = user.session();
        if (session == null) {
            return null;
        }
        Menu menu = session.menu();
        return menu;
    }

    private void handleEntityAction(WrapperPlayClientEntityAction packet, SunscreenUser<?> user) {
        if (packet.getAction() != WrapperPlayClientEntityAction.Action.STOP_SNEAKING) {
            return;
        }
        Menu menu = menu(user);
        if (menu == null) {
            return;
        }
        if (menu instanceof Menu.Base base) {
            base.handleSneak();
        }
    }

    private void handleAnimation(WrapperPlayClientAnimation packet, SunscreenUser<?> user) {
    }

    private void handleChatMessage(WrapperPlayClientChatMessage packet, SunscreenUser<?> user) {
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        SunscreenUser<?> user = SunscreenLibrary.library().users().user(event.getUser().getUUID()).get();
        if (user.session() == null) {
            return;
        }
        switch (event.getPacketType()) {
            case PacketType.Play.Server.SYSTEM_CHAT_MESSAGE:
                handleSystemChatMessageSend(event, user);
                break;
            case PacketType.Play.Server.BOSS_BAR:
                handleBossBarSend(event, user);
                break;
            case PacketType.Play.Server.CHAT_MESSAGE:
                handleChatMessageSend(event, user);
                break;
            case PacketType.Play.Server.DAMAGE_EVENT:
                handleDamageEventSend(event, user);
            default:
                break;
        }
    }

    private void handleSystemChatMessageSend(PacketSendEvent event, SunscreenUser<?> user) {
        Menu menu = menu(user);
        if (menu == null) {
            return;
        }
        event.setCancelled(true);
    }

    private void handleBossBarSend(PacketSendEvent event, SunscreenUser<?> user) {
        Menu menu = menu(user);
        if (menu == null) {
            return;
        }
        event.setCancelled(true);
    }

    private void handleChatMessageSend(PacketSendEvent event, SunscreenUser<?> user) {
        Menu menu = menu(user);
        if (menu == null) {
            return;
        }
        event.setCancelled(true);
    }

    private void handleDamageEventSend(PacketSendEvent event, SunscreenUser<?> user) {
        Menu menu = menu(user);
        if (menu == null) {
            return;
        }
        WrapperPlayServerDamageEvent packet = new WrapperPlayServerDamageEvent(event);
        if (packet.getEntityId() != user.entityId()) {
            return;
        }
        if (menu instanceof Menu.Base base) {
            base.handleDamage();
        }
    }

}
