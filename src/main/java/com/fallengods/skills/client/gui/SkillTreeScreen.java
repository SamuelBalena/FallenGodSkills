package com.fallengods.skills.client.gui;

import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.client.ClientSkillData;
import com.fallengods.skills.skill.SkillNode;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class SkillTreeScreen extends Screen {

    // ===== Constantes de layout =====
    private static final int CELL_SIZE = 24; // pixels por unidade de grid
    private static final int NODE_RADIUS = 8; // raio do círculo
    private static final float ZOOM_MIN = 0.5f;
    private static final float ZOOM_MAX = 2.0f;
    private static final float ZOOM_STEP = 0.1f;
    private static final float PAN_SPEED = 8.0f; // pixels por frame quando Shift+WASD

    // ===== Estado da câmera =====
    private float cameraX = 0f; // posição do "centro da tela" em coordenadas de grid
    private float cameraY = 0f;
    private float zoom = 1.0f;

    // ===== Estado de input =====
    private boolean isPanning = false;

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

        // Centraliza a câmera no nó central da classe
        SkillTree tree = SkillRegistry.getTree(classType);
        if (tree != null) {
            SkillNode central = findCentral(tree);
            if (central != null) {
                cameraX = central.getGridX();
                cameraY = central.getGridY();
            }
        } else {
            cameraX = 0;
            cameraY = 0;
        }
        zoom = 1.0f;

        // Botão "Centralizar"
        this.addRenderableWidget(Button.builder(
                Component.literal("Centralizar"),
                b -> recenter()).bounds(this.width - 110, this.height - 30, 100, 20).build());
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
    // INPUT — teclado
    // =====================================================================
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean shift = Screen.hasShiftDown();

        // Zoom com + e -
        if (keyCode == GLFW.GLFW_KEY_EQUAL || keyCode == GLFW.GLFW_KEY_KP_ADD) {
            zoom = Math.min(ZOOM_MAX, zoom + ZOOM_STEP);
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_MINUS || keyCode == GLFW.GLFW_KEY_KP_SUBTRACT) {
            zoom = Math.max(ZOOM_MIN, zoom - ZOOM_STEP);
            return true;
        }

        // Pan com Shift + WASD
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
        // ===== CORRIGIDO =====
        // super.mouseClicked primeiro: se um widget (botão) consumir,
        // ele retorna true e a gente NÃO começa o pan.
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        // Se chegou aqui, nenhum widget pegou. Começa o pan.
        if (button == 0) {
            isPanning = true;
            return true;
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
            // Converte o delta de pixels pra coordenadas de grid
            float scale = CELL_SIZE * zoom;
            cameraX -= (float) (dragX / scale);
            cameraY -= (float) (dragY / scale);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    // =====================================================================
    // RENDER
    // =====================================================================
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Fundo escuro
        this.renderBackground(graphics);

        ClassType classType = ClientSkillData.get().getPlayerClass();
        SkillTree tree = SkillRegistry.getTree(classType);
        if (tree == null) {
            super.render(graphics, mouseX, mouseY, partialTick);
            return;
        }

        // ===== Renderiza os nós =====
        for (SkillNode node : tree.getNodes()) {
            drawNode(graphics, node);
        }

        // ===== HUD fixa (não é afetada por pan/zoom) =====
        drawHeader(graphics);
        drawLegend(graphics);

        // ===== Widgets (botões) por cima =====
        super.render(graphics, mouseX, mouseY, partialTick);
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
        int y = this.height - 50;

        graphics.drawString(this.font, "§7Legenda:", x, y, 0xFFFFFF);
        graphics.drawString(this.font, "§c● §7Bloqueado", x, y + 12, 0xFFFFFF);
        graphics.drawString(this.font, "§e● §7Disponível", x, y + 24, 0xFFFFFF);
        graphics.drawString(this.font, "§a● §7Comprado", x, y + 36, 0xFFFFFF);
    }

    private void drawNode(GuiGraphics graphics, SkillNode node) {
        // Coordenada do nó na tela
        float screenX = gridToScreenX(node.getGridX());
        float screenY = gridToScreenY(node.getGridY());

        // Cor conforme o estado
        boolean unlocked = ClientSkillData.get().hasSkill(node.getId());
        boolean available = !unlocked && canUnlock(node);

        int color;
        if (unlocked)
            color = 0xFF55FF55; // verde
        else if (available)
            color = 0xFFFFFF55; // amarelo
        else
            color = 0xFFFF5555; // vermelho

        // Nó central é maior
        int radius = node.isCentral() ? NODE_RADIUS + 3 : NODE_RADIUS;

        // Contorno preto (borda)
        graphics.fill(
                (int) (screenX - radius - 1), (int) (screenY - radius - 1),
                (int) (screenX + radius + 1), (int) (screenY + radius + 1),
                0xFF000000);

        // Círculo (na real, um quadrado com o miolo da cor — limitado pela API do
        // GuiGraphics)
        graphics.fill(
                (int) (screenX - radius), (int) (screenY - radius),
                (int) (screenX + radius), (int) (screenY + radius),
                color);

        // Se for o nó central, escreve um "C" no meio
        if (node.isCentral()) {
            String letter = "C";
            int w = this.font.width(letter);
            graphics.drawString(this.font, letter,
                    (int) (screenX - w / 2.0f), (int) (screenY - 4),
                    0xFF000000);
        }
    }

    // =====================================================================
    // CONVERSÃO grid -> tela
    // =====================================================================
    private float gridToScreenX(float gridX) {
        float centerX = this.width / 2.0f;
        return centerX + (gridX - cameraX) * CELL_SIZE * zoom;
    }

    private float gridToScreenY(float gridY) {
        float centerY = this.height / 2.0f;
        return centerY + (gridY - cameraY) * CELL_SIZE * zoom;
    }

    // =====================================================================
    // HELPERS
    // =====================================================================
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