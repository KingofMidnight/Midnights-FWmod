package net.kmidnight.midnightsfwmod.attributes;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
        DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MidnightsfwMod.MODID);

    public static final RegistryObject<Attribute> MANA = ATTRIBUTES.register("mana",
        () -> new RangedAttribute("attributes.mfw.mana",1.0,-50000.0,50000.0));
    public static final RegistryObject<Attribute> MAX_MANA = ATTRIBUTES.register("max_mana",
        () -> new RangedAttribute("attributes.mfw.max_mana",1.0,-50000.0,50000.0));
    public static final RegistryObject<Attribute> MANA_REGEN = ATTRIBUTES.register("mana_regen",
        () -> new RangedAttribute("attributes.mfw.mana_regen",1.0,-500.0,500.0));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
