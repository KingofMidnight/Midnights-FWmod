package net.kmidnight.midnightsfwmod.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LeftClickC2S {
    public LeftClickC2S() {

    }

    public LeftClickC2S(FriendlyByteBuf buf) {

    }

    public void toByte(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {
            if (player == null) return;
            ItemStack heldItem = player.getMainHandItem();
            byte[] keysClicked = player.getPersistentData().getByteArray("keysClicked");

            if (!heldItem.isEmpty() && heldItem.getItem() instanceof BeyonderAbilityUser) {
                for (int i = 0; i < keysClicked.length; i++) {
                    if (keysClicked[i] == 0) {
                        keysClicked[i] = 1;
                        BeyonderAbilityUser.clicked(player, InteractionHand.MAIN_HAND);
                        break;
                    }
                }
            }
            if (player.getMainHandItem().getItem() instanceof LightningStorm) {
                CompoundTag tag = player.getPersistentData();
                double distance = tag.getDouble("sailorLightningStormDistance");
                tag.putDouble("sailorLightningStormDistance", (int) (distance + 30));
                player.sendSystemMessage(Component.literal("Storm Radius Is " + distance).withStyle(BeyonderUtil.getStyle(player)));
                if (distance > 300) {
                    tag.putDouble("sailorLightningStormDistance", 0);
                }
            }
            if (player.getMainHandItem().getItem() instanceof Hurricane) {
                CompoundTag tag = player.getPersistentData();
                boolean sailorHurricaneRain = tag.getBoolean("sailorHurricaneRain");
                if (sailorHurricaneRain) {
                    player.displayClientMessage(Component.literal("Hurricane will only cause rain").withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE), true);
                    tag.putBoolean("sailorHurricaneRain", false);
                } else {
                    player.displayClientMessage(Component.literal("Hurricane cause lightning, tornadoes, and rain").withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE), true);
                    tag.putBoolean("sailorHurricaneRain", true);
                }
            }
        });
        return true;
    }
}
