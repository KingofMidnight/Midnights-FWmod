package net.kmidnight.midnightsfwmod.attributes;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = MidnightsfwMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeAdder {
    @SubscribeEvent
    public static void modifyAttributes(EntityAttributeModificationEvent event) {
        addtoPlayer(event, ModAttributes.MANA);
        addtoPlayer(event, ModAttributes.MAX_MANA);
        addtoPlayer(event, ModAttributes.MANA_REGEN);
    }

    private static void addtoPlayer(EntityAttributeModificationEvent event, Supplier<Attribute>... attributes) {
        for (Supplier<Attribute> attribute : attributes) {
            event.add(EntityType.PLAYER, attribute.get());
        }
    }
}
