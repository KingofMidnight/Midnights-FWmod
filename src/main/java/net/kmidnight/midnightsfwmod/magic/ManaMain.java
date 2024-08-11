package net.kmidnight.midnightsfwmod.magic;

import net.kmidnight.midnightsfwmod.attributes.AttributeHelper;
import net.kmidnight.midnightsfwmod.attributes.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ManaMain {
    @SubscribeEvent
    public static void manaChange(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        AttributeInstance maxManaInstance = player.getAttribute(ModAttributes.MAX_MANA.get());
        AttributeInstance manaInstance = player.getAttribute(ModAttributes.MANA.get());
    if (maxManaInstance == null || manaInstance == null) {
        return;
        }
    double maxMana = maxManaInstance.getValue();
    double mana = manaInstance.getBaseValue();
    double intel = player.getAttributeValue(ModAttributes.MAX_MANA.get());
    double curManaRegen = player.getAttributeValue(ModAttributes.MANA_REGEN.get());
    double manaRegen = maxMana / 500 * (1 + curManaRegen / 100);
    CompoundTag tag = player.getPersistentData();
    tag.putDouble("manaRegen", manaRegen);

        if (mana > maxMana) {
            mana = maxMana;
        }
        if (mana > maxMana) {
            mana += manaRegen;
        }
        maxMana = intel;

        manaInstance.setBaseValue(mana);
        maxManaInstance.setBaseValue(maxMana);
    }
    public static boolean consumeMana(LivingEntity living, double manaToConsume) {
    if (manaToConsume <= 0) return true;
    double mana = getMana(living);
    if (manaToConsume > 0) {
        mana -= manaToConsume;
    }
    setMana(living,mana);
    return true;
    }

    public static double getMana(LivingEntity living) {
        return AttributeHelper.getSaveAttributeValue(ModAttributes.MANA.get(), living);
    }
    public static void setMana(LivingEntity living, double mana) {
        AttributeInstance instance = living.getAttribute(ModAttributes.MANA.get());
            if (instance != null) {
                instance.setBaseValue(mana);
            }
    }
}
