package com.fallengods.skills;

import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.active.ActiveSkillHandlers;
import com.fallengods.skills.skill.effect.SkillEffectRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FallenGodsSkills.MODID)
public class FallenGodsSkills {
    public static final String MODID = "fallengodsskills";

    public FallenGodsSkills(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();
        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.register();
            SkillRegistry.init();
            SkillEffectRegistry.init();
            ActiveSkillHandlers.init();
        });
    }
}