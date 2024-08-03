package net.kmidnight.midnightsfwmod.items;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MidnightsfwMod.MODID);

    public static final RegistryObject<Item> WOODEN_DAGGER = ITEMS.register("wooden_dagger",
        () -> new Item(new Item.Properties().stacksTo(1).durability(500)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
