package com.fallengods.skills.client;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.client.gui.SkillTreeScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, value = Dist.CLIENT)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;

        if (mc.screen == null) {
            if (KeyBindings.OPEN_SKILL_TREE.get().consumeClick()) {
                ClassType classType = ClientSkillData.get().getPlayerClass();
                if (classType != ClassType.NONE) {
                    mc.setScreen(new SkillTreeScreen());
                }
            }
        } else {
            // Drena cliques enquanto screen aberto
            while (KeyBindings.OPEN_SKILL_TREE.get().consumeClick()) {
                /* descarta */ }
        }
    }
}