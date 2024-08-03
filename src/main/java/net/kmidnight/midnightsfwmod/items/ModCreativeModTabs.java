package net.kmidnight.midnightsfwmod.items;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MidnightsfwMod.MODID);

    public static final RegistryObject<CreativeModeTab> KMIDNIGHT_TAB = CREATIVE_MODE_TABS.register("kmidnight_tab", () -> CreativeModeTab.builder()
        .withTabsBefore(CreativeModeTabs.COMBAT)
        .icon(() -> ModItems.WOODEN_DAGGER.get().getDefaultInstance())
        .displayItems((parameters, output) -> {
            output.accept(ModItems.WOODEN_DAGGER.get());
        }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
