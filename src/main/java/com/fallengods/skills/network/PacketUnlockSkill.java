package com.fallengods.skills.network;

import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.skill.Skill;
import com.fallengods.skills.skill.SkillRegistry;
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
                Skill skill = SkillRegistry.getSkill(data.getPlayerClass(), msg.skillId);
                if (skill == null)
                    return;
                if (data.hasSkill(msg.skillId))
                    return;
                if (data.getSkillPoints() < skill.getCost()) {
                    player.sendSystemMessage(Component.literal("§cPontos insuficientes."));
                    return;
                }

                data.setSkillPoints(data.getSkillPoints() - skill.getCost());
                data.unlockSkill(msg.skillId);

                player.sendSystemMessage(Component.literal(
                        "§aSkill desbloqueada: §f" + skill.getDisplayName()));

                PacketHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new PacketSyncSkillData(data.serializeNBT()));
            });
        });
        ctx.get().setPacketHandled(true);
    }
}