package me.combimagnetron.sunscreen.user;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import me.combimagnetron.passport.PacketEventsConnectionImpl;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.network.Connection;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.menu.MenuTemplate;
import me.combimagnetron.sunscreen.menu.ScreenSize;
import me.combimagnetron.sunscreen.session.Session;
import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;
import net.minestom.server.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserImpl implements SunscreenUser<Player> {
    private final static ClientVersion CLIENT_VERSION = ClientVersion.V_1_21_4;
    private final Player player;
    private final Connection connection;
    private ScreenSize screenSize = ScreenSize.of(Vec2i.of(200, 200), Pair.of(Vec2d.of(-0.11083211535, -0.11083211535), Vec2d.of(0.11083211535, 0.11083211535)));

    public static UserImpl of(Player player) {
        return new UserImpl(player);
    }

    private UserImpl(Player player) {
        this.player = player;
        this.connection = new MinestomConnectionImpl(player);
    }

    @Override
    public ScreenSize screenSize() {
        return screenSize;
    }

    @Override
    public void screenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    @Override
    public float fov() {
        return 0;
    }

    @Override
    public void fov(float fov) {

    }

    @Override
    public boolean permission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public Session session() {
        return SunscreenLibrary.library().sessionHandler().session(this);
    }

    @Override
    public Session open(MenuTemplate template) {
        return null;
    }

    @Override
    public Player platformSpecificPlayer() {
        return player;
    }

    @Override
    public String name() {
        return player.getUsername();
    }

    @Override
    public UUID uniqueIdentifier() {
        return player.getUuid();
    }

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public Vector3d position() {
        return Vector3d.vec3(player.getPosition().x(), player.getPosition().y(), player.getPosition().z());
    }

    @Override
    public void show(Entity entity) {
        WrapperPlayServerSpawnEntity clientSpawnEntity = new WrapperPlayServerSpawnEntity(entity.id().intValue(), Optional.of(entity.uuid()), EntityTypes.getById(ClientVersion.V_1_21_4, entity.type().id()), new com.github.retrooper.packetevents.util.Vector3d(entity.position().x(), entity.position().y(), entity.position().z()), (float) entity.rotation().x(), (float) entity.rotation().y(), (float) entity.rotation().z(), entity.data().i(), Optional.empty());
        List<EntityData<?>> entityData = entity.type().metadata().entityData();
        WrapperPlayServerEntityMetadata clientEntityMetadata = new WrapperPlayServerEntityMetadata(entity.id().intValue(), entityData);
        connection().send(clientSpawnEntity);
        connection().send(clientEntityMetadata);
    }

    @Override
    public int entityId() {
        return player.getEntityId();
    }

    @Override
    public Vector3d rotation() {
        return Vector3d.vec3(player.getPosition().yaw(), player.getPosition().pitch(), 0);
    }

    @Override
    public int gameMode() {
        return player.getGameMode().id();
    }

    @Override
    public float worldTime() {
        return player.getInstance().getTime();
    }

    @Override
    public void resendInv() {
        player.getInventory().update();
    }

    @Override
    public ClientVersion clientVersion() {
        return null;
    }
}
