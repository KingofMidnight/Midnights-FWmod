package net.kmidnight.midnightsfwmod.classes;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.kmidnight.midnightsfwmod.classes.api.FWClass;

import java.util.function.Supplier;

public class ClassesRegistery {
    public static final DeferredRegister<FWClass> FWCLASSES = DeferredRegister.create(new ResourceLocation(MidnightsfwMod.MODID, "fwclass"), MidnightsfwMod.MODID);
    private static final Supplier<IForgeRegistry<FWClass>> SUPPLIER = FWCLASSES.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<MageClass> MAGE = FWCLASSES.register("mage", MageClass::new);

    public static IForgeRegistry<FWClass> getRegistry() {
        return SUPPLIER.get();
    }
}
