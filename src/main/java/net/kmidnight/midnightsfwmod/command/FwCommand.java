package net.kmidnight.midnightsfwmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.kmidnight.midnightsfwmod.capabilities.ManaProvider;
import net.kmidnight.midnightsfwmod.capabilities.StaminaProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FwCommand {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
            Commands.literal("Fw")
                .then(Commands.literal("mana")
                    .then(Commands.literal("check")
                        .executes(context -> {
                            Entity entity = context.getSource().getEntity();
                            if (entity != null) {
                                return checkMana(entity);
                            }
                            return 0;
                        })
                        .then(Commands.argument("target", EntityArgument.entity())
                            .executes(context -> {
                                Entity targetEntity = EntityArgument.getEntity(context, "target");
                                return checkMana(targetEntity);
                            })
                        )
                    )
                    .then(Commands.literal("set")
                        .executes(context -> {
                            Entity entity = context.getSource().getEntity();
                            int amount = IntegerArgumentType.getInteger(context, "amount");
                            if (entity != null) {
                                return setMana(entity, amount);
                            }
                            return 0;
                        })
                        .then(Commands.argument("target", EntityArgument.entity())
                            .executes(context -> {
                                Entity targetEntity = EntityArgument.getEntity(context, "target");
                                int amount = IntegerArgumentType.getInteger(context, "amount");
                                return setMana(targetEntity, amount);
                            })
                        )
                    )
                )
                .then(Commands.literal("stamina")
                    .then(Commands.literal("check")
                        .executes(context -> {
                            Entity entity = context.getSource().getEntity();
                            if (entity != null) {
                                return checkStamina(entity);
                            }
                            return 0;
                        })
                        .then(Commands.argument("target", EntityArgument.entity())
                            .executes(context -> {
                                Entity targetEntity = EntityArgument.getEntity(context, "target");
                                return checkStamina(targetEntity);
                            })
                        )
                    )
                    .then(Commands.literal("set")
                        .executes(context -> {
                            Entity entity = context.getSource().getEntity();
                            int amount = IntegerArgumentType.getInteger(context, "amount");
                            if (entity != null) {
                                return setStamina(entity, amount);
                            }
                            return 0;
                        })
                        .then(Commands.argument("target", EntityArgument.entity())
                            .executes(context -> {
                                Entity targetEntity = EntityArgument.getEntity(context, "target");
                                int amount = IntegerArgumentType.getInteger(context, "amount");
                                return setStamina(targetEntity, amount);
                            })
                        )
                    )
                )
        );
    }

    private static int checkMana(Entity entity) {
        entity.getCapability(ManaProvider.MANA_CAP).ifPresent(mana -> {
            float manaValue = mana.getMana();
            entity.sendSystemMessage(Component.literal("Mana: " + manaValue));
        });
        return 1;
    }

    private static int checkStamina(Entity entity) {
        entity.getCapability(StaminaProvider.STAMINA_CAP).ifPresent(stamina -> {
            float staminaValue = stamina.getStamina();
            entity.sendSystemMessage(Component.literal("Stamina: " + staminaValue));
        });
        return 1;
    }

    private static int setMana(Entity entity, int amount) {
        entity.getCapability(ManaProvider.MANA_CAP).ifPresent(mana -> {
            mana.set(amount);
            entity.sendSystemMessage(Component.literal("Mana set to: " + amount));
        });
        return 1;
    }

    private static int setStamina(Entity entity, int amount) {
        entity.getCapability(StaminaProvider.STAMINA_CAP).ifPresent(stamina -> {
            stamina.set(amount);
            entity.sendSystemMessage(Component.literal("Stamina set to: " + amount));
        });
        return 1;
    }
}