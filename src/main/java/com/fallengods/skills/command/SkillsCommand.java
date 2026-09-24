package com.fallengods.skills.command;

import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketSyncSkillData;
import com.fallengods.skills.skill.SkillNode;
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
                .executes(ctx -> showInfo(ctx.getSource()))
                .then(Commands.literal("list")
                        .executes(ctx -> listSkills(ctx.getSource())))
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
                    "§7Use §f/skills list §7para ver a árvore da sua classe."), false);
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

            for (SkillNode node : tree.getNodes()) {
                boolean unlocked = data.hasSkill(node.getId());
                boolean available = canUnlock(data, tree, node);

                String mark;
                if (unlocked)
                    mark = "§a✔";
                else if (available)
                    mark = "§e○";
                else
                    mark = "§c✘";

                String branch = "§8[" + node.getBranch().getDisplayName().substring(0, 3) + "]";
                String type = node.getType().name().equals("ATIVA") ? "§d★" : "§7•";

                source.sendSuccess(() -> Component.literal(
                        mark + " " + branch + " " + type + " §f" + node.getId() + " §7- "
                                + node.getDisplayName() + " §8(" + node.getDescription() + ")"),
                        false);
            }
        });
        return 1;
    }

    private static boolean canUnlock(com.fallengods.skills.capability.PlayerSkillData data,
            SkillTree tree, SkillNode node) {
        if (data.hasSkill(node.getId()))
            return false;
        for (String pre : node.getPrerequisites()) {
            if (!data.hasSkill(pre))
                return false;
        }
        return true;
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

            SkillTree tree = SkillRegistry.getTree(classType);
            if (tree == null)
                return;

            SkillNode node = tree.getNode(skillId);
            if (node == null) {
                source.sendFailure(Component.literal("Nó não encontrado: " + skillId));
                return;
            }

            if (data.hasSkill(skillId)) {
                source.sendFailure(Component.literal("Você já desbloqueou essa skill."));
                return;
            }

            // Checa pré-requisitos
            for (String pre : node.getPrerequisites()) {
                if (!data.hasSkill(pre)) {
                    source.sendFailure(Component.literal(
                            "§cPré-requisito faltando: §f" + pre));
                    return;
                }
            }

            if (data.getSkillPoints() < node.getCost()) {
                source.sendFailure(Component.literal(
                        "Pontos insuficientes. Precisa de " + node.getCost()
                                + ", você tem " + data.getSkillPoints() + "."));
                return;
            }

            data.setSkillPoints(data.getSkillPoints() - node.getCost());
            data.unlockSkill(skillId);

            source.sendSuccess(() -> Component.literal(
                    "§aSkill desbloqueada: §f" + node.getDisplayName()
                            + " §7(-" + node.getCost() + " ponto)"),
                    false);

            PacketHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new PacketSyncSkillData(data.serializeNBT()));
        });
        return 1;
    }
}