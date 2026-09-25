package com.fallengods.skills.network;

import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.skill.SkillNode;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
import com.fallengods.skills.skill.SkillType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketBindSkill {
    private final String skillId;
    private final int slot; // -1 para desvincular

    public PacketBindSkill(String skillId, int slot) {
        this.skillId = skillId;
        this.slot = slot;
    }

    public static void encode(PacketBindSkill msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.skillId);
        buf.writeInt(msg.slot);
    }

    public static PacketBindSkill decode(FriendlyByteBuf buf) {
        return new PacketBindSkill(buf.readUtf(), buf.readInt());
    }

    public static void handle(PacketBindSkill msg, Supplier<NetworkEvent.Context> ctx) {
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
                if (!data.hasSkill(msg.skillId)) {
                    player.sendSystemMessage(Component.literal("§cVocê não tem essa skill."));
                    return;
                }
                if (node.getType() != SkillType.ATIVA) {
                    player.sendSystemMessage(Component.literal("§cSó skills ativas podem ser vinculadas."));
                    return;
                }

                if (msg.slot < 0 || msg.slot >= 12) {
                    // desvincular
                    int oldSlot = data.getSlotForSkill(msg.skillId);
                    if (oldSlot >= 0)
                        data.setBinding(oldSlot, null);
                    player.sendSystemMessage(Component.literal("§7Skill desvinculada."));
                } else {
                    // remove se já estava em outro slot
                    int oldSlot = data.getSlotForSkill(msg.skillId);
                    if (oldSlot >= 0)
                        data.setBinding(oldSlot, null);

                    // verifica se o slot está ocupado
                    String current = data.getBindingForSlot(msg.slot);
                    if (current != null && !current.isEmpty()) {
                        player.sendSystemMessage(Component.literal(
                                "§7Substituindo skill no slot " + (msg.slot + 1) + "."));
                    }

                    data.setBinding(msg.slot, msg.skillId);
                    player.sendSystemMessage(Component.literal(
                            "§aVinculado §f" + node.getDisplayName()
                                    + " §7→ tecla §f" + slotName(msg.slot)));
                }

                PacketHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new PacketSyncSkillData(data.serializeNBT()));
            });
        });
        ctx.get().setPacketHandled(true);
    }

    public static String slotName(int slot) {
        switch (slot) {
            case 0:
                return "Q";
            case 1:
                return "E";
            case 2:
                return "R";
            case 3:
                return "F";
            case 4:
                return "T";
            case 5:
                return "G";
            case 6:
                return "V";
            case 7:
                return "C";
            case 8:
                return "X";
            case 9:
                return "Z";
            case 10:
                return "Shift+Q";
            case 11:
                return "Shift+E";
            default:
                return "?";
        }
    }
}