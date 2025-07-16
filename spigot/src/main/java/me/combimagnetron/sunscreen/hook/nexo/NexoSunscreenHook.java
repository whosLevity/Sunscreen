package me.combimagnetron.sunscreen.hook.nexo;

import com.nexomc.nexo.NexoPlugin;
import com.nexomc.nexo.api.NexoPack;
import com.nexomc.nexo.fonts.FontManager;
import com.nexomc.nexo.fonts.Glyph;
import me.combimagnetron.sunscreen.hook.ResourcePackProviderHook;
import me.combimagnetron.sunscreen.hook.SunscreenHook;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.BuiltResourcePack;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.BitMapFontProvider;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.metadata.overlays.OverlayEntry;
import team.unnamed.creative.metadata.overlays.OverlaysMeta;
import team.unnamed.creative.overlay.Overlay;
import team.unnamed.creative.texture.Texture;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NexoSunscreenHook implements ResourcePackProviderHook {
    @Override
    public boolean canRun() {
        return Bukkit.getPluginManager().isPluginEnabled("Nexo");
    }

    @Override
    public void enable() {

    }

    public @Nullable Canvas font(char ch) {
        String character = String.valueOf(ch);
        FontManager manager = NexoPlugin.instance().fontManager();
        Glyph glyph = manager.glyphs().stream().filter(g -> g.getUnicodes().contains(character)).findAny().orElseThrow();
        ResourcePack pack = NexoPlugin.instance().packGenerator().resourcePack();
        Font font = pack.font(glyph.getFont());
        if (font == null) {
            return null;
        }
        List<BitMapFontProvider> providers = font.providers().stream().filter(fontProvider -> fontProvider instanceof BitMapFontProvider).map(fontProvider -> (BitMapFontProvider) fontProvider).toList();
        for (BitMapFontProvider provider : providers) {
            if (!provider.characters().contains(character)) {
                continue;
            }
            Key file = provider.file();
            Texture texture = pack.texture(file);
            Canvas canvas;
            try {
                 canvas = Canvas.image(ImageIO.read(new ByteArrayInputStream(texture.data().toByteArray())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return canvas;
        }
        return null;
    }

    public boolean merge(me.combimagnetron.sunscreen.resourcepack.ResourcePack resourcePack) {
        ResourcePack pack = NexoPack.resourcePack();
        OverlaysMeta overlaysMeta = pack.overlaysMeta();
        if (overlaysMeta == null) {
            return false;
        }
        for (OverlayEntry entry : overlaysMeta.entries()) {
            Overlay overlay = pack.overlay(entry.directory());
            if (overlay == null) {
                continue;
            }
            Map<String, Writable> files = overlay.unknownFiles();
            files.forEach((path, writable) -> {
                String shader;
                try {
                    shader = writable.toUTF8String();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        }
        return false;
    }

    @Override
    public void disable() {

    }

    @Override
    public void onMenuEnter(@NotNull SunscreenUser<?> user, @Nullable OpenedMenu menu) {

    }

    @Override
    public void onMenuLeave(@NotNull SunscreenUser<?> user, @Nullable OpenedMenu menu) {

    }
}
