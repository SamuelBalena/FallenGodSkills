package com.fallengods.skills.network;

import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.skill.SkillNode;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
import com.fallengods.skills.skill.effect.SkillEffectRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketUnlockSkill {
    private final String skillId;

    public PacketUnlockSkill(String skillId) {
        this.skillId = skillId;
    }

    public static void encode(PacketUnlockSkill msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.skillId);
    }

    public static PacketUnlockSkill decode(FriendlyByteBuf buf) {
        return new PacketUnlockSkill(buf.readUtf());
    }

    public static void handle(PacketUnlockSkill msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null)
                return;

            player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
                SkillTree tree = SkillRegistry.getTree(data.getPlayerClass());
                if (tree == null)
                    return;

                SkillNode node = tree.getNode(msg.skillId);
                if (node == null)
                    return;
                if (data.hasSkill(msg.skillId))
                    return;

                // Checa pré-requisitos no servidor
                for (String pre : node.getPrerequisites()) {
                    if (!data.hasSkill(pre)) {
                        player.sendSystemMessage(Component.literal(
                                "§cPré-requisito faltando: §f" + pre));
                        return;
                    }
                }

                if (data.getSkillPoints() < node.getCost()) {
                    player.sendSystemMessage(Component.literal("§cPontos insuficientes."));
                    return;
                }

                data.setSkillPoints(data.getSkillPoints() - node.getCost());
                data.unlockSkill(msg.skillId);

                // ===== CORREÇÃO: recalcular bônus depois de comprar =====
                data.setAccumulatedBonuses(
                        SkillEffectRegistry.recalcBonuses(
                                data.getPlayerClass(),
                                data.getUnlockedSkillsSet()));

                player.sendSystemMessage(Component.literal(
                        "§aSkill desbloqueada: §f" + node.getDisplayName()));

                PacketHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new PacketSyncSkillData(data.serializeNBT()));
            });
        });
        ctx.get().setPacketHandled(true);
    }
}