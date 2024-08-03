package net.kmidnight.midnightsfwmod.items;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(MidnightsfwMod.MODID)
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MidnightsfwMod.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
        .alwaysEat().nutrition(1).saturationMod(2f).build())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
