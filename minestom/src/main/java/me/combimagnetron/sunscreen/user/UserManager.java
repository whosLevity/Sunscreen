package me.combimagnetron.sunscreen.user;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.user.UserHandler;
import me.combimagnetron.sunscreen.session.Session;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.trait.PlayerEvent;

import java.util.*;

public class UserManager implements UserHandler<Player, SunscreenUser<Player>> {
    private final Map<UUID, SunscreenUser<Player>> userMap = new WeakHashMap<>();
    private final EventNode<PlayerEvent> eventNode = EventNode.type("player-spawn-sunscreen", EventFilter.PLAYER);

    public UserManager() {
        eventNode.addListener(PlayerSpawnEvent.class, event -> {
            final Player player = event.getPlayer();
            userMap.put(player.getUuid(), UserImpl.of(player));
        });
        eventNode.addListener(PlayerDisconnectEvent.class, event -> {
            final Player player = event.getPlayer();
            SunscreenUser<Player> user = userMap.get(player.getUuid());
            Session session = user.session();
            if (session != null) {
                session.close();
            }
            userMap.remove(player.getUuid());
        });
    }

    @Override
    public SunscreenUser<Player> user(Player player) {
        return null;
    }

    @Override
    public Optional<SunscreenUser<Player>> user(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<SunscreenUser<Player>> user(String s) {
        return Optional.empty();
    }

    @Override
    public Collection<SunscreenUser<Player>> users() {
        return List.of();
    }

    @Override
    public Collection<SunscreenUser<Player>> global() {
        return List.of();
    }

    @Override
    public SunscreenUser<Player> deserialize(ByteBuffer byteBuffer) {
        return null;
    }
}
