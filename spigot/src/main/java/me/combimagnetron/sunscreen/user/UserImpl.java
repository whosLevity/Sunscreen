package me.combimagnetron.sunscreen.user;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTimeUpdate;
import me.combimagnetron.passport.PacketEventsConnectionImpl;
import me.combimagnetron.passport.config.Config;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.network.Connection;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.menu.AspectRatioMenu;
import me.combimagnetron.sunscreen.menu.MenuTemplate;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.menu.ScreenSize;
import me.combimagnetron.sunscreen.menu.builtin.setup.UserSetupMenu;
import me.combimagnetron.sunscreen.menu.timing.MenuTicker;
import me.combimagnetron.sunscreen.session.Session;
import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserImpl implements SunscreenUser<Player> {
    private final Player player;
    private final Connection connection;
    private ScreenSize screenSize = ScreenSize.of(Vec2i.of(200, 200), Pair.of(Vec2d.of(-0.11083211535, -0.11083211535), Vec2d.of(0.11083211535, 0.11083211535)));
    private float fov = 70;

    public static UserImpl of(Player player) {
        return new UserImpl(player);
    }

    private UserImpl(Player player) {
        this.player = player;
        this.connection = new PacketEventsConnectionImpl<>(player);
        Node<String> node = Config.file(SunscreenLibrary.library().path().resolve("data.dt")).reader().node(uniqueIdentifier().toString());
        if (node == null) {
            AspectRatioMenu menu = new AspectRatioMenu(this);
            SunscreenLibrary.library().menuTicker().start(menu);
            SunscreenLibrary.library().sessionHandler().session(Session.session(menu, this));
            return;
        }
        if (node.value() == null) {
            SunscreenLibrary.library().menuTicker().start(new UserSetupMenu(this));
            return;
        }
        this.screenSize = ScreenSize.fromString(node.value());
    }

    @Override
    public Player platformSpecificPlayer() {
        return player;
    }

    @Override
    public String name() {
        return player.getName();
    }

    @Override
    public UUID uniqueIdentifier() {
        return player.getUniqueId();
    }

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public Vector3d position() {
        return Vector3d.vec3(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
    }

    @Override
    public void show(Entity entity) {
        WrapperPlayServerSpawnEntity clientSpawnEntity = new WrapperPlayServerSpawnEntity(entity.id().intValue(), Optional.of(entity.uuid()), EntityTypes.getById(ClientVersion.V_1_21_4, entity.type().id()), new com.github.retrooper.packetevents.util.Vector3d(entity.position().x(), entity.position().y(), entity.position().z()), (float) entity.rotation().x(), (float) entity.rotation().y(), (float) entity.rotation().z(), entity.data().i(), Optional.empty());
        List<EntityData> entityData = entity.type().metadata().entityData();
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
        return Vector3d.vec3(player.getLocation().getYaw(), player.getLocation().getPitch(), 0);
    }

    @Override
    public int gameMode() {
        return player.getGameMode().getValue();
    }

    @Override
    public ScreenSize screenSize() {
        return screenSize;
    }

    @Override
    public void screenSize(ScreenSize pos2D) {
        this.screenSize = pos2D;
    }

    @Override
    public float fov() {
        return fov;
    }

    @Override
    public void fov(float fov) {
        this.fov = fov;
        if (fov == -1) {
            connection().send(new WrapperPlayServerTimeUpdate(0, player.getWorld().getTime()));
        } else {
            connection().send(new WrapperPlayServerTimeUpdate((long) Math.floor((fov + 0.5F) / 180.0F * 24000.0F), player.getWorld().getTime()));
        }
    }

    @Override
    public boolean permission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public Session session() {
        return SunscreenLibrary.library().sessionHandler().session(this) == null ? new Session.Impl(null, this) : SunscreenLibrary.library().sessionHandler().session(this);
    }

    @Override
    public Session open(MenuTemplate template) {
        OpenedMenu.FloatImpl menu = new OpenedMenu.Float(this, template);
        SunscreenLibrary.library().menuTicker().start(menu);
        menu.open(this);
        Session session = Session.session(menu, this);
        return SunscreenLibrary.library().sessionHandler().session(session);
    }

    @Override
    public float worldTime() {
        return player.getWorld().getTime();
    }

    @Override
    public void resendInv() {
        player.updateInventory();
    }

}
