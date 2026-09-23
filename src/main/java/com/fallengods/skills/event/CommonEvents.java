package com.fallengods.skills.event;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketOpenClassScreen;
import com.fallengods.skills.network.PacketSyncSkillData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player))
            return;

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            // Sincronizar dados primeiro
            PacketHandler.INSTANCE.sendTo(
                    new PacketSyncSkillData(data.serializeNBT()),
                    player);

            // Se não tiver classe, abrir GUI
            if (data.getPlayerClass() == ClassType.NONE) {
                PacketHandler.INSTANCE.sendTo(new PacketOpenClassScreen(), player);
            }
        });
    }
}