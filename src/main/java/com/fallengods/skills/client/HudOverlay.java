package com.fallengods.skills.client;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.network.PacketBindSkill;
import com.fallengods.skills.skill.SkillNode;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
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

        // ===== 1. Texto superior esquerdo (classe + pontos) =====
        int points = ClientSkillData.get().getSkillPoints();
        String className = classType.getDisplayName();
        String text = "§6" + className + " §7| §e" + points + " pts";
        graphics.drawString(mc.font, text, 5, 5, 0xFFFFFF);

        // ===== 2. Barra de skills ativas (parte inferior) =====
        drawSkillBar(graphics, mc);
    }

    private static void drawSkillBar(GuiGraphics graphics, Minecraft mc) {
        SkillTree tree = SkillRegistry.getTree(ClientSkillData.get().getPlayerClass());
        if (tree == null)
            return;

        int slotSize = 24;
        int gap = 2;
        int totalWidth = 12 * slotSize + 11 * gap;
        int startX = (mc.getWindow().getGuiScaledWidth() - totalWidth) / 2;
        int y = mc.getWindow().getGuiScaledHeight() - 60;

        long now = System.currentTimeMillis();

        for (int slot = 0; slot < 12; slot++) {
            int x = startX + slot * (slotSize + gap);

            String skillId = ClientSkillData.get().getBindingForSlot(slot);
            boolean hasSkill = skillId != null && !skillId.isEmpty();

            // Fundo (cinza escuro)
            graphics.fill(x, y, x + slotSize, y + slotSize, 0xAA000000);

            // Borda
            graphics.fill(x, y, x + slotSize, y + 1, 0xFFFFFFFF);
            graphics.fill(x, y + slotSize - 1, x + slotSize, y + slotSize, 0xFFFFFFFF);
            graphics.fill(x, y, x + 1, y + slotSize, 0xFFFFFFFF);
            graphics.fill(x + slotSize - 1, y, x + slotSize, y + slotSize, 0xFFFFFFFF);

            if (hasSkill) {
                SkillNode node = tree.getNode(skillId);
                if (node == null)
                    continue;

                // Nome abreviado (primeiras 2 letras) no centro
                String abbr = node.getDisplayName().length() >= 2
                        ? node.getDisplayName().substring(0, 2)
                        : node.getDisplayName();
                int abbrWidth = mc.font.width(abbr);
                graphics.drawString(mc.font, abbr,
                        x + (slotSize - abbrWidth) / 2, y + 8, 0xFFFFFF);

                // Cooldown (se em cooldown)
                long cdEnd = ClientSkillData.get().getCooldownEnd(skillId);
                if (cdEnd > now) {
                    long remaining = cdEnd - now;

                    // Tempo restante em segundos
                    String secText = (remaining / 1000) + "s";
                    int textWidth = mc.font.width(secText);
                    graphics.drawString(mc.font, secText,
                            x + (slotSize - textWidth) / 2, y + 8, 0xFFAA00);

                    // Overlay cinza por cima
                    graphics.fill(x + 1, y + 1, x + slotSize - 1, y + slotSize - 1, 0x88000000);
                }
            }

            // Nome da tecla abaixo do slot
            String keyName = PacketBindSkill.slotName(slot);
            int keyWidth = mc.font.width(keyName);
            graphics.drawString(mc.font, "§7" + keyName,
                    x + (slotSize - keyWidth) / 2, y + slotSize + 2, 0xFFFFFF);
        }
    }
}