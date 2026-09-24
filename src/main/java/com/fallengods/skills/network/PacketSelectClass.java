package com.fallengods.skills.network;

import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.effect.SkillEffectRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketSelectClass {
    private final ClassType classType;

    public PacketSelectClass(ClassType classType) {
        this.classType = classType;
    }

    public static void encode(PacketSelectClass msg, FriendlyByteBuf buf) {
        buf.writeEnum(msg.classType);
    }

    public static PacketSelectClass decode(FriendlyByteBuf buf) {
        return new PacketSelectClass(buf.readEnum(ClassType.class));
    }

    public static void handle(PacketSelectClass msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
                    if (data.getPlayerClass() == ClassType.NONE) {
                        data.setPlayerClass(msg.classType);
                        data.addSkillPoints(1);

                        // ===== Desbloqueia o nó central automaticamente =====
                        String centralId = SkillRegistry.getCentralNodeId(msg.classType);
                        if (centralId != null) {
                            data.unlockSkill(centralId);
                        }

                        // Recalcula bônus (inclui agora a passiva base + o central)
                        data.setAccumulatedBonuses(
                                SkillEffectRegistry.recalcBonuses(
                                        data.getPlayerClass(),
                                        data.getUnlockedSkillsSet()));

                        PacketHandler.INSTANCE.send(
                                PacketDistributor.PLAYER.with(() -> player),
                                new PacketSyncSkillData(data.serializeNBT()));
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}