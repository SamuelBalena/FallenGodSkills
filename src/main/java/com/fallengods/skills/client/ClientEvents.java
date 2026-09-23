package com.fallengods.skills.client;

import com.fallengods.skills.client.gui.ClassSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "fallengodsskills", value = Dist.CLIENT)
public class ClientEvents {
    public static void openClassScreen() {
        Minecraft.getInstance().setScreen(new ClassSelectionScreen());
    }
}