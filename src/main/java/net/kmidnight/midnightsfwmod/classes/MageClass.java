package net.kmidnight.midnightsfwmod.classes;

import net.kmidnight.midnightsfwmod.classes.api.FWClass;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class MageClass implements FWClass {
    @Override
    public List<String> ClassLevelNames() {
        return List.of(
            "Medium",
            "Seer",
            "Conjurer",
            "Magician",
            "Warlock",
            "Sorcerer",
            "Arch-Magician"
        );
    }

    @Override
    public List<Integer> manaLevels() {
        return List.of();
    }

    @Override
    public List<Integer> manaRegen() {
        return List.of();
    }

    @Override
    public List<Double> maxHealth() {
        return List.of();
    }

    @Override
    public void tick(Player player, int level) {

    }

    @Override
    public ChatFormatting getColorFormatting() {
        return null;
    }
}
