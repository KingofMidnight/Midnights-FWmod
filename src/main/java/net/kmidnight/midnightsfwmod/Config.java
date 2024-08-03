package net.kmidnight.midnightsfwmod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = MidnightsfwMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static int magicNumber;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
        .comment("A magic number")
        .defineInRange("magicNumber", 1, 0, Integer.MAX_VALUE);

    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        magicNumber = MAGIC_NUMBER.get();
    }
}
