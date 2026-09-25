package com.fallengods.skills.network;

import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.skill.SkillNode;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
import com.fallengods.skills.skill.SkillType;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import com.fallengods.skills.skill.active.ActiveSkillHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketActivateSkill {
    private final String skillId;

    public PacketActivateSkill(String skillId) {
        this.skillId = skillId;
    }

    public static void encode(PacketActivateSkill msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.skillId);
    }

    public static PacketActivateSkill decode(FriendlyByteBuf buf) {
        return new PacketActivateSkill(buf.readUtf());
    }

    public static void handle(PacketActivateSkill msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null)
                return;

            player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
                ClassType classType = data.getPlayerClass();
                SkillTree tree = SkillRegistry.getTree(classType);
                if (tree == null)
                    return;

                SkillNode node = tree.getNode(msg.skillId);
                if (node == null)
                    return;

                // ===== Validações =====
                if (!data.hasSkill(msg.skillId)) {
                    player.sendSystemMessage(Component.literal(
                            "§cVocê não tem essa skill."));
                    return;
                }
                if (node.getType() != SkillType.ATIVA) {
                    player.sendSystemMessage(Component.literal(
                            "§cEssa skill não é ativa."));
                    return;
                }

                long now = System.currentTimeMillis();
                long cooldownEnd = data.getCooldownEnd(msg.skillId);
                if (now < cooldownEnd) {
                    long remaining = (cooldownEnd - now) / 1000;
                    player.sendSystemMessage(Component.literal(
                            "§cSkill em cooldown: " + remaining + "s"));
                    return;
                }

                // ===== Executa =====
                ActiveSkillHandler handler = ActiveSkillHandlers.get(msg.skillId);
                if (handler == null) {
                    player.sendSystemMessage(Component.literal(
                            "§cSkill sem handler implementado: " + msg.skillId));
                    return;
                }

                handler.activate(player, data);

                // ===== Marca cooldown =====
                long cdMs = handler.getCooldownMs();
                if (cdMs > 0) {
                    data.setCooldownEnd(msg.skillId, now + cdMs);
                }

                // ===== Sincroniza =====
                PacketHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new PacketSyncSkillData(data.serializeNBT()));
            });
        });
        ctx.get().setPacketHandled(true);
    }
}