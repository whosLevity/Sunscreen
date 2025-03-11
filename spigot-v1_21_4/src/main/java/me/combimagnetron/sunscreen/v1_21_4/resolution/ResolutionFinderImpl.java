package me.combimagnetron.sunscreen.v1_21_4.resolution;

import me.combimagnetron.passport.concurrency.Scheduler;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Duration;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.menu.ResolutionFinder;
import me.combimagnetron.sunscreen.menu.ScreenSize;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftCreaking;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class ResolutionFinderImpl implements ResolutionFinder {
    @Override
    public ScreenSize find(SunscreenUser<?> user) {
        Player player = (Player) user.platformSpecificPlayer();
        creaking(player);
        return null;
    }

    private void creaking(Player player) {
        org.bukkit.entity.Creaking bukkitCreaking = player.getWorld().spawn(player.getLocation(), org.bukkit.entity.Creaking.class);
        Creaking creaking = ((CraftCreaking) bukkitCreaking).getHandle();
        Bukkit.getScheduler().runTaskTimer((JavaPlugin) SunscreenLibrary.library().plugin(), (task) -> {
            player.sendMessage(Component.text(creaking.canMove()));
            creaking.move(MoverType.SELF, new Vec3(0.01, 0, 0));
            if (!creaking.canMove()) {
                task.cancel();
            } else {
                creaking.setIsActive(false);
            }
        }, 0, 1);
    }

}
