package net.kmidnight.midnightsfwmod.event;

import net.kmidnight.midnightsfwmod.MidnightsfwMod;
import net.kmidnight.midnightsfwmod.capabilities.IMana;
import net.kmidnight.midnightsfwmod.capabilities.IStamina;
import net.kmidnight.midnightsfwmod.capabilities.ManaProvider;
import net.kmidnight.midnightsfwmod.capabilities.StaminaProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=MidnightsfwMod.MODID)
public class EventHandler {

    @SubscribeEvent
    public void onPlayerLogins(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            player.getCapability(ManaProvider.MANA_CAP).ifPresent(mana -> {
                String message = String.format("Hello there, you have %d mana left.", (int) mana.getMana());
                ((ServerPlayer) player).sendSystemMessage(Component.literal(message));
            });
        }
        if (player instanceof ServerPlayer) {
            player.getCapability(StaminaProvider.STAMINA_CAP).ifPresent(stamina -> {
                String message = String.format("Hello there, you have %d stamina left.", (int) stamina.getStamina());
                ((ServerPlayer) player).sendSystemMessage(Component.literal(message));
            });
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        IMana mana = (IMana) player.getCapability(ManaProvider.MANA_CAP, null);
        IMana oldMana = (IMana) event.getOriginal().getCapability(ManaProvider.MANA_CAP, null);
        mana.set(oldMana.getMana());
        IStamina stamina = (IStamina) player.getCapability(StaminaProvider.STAMINA_CAP, null);
        IStamina oldStamina = (IStamina) event.getOriginal().getCapability(StaminaProvider.STAMINA_CAP, null);
        stamina.set(oldStamina.getStamina());
    }
}
