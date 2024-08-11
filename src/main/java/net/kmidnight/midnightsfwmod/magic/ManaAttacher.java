package net.kmidnight.midnightsfwmod.magic;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.kmidnight.midnightsfwmod.util.CapabilitySyncer.core.CapabilityAttacher;
import net.kmidnight.midnightsfwmod.util.CapabilitySyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;

public class ManaAttacher extends CapabilityAttacher {
    public static final Capability<ManaData> CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(MidnightsfwMod.MODID, "mana");
    private static final Class<ManaData> CAPABILITY_CLASS = ManaData.class;

    @NotNull
    public static ManaData getHolderUnwrap(Player player) {
        return getHolder(player).resolve().orElseThrow();
    }

    public static LazyOptional<ManaData> getHolder(Player player) {
        return player.getCapability(CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Player entity) {
        genericAttachCapability(event, new ManaData(entity), CAPABILITY, RESOURCE_LOCATION);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerPlayerAttacher(ManaAttacher::attach, ManaAttacher::getHolder, true);
        SimpleEntityCapabilityStatusPacket.registerRetriever(RESOURCE_LOCATION, ManaAttacher::getHolderUnwrap);
    }
}
