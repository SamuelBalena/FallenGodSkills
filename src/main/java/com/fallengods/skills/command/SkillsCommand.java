package com.fallengods.skills.command;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.network.PacketBindSkill;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketSyncSkillData;
import com.fallengods.skills.skill.SkillNode;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
import com.fallengods.skills.skill.SkillType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.PacketDistributor;

public class SkillsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skills")
                .requires(source -> source.hasPermission(0))
                .executes(ctx -> showInfo(ctx.getSource()))
                .then(Commands.literal("list")
                        .executes(ctx -> listSkills(ctx.getSource())))
                .then(Commands.literal("stats")
                        .executes(ctx -> showStats(ctx.getSource())))
                .then(Commands.literal("attr")
                        .executes(ctx -> showAttributes(ctx.getSource())))
                .then(Commands.literal("bindings")
                        .executes(ctx -> showBindings(ctx.getSource())))
                .then(Commands.literal("bind")
                        .then(Commands.argument("skillId", StringArgumentType.string())
                                .then(Commands.argument("slot", IntegerArgumentType.integer(0, 11))
                                        .executes(ctx -> bindSkill(
                                                ctx.getSource(),
                                                StringArgumentType.getString(ctx, "skillId"),
                                                IntegerArgumentType.getInteger(ctx, "slot"))))
                                .executes(ctx -> unbindSkill(
                                        ctx.getSource(),
                                        StringArgumentType.getString(ctx, "skillId")))))
                .then(Commands.literal("unlock")
                        .then(Commands.argument("skillId", StringArgumentType.string())
                                .executes(ctx -> unlockSkill(
                                        ctx.getSource(),
                                        StringArgumentType.getString(ctx, "skillId"))))));
    }

    // =====================================================================
    // /skills
    // =====================================================================
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
                    "§7Use §f/skills list §7para ver a árvore."), false);
            source.sendSuccess(() -> Component.literal(
                    "§7Use §f/skills bindings §7para ver os binds."), false);
        });
        return 1;
    }

    // =====================================================================
    // /skills list
    // =====================================================================
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
                boolean available = canUnlock(data, node);

                String mark;
                if (unlocked)
                    mark = "§a✔";
                else if (available)
                    mark = "§e○";
                else
                    mark = "§c✘";

                String branch = "§8[" + node.getBranch().getDisplayName().substring(0, 3) + "]";
                String type = node.getType() == SkillType.ATIVA ? "§d★" : "§7•";

                source.sendSuccess(() -> Component.literal(
                        mark + " " + branch + " " + type + " §f" + node.getId() + " §7- "
                                + node.getDisplayName() + " §8(" + node.getDescription() + ")"),
                        false);
            }
        });
        return 1;
    }

    // =====================================================================
    // /skills stats
    // =====================================================================
    private static int showStats(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
            return 0;
        }

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            source.sendSuccess(() -> Component.literal("§6=== Bônus Acumulados ==="), false);

            if (data.getAccumulatedBonuses().isEmpty()) {
                source.sendSuccess(() -> Component.literal("§7(nenhum bônus ainda)"), false);
                return;
            }

            data.getAccumulatedBonuses().forEach((type, value) -> source.sendSuccess(() -> Component.literal(
                    "§e" + type.getId() + "§7: §f" + String.format("%.2f", value)), false));
        });
        return 1;
    }

    // =====================================================================
    // /skills attr
    // =====================================================================
    private static int showAttributes(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
            return 0;
        }

        source.sendSuccess(() -> Component.literal("§6=== Atributos Reais ==="), false);

        printAttr(source, player, "Armadura", Attributes.ARMOR);
        printAttr(source, player, "Toughness de Armadura", Attributes.ARMOR_TOUGHNESS);
        printAttr(source, player, "Vida Maxima", Attributes.MAX_HEALTH);
        printAttr(source, player, "Dano de Ataque", Attributes.ATTACK_DAMAGE);
        printAttr(source, player, "Velocidade de Ataque", Attributes.ATTACK_SPEED);
        printAttr(source, player, "Velocidade de Movimento", Attributes.MOVEMENT_SPEED);
        printAttr(source, player, "Resistencia a Knockback", Attributes.KNOCKBACK_RESISTANCE);

        return 1;
    }

    private static void printAttr(CommandSourceStack source, ServerPlayer player,
            String label, Attribute attr) {
        AttributeInstance inst = player.getAttribute(attr);
        if (inst == null) {
            source.sendSuccess(() -> Component.literal(
                    "§7" + label + "§f: §c(nao aplicavel)"), false);
            return;
        }
        double base = inst.getBaseValue();
        double total = inst.getValue();
        source.sendSuccess(() -> Component.literal(
                "§7" + label + "§f: §a" + String.format("%.2f", total)
                        + " §7(base " + String.format("%.2f", base) + ")"),
                false);
    }

    // =====================================================================
    // /skills bindings
    // =====================================================================
    private static int showBindings(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
            return 0;
        }

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            source.sendSuccess(() -> Component.literal("§6=== Binds ==="), false);

            boolean any = false;
            for (int slot = 0; slot < 12; slot++) {
                String skillId = data.getBindingForSlot(slot);
                if (skillId == null || skillId.isEmpty())
                    continue;
                any = true;

                SkillTree tree = SkillRegistry.getTree(data.getPlayerClass());
                SkillNode node = tree != null ? tree.getNode(skillId) : null;
                String name = node != null ? node.getDisplayName() : skillId;

                final int s = slot;
                source.sendSuccess(() -> Component.literal(
                        "§e" + PacketBindSkill.slotName(s) + "§7: §f" + name
                                + " §8(" + skillId + ")"),
                        false);
            }

            if (!any) {
                source.sendSuccess(() -> Component.literal(
                        "§7(nenhum bind ainda) §f/skills bind <skillId> <slot>"), false);
            }
        });
        return 1;
    }

    // =====================================================================
    // /skills bind <skillId> <slot>
    // =====================================================================
    private static int bindSkill(CommandSourceStack source, String skillId, int slot) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
            return 0;
        }

        // Reutiliza a lógica do pacote. Vamos chamar direto pelo server.
        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            SkillTree tree = SkillRegistry.getTree(data.getPlayerClass());
            if (tree == null)
                return;

            SkillNode node = tree.getNode(skillId);
            if (node == null) {
                source.sendFailure(Component.literal("Nó não encontrado: " + skillId));
                return;
            }
            if (!data.hasSkill(skillId)) {
                source.sendFailure(Component.literal("Você não tem essa skill."));
                return;
            }
            if (node.getType() != SkillType.ATIVA) {
                source.sendFailure(Component.literal("Só skills ativas podem ser vinculadas."));
                return;
            }

            // Remove se já estava em outro slot
            int oldSlot = data.getSlotForSkill(skillId);
            if (oldSlot >= 0)
                data.setBinding(oldSlot, null);

            data.setBinding(slot, skillId);

            source.sendSuccess(() -> Component.literal(
                    "§aVinculado §f" + node.getDisplayName()
                            + " §7→ tecla §f" + PacketBindSkill.slotName(slot)),
                    false);

            PacketHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new PacketSyncSkillData(data.serializeNBT()));
        });
        return 1;
    }

    // =====================================================================
    // /skills bind <skillId>
    // =====================================================================
    private static int unbindSkill(CommandSourceStack source, String skillId) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Este comando só pode ser usado por jogadores."));
            return 0;
        }

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            int oldSlot = data.getSlotForSkill(skillId);
            if (oldSlot < 0) {
                source.sendFailure(Component.literal("Essa skill não está vinculada."));
                return;
            }
            data.setBinding(oldSlot, null);

            source.sendSuccess(() -> Component.literal("§7Skill desvinculada."), false);

            PacketHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new PacketSyncSkillData(data.serializeNBT()));
        });
        return 1;
    }

    // =====================================================================
    // /skills unlock <id>
    // =====================================================================
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

            data.setAccumulatedBonuses(
                    com.fallengods.skills.skill.effect.SkillEffectRegistry.recalcBonuses(
                            data.getPlayerClass(),
                            data.getUnlockedSkillsSet()));

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

    // =====================================================================
    // Helper
    // =====================================================================
    private static boolean canUnlock(PlayerSkillData data, SkillNode node) {
        if (data.hasSkill(node.getId()))
            return false;
        for (String pre : node.getPrerequisites()) {
            if (!data.hasSkill(pre))
                return false;
        }
        return true;
    }
}