package com.fallengods.skills.command;

import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketSyncSkillData;
import com.fallengods.skills.skill.Skill;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class SkillsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skills")
                .requires(source -> source.hasPermission(0))
                // /skills
                .executes(ctx -> showInfo(ctx.getSource()))
                // /skills list
                .then(Commands.literal("list")
                        .executes(ctx -> listSkills(ctx.getSource())))
                // /skills unlock <id>
                .then(Commands.literal("unlock")
                        .then(Commands.argument("skillId", StringArgumentType.string())
                                .executes(ctx -> unlockSkill(
                                        ctx.getSource(),
                                        StringArgumentType.getString(ctx, "skillId"))))));
    }

    private static int showInfo(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
            return 0;
        }

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            source.sendSuccess(() -> Component.literal("§6=== Fallen Gods Skills ==="), false);
            source.sendSuccess(() -> Component.literal(
                    "§eClasse: §f" + data.getPlayerClass().getDisplayName()), false);
            source.sendSuccess(() -> Component.literal(
                    "§ePontos de habilidade: §f" + data.getSkillPoints()), false);
            source.sendSuccess(() -> Component.literal(
                    "§7Use §f/skills list §7para ver as skills da sua classe."), false);
        });
        return 1;
    }

    private static int listSkills(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
            return 0;
        }

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            ClassType classType = data.getPlayerClass();
            if (classType == ClassType.NONE) {
                source.sendFailure(Component.literal("Você ainda não escolheu uma classe."));
                return;
            }

            SkillTree tree = SkillRegistry.getTree(classType);
            if (tree == null) {
                source.sendFailure(Component.literal("Esta classe não tem árvore registrada."));
                return;
            }

            source.sendSuccess(() -> Component.literal(
                    "§6=== Árvore: " + classType.getDisplayName() + " ==="), false);

            for (Skill skill : tree.getSkills()) {
                boolean unlocked = data.hasSkill(skill.getId());
                String mark = unlocked ? "§a✔" : "§c✘";
                source.sendSuccess(() -> Component.literal(
                        mark + " §f" + skill.getId() + " §7(" + skill.getDisplayName() + ") §e- "
                                + skill.getCost() + " ponto(s) §7- " + skill.getDescription()),
                        false);
            }
        });
        return 1;
    }

    private static int unlockSkill(CommandSourceStack source, String skillId) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
            return 0;
        }

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            ClassType classType = data.getPlayerClass();
            if (classType == ClassType.NONE) {
                source.sendFailure(Component.literal("Escolha uma classe primeiro."));
                return;
            }

            Skill skill = SkillRegistry.getSkill(classType, skillId);
            if (skill == null) {
                source.sendFailure(Component.literal("Skill não encontrada: " + skillId));
                return;
            }

            if (data.hasSkill(skillId)) {
                source.sendFailure(Component.literal("Você já desbloqueou essa skill."));
                return;
            }

            if (data.getSkillPoints() < skill.getCost()) {
                source.sendFailure(Component.literal(
                        "Pontos insuficientes. Precisa de " + skill.getCost()
                                + ", você tem " + data.getSkillPoints() + "."));
                return;
            }

            data.setSkillPoints(data.getSkillPoints() - skill.getCost());
            data.unlockSkill(skillId);

            source.sendSuccess(() -> Component.literal(
                    "§aSkill desbloqueada: §f" + skill.getDisplayName()
                            + " §7(-" + skill.getCost() + " pontos)"),
                    false);

            // Sincroniza cliente
            PacketHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new PacketSyncSkillData(data.serializeNBT()));
        });
        return 1;
    }
}