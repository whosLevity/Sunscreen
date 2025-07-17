package me.combimagnetron.sunscreen.menu.cursor;

import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.player.Equipment;
import com.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetPassengers;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;
import me.combimagnetron.passport.internal.entity.impl.passive.horse.Color;
import me.combimagnetron.passport.internal.entity.impl.passive.horse.Horse;
import me.combimagnetron.passport.internal.entity.impl.passive.horse.Marking;
import me.combimagnetron.passport.internal.entity.impl.passive.horse.Variant;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.element.impl.ImageElement;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;
import me.combimagnetron.sunscreen.util.ViewportHelper;

import java.util.List;
import java.util.UUID;

public class ClientsideCursor implements Cursor {
    private static final WrapperPlayServerUpdateAttributes.PropertyModifier MODIFIER = new WrapperPlayServerUpdateAttributes.PropertyModifier(UUID.randomUUID(), 0, WrapperPlayServerUpdateAttributes.PropertyModifier.Operation.MULTIPLY_BASE);
    private static final Variant VARIANT = Variant.of(Color.BLACK, Marking.NONE);
    private final SunscreenUser<?> user;
    private final Horse horse;

    protected ClientsideCursor(SunscreenUser<?> user) {
        this.user = user;
        this.horse = Horse.horse(user.position());
        horse.variant(VARIANT);
    }

    @Override
    public void show(SunscreenUser<?> user) {
        EquipmentSlot equipmentSlot = EquipmentSlot.BODY;
        if (user.clientVersion().getProtocolVersion() >= ClientVersion.V_1_21_5.getProtocolVersion()) {
            equipmentSlot = EquipmentSlot.SADDLE;
        } else {
            horse.saddled(true);
        }
        WrapperPlayServerEntityEquipment entityEquipment = new WrapperPlayServerEntityEquipment(horse.id().intValue(), List.of(new Equipment(equipmentSlot, ItemStack.builder().type(ItemTypes.SADDLE).build())));
        WrapperPlayServerSetPassengers passengers = new WrapperPlayServerSetPassengers(horse.id().intValue(), new int[]{user.entityId()});
        List<WrapperPlayServerUpdateAttributes.Property> attributes = List.of(
                new WrapperPlayServerUpdateAttributes.Property(
                        Attributes.JUMP_STRENGTH,
                        0.0D,
                        List.of(MODIFIER)
                ),
                new WrapperPlayServerUpdateAttributes.Property(
                        Attributes.SCALE,
                        0.01,
                        List.of(MODIFIER)
                )
        );
        WrapperPlayServerUpdateAttributes updateAttributes = new WrapperPlayServerUpdateAttributes(horse.id().intValue(), attributes);
        user.show(horse);
        user.connection().send(updateAttributes);
        user.connection().send(entityEquipment);
        user.connection().send(passengers);
    }

    public Horse horse() {
        return horse;
    }

    @Override
    public void move(Vec2i vec2i) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void remove() {
        WrapperPlayServerDestroyEntities destroyEntities = new WrapperPlayServerDestroyEntities(horse.id().intValue());
        user.connection().send(destroyEntities);
    }

    @Override
    public Vec2d startPos(SunscreenUser<?> user) {
        Position position = Position.position().x().percentage(50).back().y().pixel(10).back().finish(user.screenSize().pixel(), Vec2i.of(0, 0));
        Vector3d vector3d = ViewportHelper.toTranslation(Vec2i.of(position.x().pixel(), position.y().pixel()), user.screenSize());
        return Vec2d.of(vector3d.x(), vector3d.y());
    }

}
