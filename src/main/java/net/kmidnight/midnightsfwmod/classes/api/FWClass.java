package net.kmidnight.midnightsfwmod.classes.api;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface FWClass {
    List<String> ClassLevelNames();

    List<Integer> manaLevels();

    List<Integer> manaRegen();

    List<Double> maxHealth();

    void tick(Player player, int level);


    ChatFormatting getColorFormatting();
}
