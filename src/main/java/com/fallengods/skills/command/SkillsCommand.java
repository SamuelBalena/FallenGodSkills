package com.fallengods.skills.command;

import com.fallengods.skills.capability.SkillDataCapability;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SkillsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("skills")
                .requires(source -> source.hasPermission(0))
                .executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();
                    if (!(source.getEntity() instanceof ServerPlayer player)) {
                        source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
                        return 0;
                    }

                    player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
                        source.sendSuccess(() -> Component.literal(
                                "§6=== Fallen Gods Skills ==="), false);
                        source.sendSuccess(() -> Component.literal(
                                "§eClasse: §f" + data.getPlayerClass().getDisplayName()), false);
                        source.sendSuccess(() -> Component.literal(
                                "§ePontos de habilidade: §f" + data.getSkillPoints()), false);
                        source.sendSuccess(() -> Component.literal(
                                "§eSkills desbloqueadas: §f" + (data.hasSkill("__none__") ? "sim" : "0")), false);
                    });

                    return 1;
                });

        dispatcher.register(builder);
    }
}