package com.fallengods.skills.capability;

import com.fallengods.skills.FallenGodsSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillDataCapability {
    public static final Capability<PlayerSkillData> PLAYER_SKILL_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private static final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(FallenGodsSkills.MODID,
            "skill_data");

    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PLAYER_SKILL_DATA).isPresent()) {
                event.addCapability(KEY, new SkillDataProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(PLAYER_SKILL_DATA).ifPresent(oldData -> {
            event.getEntity().getCapability(PLAYER_SKILL_DATA).ifPresent(newData -> {
                newData.deserializeNBT(oldData.serializeNBT());
            });
        });
        event.getOriginal().invalidateCaps();
    }

    // Registro da capability — precisa ficar no MOD bus
    @Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void register(RegisterCapabilitiesEvent event) {
            event.register(PlayerSkillData.class);
        }
    }
}