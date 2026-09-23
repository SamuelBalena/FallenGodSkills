package com.fallengods.skills;

import com.fallengods.skills.network.PacketHandler;
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
        event.enqueueWork(PacketHandler::register);
    }
}