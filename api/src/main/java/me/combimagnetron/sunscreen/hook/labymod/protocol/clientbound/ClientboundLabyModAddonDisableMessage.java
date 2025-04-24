package me.combimagnetron.sunscreen.hook.labymod.protocol.clientbound;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.hook.labymod.protocol.LabyModMessage;

import java.util.List;

public class ClientboundLabyModAddonDisableMessage implements LabyModMessage {
    private List<String> addonIds;
    private Action action;

    protected ClientboundLabyModAddonDisableMessage(List<String> addonIds, Action action) {
        this.addonIds = addonIds;
        this.action = action;
    }

    protected ClientboundLabyModAddonDisableMessage(ByteBuffer byteBuffer) {
        read(byteBuffer);
    }

    public static ClientboundLabyModAddonDisableMessage of(List<String> addonIds, Action action) {
        return new ClientboundLabyModAddonDisableMessage(addonIds, action);
    }

    public List<String> addonIds() {
        return addonIds;
    }

    public Action action() {
        return action;
    }

    @Override
    public void read(ByteBuffer reader) {
        this.addonIds = (List<String>) reader.readCollection(buffer -> buffer.read(ByteBuffer.Adapter.STRING));
        this.action = reader.read(ByteBuffer.Adapter.BOOLEAN) ? Action.DISABLE : Action.REVERT;
    }

    @Override
    public void write(ByteBuffer writer) {
        writer.writeCollection(ByteBuffer.Adapter.STRING, addonIds);
        writer.write(ByteBuffer.Adapter.BOOLEAN, action == Action.DISABLE);
    }

    public enum Action {
        DISABLE,
        REVERT
    }

}
