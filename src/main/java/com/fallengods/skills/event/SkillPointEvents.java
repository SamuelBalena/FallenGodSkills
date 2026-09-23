package com.fallengods.skills.event;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketSyncSkillData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillPointEvents {

    @SubscribeEvent
    public static void onMobKilled(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Mob mob))
            return;
        if (!(mob instanceof Enemy))
            return;
        if (mob.level().isClientSide())
            return;
        if (!(mob.getKillCredit() instanceof ServerPlayer player))
            return;

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            if (data.getPlayerClass().name().equals("NONE"))
                return;

            data.addSkillPoints(1);

            // Mensagem no chat
            player.sendSystemMessage(Component.literal(
                    "§a+1 Ponto de Habilidade §7(Total: " + data.getSkillPoints() + ")"));

            // Sincroniza com o cliente
            PacketHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new PacketSyncSkillData(data.serializeNBT()));
        });
    }
}