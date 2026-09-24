package com.fallengods.skills.client.gui;

import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.client.ClientSkillData;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketUnlockSkill;
import com.fallengods.skills.skill.SkillNode;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SkillTreeScreen extends Screen {

    private int scrollOffset = 0;
    private static final int LINE_HEIGHT = 22;
    private static final int VISIBLE_LINES = 18;

    public SkillTreeScreen() {
        super(Component.literal("Árvore de Habilidades"));
    }

    @Override
    protected void init() {
        buildButtons();

        ClientSkillData.setChangeListener(this::rebuildSafely);
    }

    @Override
    public void removed() {
        ClientSkillData.clearChangeListener();
        super.removed();
    }

    private void rebuildSafely() {
        if (this.minecraft == null || this.minecraft.screen != this)
            return;
        this.clearWidgets();
        buildButtons();
    }

    private void buildButtons() {
        ClassType classType = ClientSkillData.get().getPlayerClass();
        if (classType == ClassType.NONE) {
            this.onClose();
            return;
        }

        SkillTree tree = SkillRegistry.getTree(classType);
        if (tree == null)
            return;

        List<SkillNode> nodes = tree.getNodes();
        int centerX = this.width / 2;
        int startY = 50;

        int end = Math.min(nodes.size(), scrollOffset + VISIBLE_LINES);
        for (int i = scrollOffset; i < end; i++) {
            final SkillNode node = nodes.get(i);
            int row = i - scrollOffset;

            boolean unlocked = ClientSkillData.get().hasSkill(node.getId());
            boolean available = canUnlock(node);
            int points = ClientSkillData.get().getSkillPoints();
            boolean canAfford = points >= node.getCost();

            String prefix;
            if (unlocked)
                prefix = "§a✔ ";
            else if (available)
                prefix = "§e○ ";
            else
                prefix = "§c✘ ";

            String branch = "§8[" + node.getBranch().getDisplayName().substring(0, 3) + "] ";
            String type = node.getType().name().equals("ATIVA") ? "§d★ " : "§7• ";

            String label = prefix + branch + type + "§f" + node.getDisplayName()
                    + " §7(" + node.getDescription() + ")";

            Button btn = Button.builder(
                    Component.literal(label),
                    b -> tryUnlock(node)).bounds(centerX - 200, startY + row * LINE_HEIGHT, 400, 20).build();

            btn.active = !unlocked && available && canAfford;
            this.addRenderableWidget(btn);
        }

        // Botões de scroll
        int bottomY = startY + VISIBLE_LINES * LINE_HEIGHT + 5;

        Button upBtn = Button.builder(
                Component.literal("▲"),
                b -> {
                    if (scrollOffset > 0) {
                        scrollOffset--;
                        rebuildSafely();
                    }
                }).bounds(centerX - 60, bottomY, 30, 20).build();
        upBtn.active = scrollOffset > 0;
        this.addRenderableWidget(upBtn);

        Button downBtn = Button.builder(
                Component.literal("▼"),
                b -> {
                    if (scrollOffset + VISIBLE_LINES < nodes.size()) {
                        scrollOffset++;
                        rebuildSafely();
                    }
                }).bounds(centerX + 30, bottomY, 30, 20).build();
        downBtn.active = scrollOffset + VISIBLE_LINES < nodes.size();
        this.addRenderableWidget(downBtn);

        this.addRenderableWidget(Button.builder(
                Component.literal("Fechar"),
                b -> this.onClose()).bounds(centerX - 40, bottomY, 80, 20).build());
    }

    private boolean canUnlock(SkillNode node) {
        if (ClientSkillData.get().hasSkill(node.getId()))
            return false;
        for (String pre : node.getPrerequisites()) {
            if (!ClientSkillData.get().hasSkill(pre))
                return false;
        }
        return true;
    }

    private void tryUnlock(SkillNode node) {
        PacketHandler.INSTANCE.sendToServer(new PacketUnlockSkill(node.getId()));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);

        ClassType classType = ClientSkillData.get().getPlayerClass();
        int points = ClientSkillData.get().getSkillPoints();

        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        graphics.drawCenteredString(this.font,
                "§6Classe: §f" + classType.getDisplayName()
                        + " §7| §ePontos: §f" + points,
                this.width / 2, 30, 0xFFFFFF);

        SkillTree tree = SkillRegistry.getTree(classType);
        if (tree != null) {
            int total = tree.getNodes().size();
            graphics.drawCenteredString(this.font,
                    "§7" + scrollOffset + "-" + Math.min(scrollOffset + VISIBLE_LINES, total)
                            + " de " + total + " nós",
                    this.width / 2, 40, 0xFFFFFF);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }
}