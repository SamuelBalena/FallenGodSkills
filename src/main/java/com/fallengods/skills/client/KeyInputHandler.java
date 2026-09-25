package com.fallengods.skills.client;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.client.gui.SkillTreeScreen;
import com.fallengods.skills.network.PacketActivateSkill;
import com.fallengods.skills.network.PacketHandler;
import net.minecraft.client.KeyMapping;
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

        // ===== Abrir árvore (tecla K) =====
        // Só funciona se nenhum screen estiver aberto
        if (mc.screen == null) {
            while (KeyBindings.OPEN_SKILL_TREE.get().consumeClick()) {
                ClassType classType = ClientSkillData.get().getPlayerClass();
                if (classType != ClassType.NONE) {
                    mc.setScreen(new SkillTreeScreen());
                }
            }
        }

        // ===== Skills ativas (12 teclas) =====
        // Também só funciona sem screen aberto
        if (mc.screen == null) {
            for (int slot = 0; slot < KeyBindings.ALL_SKILL_KEYS.length; slot++) {
                KeyMapping key = KeyBindings.ALL_SKILL_KEYS[slot].get();
                while (key.consumeClick()) {
                    tryActivateSlot(slot);
                }
            }
        } else {
            // Drena os cliques mesmo com screen aberto pra não acumular
            for (int slot = 0; slot < KeyBindings.ALL_SKILL_KEYS.length; slot++) {
                KeyMapping key = KeyBindings.ALL_SKILL_KEYS[slot].get();
                while (key.consumeClick()) {
                    // descarta
                }
            }
            while (KeyBindings.OPEN_SKILL_TREE.get().consumeClick()) {
                // descarta
            }
        }
    }

    private static void tryActivateSlot(int slot) {
        String skillId = ClientSkillData.get().getBindingForSlot(slot);
        if (skillId == null || skillId.isEmpty())
            return;

        // Otimista: envia o pacote. O servidor valida tudo.
        PacketHandler.INSTANCE.sendToServer(new PacketActivateSkill(skillId));
    }
}