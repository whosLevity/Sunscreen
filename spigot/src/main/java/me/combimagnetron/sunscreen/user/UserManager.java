package me.combimagnetron.sunscreen.user;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.user.UserHandler;
import me.combimagnetron.sunscreen.SunscreenPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.EulerAngle;

import java.util.*;

public class UserManager implements Listener, UserHandler<Player, SunscreenUser<Player>> {
    private final Map<UUID, SunscreenUser<Player>> userMap = new WeakHashMap<>();

    public UserManager(SunscreenPlugin library) {
        Bukkit.getServer().getPluginManager().registerEvents(this, library);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        userMap.put(player.getUniqueId(), UserImpl.of(player));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        userMap.remove(player.getUniqueId());
    }

    public SunscreenUser<Player> user(Player player) {
        return userMap.get(player.getUniqueId());
    }

    @Override
    public Optional<SunscreenUser<Player>> user(UUID uuid) {
        return Optional.of(userMap.get(uuid));
    }

    @Override
    public Optional<SunscreenUser<Player>> user(String s) {
        return Optional.of(userMap.get(Bukkit.getPlayer(s).getUniqueId()));
    }

    @Override
    public Collection<SunscreenUser<Player>> users() {
        return userMap.values();
    }

    @Override
    public Collection<SunscreenUser<Player>> global() {
        return users();
    }

    @Override
    public SunscreenUser<Player> deserialize(ByteBuffer byteBuffer) {
        return null;
    }

}
