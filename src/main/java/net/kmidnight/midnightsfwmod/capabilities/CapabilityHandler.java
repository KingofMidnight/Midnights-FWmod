package net.kmidnight.midnightsfwmod.capabilities;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityHandler {
    public static final ResourceLocation MANA_CAP = new ResourceLocation(MidnightsfwMod.MODID, "mana");
    public static final ResourceLocation STAMINA_CAP = new ResourceLocation(MidnightsfwMod.MODID, "stamina");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(MANA_CAP, new ManaProvider());
        event.addCapability(STAMINA_CAP, new StaminaProvider());
    }
}
