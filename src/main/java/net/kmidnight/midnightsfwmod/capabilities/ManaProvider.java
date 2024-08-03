package net.kmidnight.midnightsfwmod.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber
public class ManaProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<IMana> MANA_CAP = CapabilityManager.get(new CapabilityToken<IMana>() {});

    public final IMana instance = new Mana();
    private final LazyOptional<IMana> optional = LazyOptional.of(() -> instance);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction dir) {
        return cap == MANA_CAP ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("mana", instance.getMana());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.set(nbt.getFloat("mana"));
    }
}