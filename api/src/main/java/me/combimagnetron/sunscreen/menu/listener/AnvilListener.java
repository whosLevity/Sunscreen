package me.combimagnetron.sunscreen.menu.listener;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.FontUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class AnvilListener implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.OPEN_WINDOW) {
            return;
        }
        WrapperPlayServerOpenWindow openWindow = new WrapperPlayServerOpenWindow(event);
        if (openWindow.getType() != 8) {
            return;
        }
        SunscreenUser<?> user = SunscreenLibrary.library().users().user(event.getUser().getUUID()).orElse(null);
        if (user == null) {
            return;
        }
        if (user.session().menu() != null) {
            return;
        }
        Component title = openWindow.getTitle();
        Component newTitle = Component.empty().append(FontUtil.offset(-60).font(Key.key("comet:offset"))).append(Component.text("a").font(Key.key("comet:anvil"))).append(FontUtil.offset(-118)).append(Component.text("b").font(Key.key("comet:anvil"))).color(NamedTextColor.WHITE).append(FontUtil.offset(-110));
        Component product = newTitle.append(title.color(NamedTextColor.DARK_GRAY)).font(Key.key("default"));
        openWindow.setTitle(product);
    }

}
