package com.fallengods.skills.client;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.classsystem.ClassType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, value = Dist.CLIENT)
public class HudOverlay {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui)
            return;

        ClassType classType = ClientSkillData.get().getPlayerClass();
        if (classType == ClassType.NONE)
            return;

        GuiGraphics graphics = event.getGuiGraphics();
        int points = ClientSkillData.get().getSkillPoints();

        String text = "§6" + classType.getDisplayName() + " §7| §e" + points + " pts";
        graphics.drawString(mc.font, text, 5, 5, 0xFFFFFF);
    }
}