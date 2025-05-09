package me.combimagnetron.sunscreen.user;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import me.combimagnetron.passport.internal.network.Connection;
import net.minestom.server.entity.Player;
import net.minestom.server.network.ConnectionState;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.LazyPacket;
import net.minestom.server.network.packet.server.ServerPacket;
import org.jetbrains.annotations.NotNull;

public class MinestomConnectionImpl implements Connection {
    private final Player player;

    MinestomConnectionImpl(Player player) {
        this.player = player;
    }

    @Override
    public void send(PacketWrapper<?> packetWrapper) {
        player.getPlayerConnection();
        ServerPacket packet = new ServerPacket() {
            @Override
            public int getId(@NotNull ConnectionState connectionState) {
                return packetWrapper.getNativePacketId();
            }

            @Override
            public void write(@NotNull NetworkBuffer networkBuffer) {
                networkBuffer.write(NetworkBuffer.RAW_BYTES, packetWrapper.readByteArray());
            }
        };
        LazyPacket lazyPacket = new LazyPacket(() -> packet);
        player.getPlayerConnection().sendPacket(lazyPacket);
    }
}
