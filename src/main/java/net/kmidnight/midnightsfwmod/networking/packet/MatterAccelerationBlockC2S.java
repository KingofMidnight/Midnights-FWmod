package net.kmidnight.midnightsfwmod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;


import java.util.Comparator;
import java.util.function.Supplier;

public class MatterAccelerationBlockC2S {
    public MatterAccelerationBlockC2S() {

    }

    public MatterAccelerationBlockC2S(FriendlyByteBuf buf) {

    }

    public void toByte(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {
           int x = player.getPersistentData().getInt("matterAccelerationBlockTimer");
           if (x >= 1) {
               Vec3 lookDirection = player.getLookAngle().normalize().scale(20);
               if (player.level().dimension() == Level.OVERWORLD) {
                   StoneEntity stoneEntity = player.level().getEntitiesOfClass(StoneEntity.class, player.getBoundingBox().inflate(10))
                           .stream()
                           .min(Comparator.comparingDouble(zombie -> zombie.distanceTo(player)))
                           .orElse(null);
                   if (stoneEntity != null) {
                       stoneEntity.setDeltaMovement(lookDirection);
                       stoneEntity.setSent(true);
                       stoneEntity.setShouldntDamage(false);
                       stoneEntity.setTickCount(440);
                   }
                   if (stoneEntity == null) {
                       player.getPersistentData().putInt("matterAccelerationBlockTimer", 0);
                   }
               }
               if (player.level().dimension() == Level.NETHER) {
                   NetherrackEntity netherrackEntity = player.level().getEntitiesOfClass(NetherrackEntity.class, player.getBoundingBox().inflate(10))
                           .stream()
                           .min(Comparator.comparingDouble(zombie -> zombie.distanceTo(player)))
                           .orElse(null);
                   if (netherrackEntity != null) {
                       netherrackEntity.setDeltaMovement(lookDirection);
                       netherrackEntity.setSent(true);
                       netherrackEntity.setShouldDamage(true);
                       netherrackEntity.setTickCount(440);
                   }
                   if (netherrackEntity == null) {
                       player.getPersistentData().putInt("matterAccelerationBlockTimer", 0);
                   }
               }
               if (player.level().dimension() == Level.END) {
                   EndStoneEntity endStoneEntity = player.level().getEntitiesOfClass(EndStoneEntity.class, player.getBoundingBox().inflate(10))
                           .stream()
                           .min(Comparator.comparingDouble(zombie -> zombie.distanceTo(player)))
                           .orElse(null);
                   if (endStoneEntity != null) {
                       endStoneEntity.setDeltaMovement(lookDirection);
                       endStoneEntity.setSent(true);
                       endStoneEntity.setShouldntDamage(false);
                       endStoneEntity.setTickCount(440);
                   }
                   if (endStoneEntity == null) {
                       player.getPersistentData().putInt("matterAccelerationBlockTimer", 0);
                   }
               }
           } else {
               int activeSlot = player.getInventory().selected;
               ItemStack heldItem = player.getMainHandItem();
               if (!heldItem.isEmpty() && heldItem.getItem() instanceof MatterAccelerationBlocks) {
                   player.getInventory().setItem(activeSlot, new ItemStack(ItemInit.MATTER_ACCELERATION_SELF.get()));
                   heldItem.shrink(1);
               }
           }
        });
        return true;
    }
}
