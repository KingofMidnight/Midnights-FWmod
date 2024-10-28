package net.kmidnight.midnightsfwmod.magic;

import net.kmidnight.midnightsfwmod.classes.ClassesRegistery;
import net.kmidnight.midnightsfwmod.classes.api.FWClass;
import net.kmidnight.midnightsfwmod.networking.MFWNetworkHandler;
import net.kmidnight.midnightsfwmod.util.CapabilitySyncer.core.PlayerCapability;
import net.kmidnight.midnightsfwmod.util.CapabilitySyncer.network.EntityCapabilityStatusPacket;
import net.kmidnight.midnightsfwmod.util.CapabilitySyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.SimpleChannel;
import org.jetbrains.annotations.Nullable;

public class ManaData extends PlayerCapability {
    public static final int LEVEL_MIN = 0;
    public static final int LEVEL_MAX = 100;
    private final RandomSource random;
    @Nullable private FWClass currentClass = null;
    private int currentLevel = -1;
    private double mana = 100;
    private double maxMana = 100;
    private double manaRegen = 1;

    protected ManaData(Player entity) {
        super(entity);
        this.random = RandomSource.create();
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.level().isClientSide && event.phase == TickEvent.Phase.END) {
            ManaData player = ManaAttacher.getHolderUnwrap(event.player);
            player.regenMana(event.player);
            if (player.getCurrentClass() != null) {
                player.getCurrentClass().tick(event.player, player.getCurrentLevel());
            }
        }
    }

    public void removeClass() {
        this.currentClass = null;
        this.currentLevel = -1;
        this.mana = 100;
        this.maxMana = 100;
        this.manaRegen = 1;
        updateTracking();
    }

    public void setClassAndLevel(FWClass newClass, int level) {
        this.currentClass = newClass;
        this.currentLevel = level;
        this.maxMana = this.currentClass.manaLevels().get(this.currentLevel);
        this.mana = this.maxMana;
        this.manaRegen = this.currentClass.manaRegen().get(this.currentLevel);
        this.player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.currentClass.maxHealth().get(level));
        this.player.setHealth(this.player.getMaxHealth());
        updateTracking();
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        updateTracking();
    }

    public double getManaRegen() {
        return this.manaRegen;
    }

    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
        updateTracking();
    }

    public double getMana() {
        return this.mana;
    }

    public void setMana(double mana) {
        this.mana = Mth.clamp(mana, 0, this.maxMana);
        updateTracking();
    }

    public @Nullable FWClass getCurrentClass() {
        return this.currentClass;
    }

    public void removeCurrentClass() {
        this.currentClass = null;
        updateTracking();
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        this.maxMana = this.currentClass.manaLevels().get(currentLevel);
        this.manaRegen = this.currentClass.manaRegen().get(currentLevel);
        this.mana = this.maxMana;
        updateTracking();
    }

    public void incrementLevel() {
        if (this.currentLevel > LEVEL_MIN) {
            this.currentLevel--;
            this.maxMana = this.currentClass.manaLevels().get(this.currentLevel);
            this.mana = this.maxMana;
            this.manaRegen = this.currentClass.manaRegen().get(this.currentLevel);
            updateTracking();
        }
    }

    public void decrementLevel() {
        if (this.currentLevel < LEVEL_MAX) {
            this.currentLevel++;
            this.maxMana = this.currentClass.manaLevels().get(this.currentLevel);
            this.mana = this.maxMana;
            this.manaRegen = this.currentClass.manaRegen().get(this.currentLevel);
            updateTracking();
        }
    }

    public boolean useMana(int amount) {
        if (this.player.isCreative()) {
            return true;
        }
        if (this.mana - amount < 0) {
            return false;
        }
        this.mana = Mth.clamp(this.mana - amount, 0, this.maxMana);
        updateTracking();
        return true;
    }

    public void increaseMana(int amount) {
        this.mana = Mth.clamp(this.mana + amount, 0, this.maxMana);
        updateTracking();
    }

    public void regenMana(Entity entity) {
        if (entity instanceof Player && this.mana < this.maxMana) {
            double increase = (Mth.nextDouble(this.random, 0.1, 1.0) * (this.manaRegen * 1.5f)) / 5;
            this.mana = Mth.clamp(this.mana + increase, 0, this.maxMana);
            updateTracking();
        }
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("currentLevel", this.currentLevel);
        tag.putString("currentClass", this.currentClass == null ? "" : ClassesRegistery.getRegistry().getKey(this.currentClass).toString());
        tag.putDouble("mana", this.mana);
        tag.putDouble("maxMana", this.maxMana);
        tag.putDouble("manaRegen", this.manaRegen);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        this.currentLevel = nbt.getInt("currentLevel");
        String className = nbt.getString("currentClass");
        if (!className.isEmpty()) {
            this.currentClass = ClassesRegistery.getRegistry().getValue(new ResourceLocation(className));
        }
        this.mana = nbt.getDouble("mana");
        this.maxMana = nbt.getDouble("maxMana");
        this.manaRegen = nbt.getDouble("manaRegen");
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), ManaAttacher.RESOURCE_LOCATION, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return MFWNetworkHandler.INSTANCE;
    }
}