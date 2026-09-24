package com.fallengods.skills.event;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketSyncSkillData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillPointEvents {

    /**
     * Ganha pontos ao subir de nível de XP (vanilla).
     *
     * PlayerXpEvent.LevelChange dispara ANTES do nível mudar.
     * getLevels() retorna a quantidade de níveis sendo adicionados (ou removidos).
     */
    @SubscribeEvent
    public static void onLevelChange(PlayerXpEvent.LevelChange event) {
        if (!(event.getEntity() instanceof ServerPlayer player))
            return;
        if (player.level().isClientSide())
            return;

        int levelsChanged = event.getLevels();

        // Só ganha ponto se está SUBINDO de nível
        if (levelsChanged <= 0)
            return;

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            // Só dá pontos se o jogador já escolheu uma classe
            if (data.getPlayerClass() == ClassType.NONE)
                return;

            data.addSkillPoints(levelsChanged);

            player.sendSystemMessage(Component.literal(
                    "§a+" + levelsChanged + " Ponto" + (levelsChanged > 1 ? "s" : "")
                            + " de Habilidade §7(Total: " + data.getSkillPoints() + ")"));

            // Sincroniza cliente
            PacketHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new PacketSyncSkillData(data.serializeNBT()));
        });
    }
}