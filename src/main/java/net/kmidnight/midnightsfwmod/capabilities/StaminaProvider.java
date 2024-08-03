package net.kmidnight.midnightsfwmod.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StaminaProvider  implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<IStamina> STAMINA_CAP = CapabilityManager.get(new CapabilityToken<IStamina>() {});

    public final IStamina instance = new Stamina();
    private final LazyOptional<IStamina> optional = LazyOptional.of(() -> instance);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction dir) {
        return cap == STAMINA_CAP ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("stamina", instance.getStamina());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.set(nbt.getFloat("stamina"));
    }
}
