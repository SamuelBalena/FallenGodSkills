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
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class SkillTreeScreen extends Screen {

    // ===== Constantes de layout =====
    private static final int CELL_SIZE = 24;
    private static final int NODE_RADIUS = 8;
    private static final float ZOOM_MIN = 0.5f;
    private static final float ZOOM_MAX = 2.0f;
    private static final float ZOOM_STEP = 0.1f;
    private static final float PAN_SPEED = 8.0f;

    // ===== Estado da câmera =====
    private float cameraX = 0f;
    private float cameraY = 0f;
    private float zoom = 1.0f;

    // ===== Input =====
    private boolean isPanning = false;

    // ===== Seleção =====
    private SkillNode selectedNode = null;
    private Button learnButton = null;

    public SkillTreeScreen() {
        super(Component.literal("Árvore de Habilidades"));
    }

    @Override
    protected void init() {
        ClassType classType = ClientSkillData.get().getPlayerClass();
        if (classType == ClassType.NONE) {
            this.onClose();
            return;
        }

        SkillTree tree = SkillRegistry.getTree(classType);
        if (tree != null) {
            SkillNode central = findCentral(tree);
            if (central != null) {
                cameraX = central.getGridX();
                cameraY = central.getGridY();
            }
        }
        zoom = 1.0f;
        selectedNode = null;

        // Botão "Centralizar"
        this.addRenderableWidget(Button.builder(
                Component.literal("Centralizar"),
                b -> recenter()).bounds(this.width - 110, this.height - 30, 100, 20).build());

        // Botão "Aprender"
        learnButton = Button.builder(
                Component.literal("Aprender"),
                b -> tryLearn()).bounds(this.width / 2 - 60, this.height - 60, 120, 20).build();
        learnButton.visible = false;
        learnButton.active = false;
        this.addRenderableWidget(learnButton);

        // ===== Registra listener pro ClientSkillData =====
        ClientSkillData.setChangeListener(this::onSkillDataChanged);
    }

    @Override
    public void removed() {
        ClientSkillData.clearChangeListener();
        super.removed();
    }

    /**
     * Chamado quando o servidor sincroniza os dados (ex: após comprar skill).
     * Roda FORA do render, então é seguro mexer nos widgets.
     */
    private void onSkillDataChanged() {
        // Só age se a tela ainda está aberta
        if (this.minecraft == null || this.minecraft.screen != this)
            return;

        // Revalida o botão "Aprender" (pode ter mudado o estado do nó selecionado)
        if (selectedNode != null) {
            selectNode(selectedNode);
        }
    }

    private void recenter() {
        ClassType classType = ClientSkillData.get().getPlayerClass();
        SkillTree tree = SkillRegistry.getTree(classType);
        if (tree != null) {
            SkillNode central = findCentral(tree);
            if (central != null) {
                cameraX = central.getGridX();
                cameraY = central.getGridY();
            }
        }
        zoom = 1.0f;
    }

    private SkillNode findCentral(SkillTree tree) {
        for (SkillNode n : tree.getNodes()) {
            if (n.isCentral())
                return n;
        }
        return null;
    }

    // =====================================================================
    // SELEÇÃO + BOTÃO
    // =====================================================================
    private void selectNode(SkillNode node) {
        selectedNode = node;
        if (learnButton != null) {
            learnButton.visible = true;

            boolean unlocked = ClientSkillData.get().hasSkill(node.getId());
            boolean available = canUnlock(node);
            int points = ClientSkillData.get().getSkillPoints();
            boolean canAfford = points >= node.getCost();

            if (unlocked) {
                learnButton.setMessage(Component.literal("§7Já aprendida"));
                learnButton.active = false;
            } else if (!available) {
                learnButton.setMessage(Component.literal("§cPré-requisito faltando"));
                learnButton.active = false;
            } else if (!canAfford) {
                learnButton.setMessage(Component.literal("§cPontos insuficientes"));
                learnButton.active = false;
            } else {
                learnButton.setMessage(Component.literal("§aAprender"));
                learnButton.active = true;
            }
        }
    }

    private void clearSelection() {
        selectedNode = null;
        if (learnButton != null) {
            learnButton.visible = false;
            learnButton.active = false;
        }
    }

    // =====================================================================
    // COMPRA — manda pacote pro servidor
    // =====================================================================
    private void tryLearn() {
        if (selectedNode == null)
            return;

        // Manda pro servidor. O servidor vai validar tudo (pontos,
        // pré-requisitos, classe) e devolver um PacketSyncSkillData.
        // O listener onSkillDataChanged() vai revalidar o botão.
        PacketHandler.INSTANCE.sendToServer(
                new PacketUnlockSkill(selectedNode.getId()));
    }

    // =====================================================================
    // INPUT — teclado
    // =====================================================================
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean shift = Screen.hasShiftDown();

        if (keyCode == GLFW.GLFW_KEY_EQUAL || keyCode == GLFW.GLFW_KEY_KP_ADD) {
            zoom = Math.min(ZOOM_MAX, zoom + ZOOM_STEP);
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_MINUS || keyCode == GLFW.GLFW_KEY_KP_SUBTRACT) {
            zoom = Math.max(ZOOM_MIN, zoom - ZOOM_STEP);
            return true;
        }

        if (shift) {
            switch (keyCode) {
                case GLFW.GLFW_KEY_W -> {
                    cameraY -= PAN_SPEED / (CELL_SIZE * zoom);
                    return true;
                }
                case GLFW.GLFW_KEY_S -> {
                    cameraY += PAN_SPEED / (CELL_SIZE * zoom);
                    return true;
                }
                case GLFW.GLFW_KEY_A -> {
                    cameraX -= PAN_SPEED / (CELL_SIZE * zoom);
                    return true;
                }
                case GLFW.GLFW_KEY_D -> {
                    cameraX += PAN_SPEED / (CELL_SIZE * zoom);
                    return true;
                }
            }
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE && selectedNode != null) {
            clearSelection();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    // =====================================================================
    // INPUT — mouse
    // =====================================================================
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0)
            zoom = Math.min(ZOOM_MAX, zoom + ZOOM_STEP);
        else if (delta < 0)
            zoom = Math.max(ZOOM_MIN, zoom - ZOOM_STEP);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (button == 0) {
            SkillNode clicked = getNodeAt(mouseX, mouseY);
            if (clicked != null) {
                selectNode(clicked);
                return true;
            } else {
                clearSelection();
                isPanning = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && isPanning) {
            isPanning = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isPanning && button == 0) {
            float scale = CELL_SIZE * zoom;
            cameraX -= (float) (dragX / scale);
            cameraY -= (float) (dragY / scale);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    // =====================================================================
    // HIT DETECTION
    // =====================================================================
    private SkillNode getNodeAt(double mouseX, double mouseY) {
        ClassType classType = ClientSkillData.get().getPlayerClass();
        SkillTree tree = SkillRegistry.getTree(classType);
        if (tree == null)
            return null;

        List<SkillNode> nodes = tree.getNodes();
        for (int i = nodes.size() - 1; i >= 0; i--) {
            SkillNode n = nodes.get(i);
            float sx = gridToScreenX(n.getGridX());
            float sy = gridToScreenY(n.getGridY());
            int radius = n.isCentral() ? NODE_RADIUS + 3 : NODE_RADIUS;

            double dx = mouseX - sx;
            double dy = mouseY - sy;
            if (dx * dx + dy * dy <= radius * radius) {
                return n;
            }
        }
        return null;
    }

    // =====================================================================
    // RENDER
    // =====================================================================
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);

        ClassType classType = ClientSkillData.get().getPlayerClass();
        SkillTree tree = SkillRegistry.getTree(classType);
        if (tree == null) {
            super.render(graphics, mouseX, mouseY, partialTick);
            return;
        }

        // 1. Linhas de conexão
        for (SkillNode node : tree.getNodes()) {
            for (String preId : node.getPrerequisites()) {
                SkillNode pre = tree.getNode(preId);
                if (pre == null)
                    continue;
                drawConnection(graphics, pre, node);
            }
        }

        // 2. Nós
        SkillNode hovered = getNodeAt(mouseX, mouseY);
        for (SkillNode node : tree.getNodes()) {
            boolean isSelected = (node == selectedNode);
            boolean isHovered = (node == hovered);
            drawNode(graphics, node, isSelected, isHovered);
        }

        // 3. HUD fixa
        drawHeader(graphics);
        drawLegend(graphics);
        drawSelectedPanel(graphics);

        // 4. Widgets (botões)
        super.render(graphics, mouseX, mouseY, partialTick);

        // 5. Tooltip por último
        if (hovered != null) {
            drawTooltip(graphics, hovered, mouseX, mouseY);
        }
    }

    private void drawConnection(GuiGraphics graphics, SkillNode from, SkillNode to) {
        float x1 = gridToScreenX(from.getGridX());
        float y1 = gridToScreenY(from.getGridY());
        float x2 = gridToScreenX(to.getGridX());
        float y2 = gridToScreenY(to.getGridY());

        boolean fromUnlocked = ClientSkillData.get().hasSkill(from.getId());
        boolean toUnlocked = ClientSkillData.get().hasSkill(to.getId());

        int color;
        if (toUnlocked)
            color = 0xFF55FF55;
        else if (fromUnlocked)
            color = 0xFFFFFF55;
        else
            color = 0xFF444444;

        drawLine(graphics, x1, y1, x2, y2, color);
    }

    private void drawLine(GuiGraphics graphics, float x1, float y1, float x2, float y2, int color) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len < 0.01f)
            return;
        int steps = (int) len;
        if (steps < 1)
            steps = 1;
        float stepX = dx / steps;
        float stepY = dy / steps;

        int thickness = 2;
        for (int i = 0; i <= steps; i++) {
            float px = x1 + stepX * i;
            float py = y1 + stepY * i;
            graphics.fill(
                    (int) (px - thickness / 2f), (int) (py - thickness / 2f),
                    (int) (px + thickness / 2f), (int) (py + thickness / 2f),
                    color);
        }
    }

    private void drawHeader(GuiGraphics graphics) {
        ClassType classType = ClientSkillData.get().getPlayerClass();
        int points = ClientSkillData.get().getSkillPoints();

        graphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        graphics.drawCenteredString(this.font,
                "§6" + classType.getDisplayName() + " §7| §e" + points + " pts"
                        + " §7| zoom §f" + String.format("%.1fx", zoom),
                this.width / 2, 22, 0xFFFFFF);
    }

    private void drawLegend(GuiGraphics graphics) {
        int x = 8;
        int y = this.height - 70;

        graphics.drawString(this.font, "§7Legenda:", x, y, 0xFFFFFF);
        graphics.drawString(this.font, "§c● §7Bloqueado", x, y + 12, 0xFFFFFF);
        graphics.drawString(this.font, "§e● §7Disponível", x, y + 24, 0xFFFFFF);
        graphics.drawString(this.font, "§a● §7Comprado", x, y + 36, 0xFFFFFF);
    }

    private void drawSelectedPanel(GuiGraphics graphics) {
        if (selectedNode == null)
            return;

        int panelY = this.height - 45;
        int panelX = this.width / 2 - 200;
        int panelW = 400;

        graphics.fill(panelX, panelY, panelX + panelW, panelY + 20, 0xAA000000);

        boolean unlocked = ClientSkillData.get().hasSkill(selectedNode.getId());
        String status;
        if (unlocked)
            status = "§a✔ Aprendida";
        else if (canUnlock(selectedNode))
            status = "§e○ Disponível";
        else
            status = "§c✘ Bloqueada";

        graphics.drawCenteredString(this.font,
                "§f" + selectedNode.getDisplayName()
                        + " §7| " + status
                        + " §7| §e" + selectedNode.getCost() + " pts",
                this.width / 2, panelY + 6, 0xFFFFFF);
    }

    private void drawTooltip(GuiGraphics graphics, SkillNode node, int mouseX, int mouseY) {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal("§6" + node.getDisplayName()));
        lines.add(Component.literal("§7" + node.getBranch().getDisplayName()
                + " • " + node.getType().getDisplayName()));
        lines.add(Component.literal("§f" + node.getDescription()));
        lines.add(Component.literal("§7Custo: §e" + node.getCost() + " pts"));

        boolean unlocked = ClientSkillData.get().hasSkill(node.getId());
        if (unlocked) {
            lines.add(Component.literal("§a✔ Já aprendida"));
        } else if (canUnlock(node)) {
            lines.add(Component.literal("§e○ Clique para selecionar"));
        } else {
            lines.add(Component.literal("§c✘ Pré-requisitos:"));
            for (String pre : node.getPrerequisites()) {
                boolean preUnlocked = ClientSkillData.get().hasSkill(pre);
                lines.add(Component.literal("  §7- " + pre
                        + (preUnlocked ? " §a✔" : " §c✘")));
            }
        }

        List<net.minecraft.util.FormattedCharSequence> tooltipLines = new ArrayList<>();
        for (Component c : lines) {
            tooltipLines.add(c.getVisualOrderText());
        }
        graphics.renderTooltip(this.font, tooltipLines, mouseX, mouseY);
    }

    private void drawNode(GuiGraphics graphics, SkillNode node, boolean selected, boolean hovered) {
        float screenX = gridToScreenX(node.getGridX());
        float screenY = gridToScreenY(node.getGridY());

        boolean unlocked = ClientSkillData.get().hasSkill(node.getId());
        boolean available = !unlocked && canUnlock(node);

        int color;
        if (unlocked)
            color = 0xFF55FF55;
        else if (available)
            color = 0xFFFFFF55;
        else
            color = 0xFFFF5555;

        int radius = node.isCentral() ? NODE_RADIUS + 3 : NODE_RADIUS;

        graphics.fill(
                (int) (screenX - radius - 1), (int) (screenY - radius - 1),
                (int) (screenX + radius + 1), (int) (screenY + radius + 1),
                0xFF000000);

        if (selected) {
            graphics.fill(
                    (int) (screenX - radius - 2), (int) (screenY - radius - 2),
                    (int) (screenX + radius + 2), (int) (screenY + radius + 2),
                    0xFFFFFFFF);
        } else if (hovered) {
            graphics.fill(
                    (int) (screenX - radius - 2), (int) (screenY - radius - 2),
                    (int) (screenX + radius + 2), (int) (screenY + radius + 2),
                    0xAAAAAAAA);
        }

        graphics.fill(
                (int) (screenX - radius), (int) (screenY - radius),
                (int) (screenX + radius), (int) (screenY + radius),
                color);

        if (node.isCentral()) {
            String letter = "C";
            int w = this.font.width(letter);
            graphics.drawString(this.font, letter,
                    (int) (screenX - w / 2.0f), (int) (screenY - 4),
                    0xFF000000);
        }
    }

    // =====================================================================
    // Conversão grid -> tela
    // =====================================================================
    private float gridToScreenX(float gridX) {
        return this.width / 2.0f + (gridX - cameraX) * CELL_SIZE * zoom;
    }

    private float gridToScreenY(float gridY) {
        return this.height / 2.0f + (gridY - cameraY) * CELL_SIZE * zoom;
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

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}