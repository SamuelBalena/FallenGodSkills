package com.fallengods.skills.client.gui;

import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketSelectClass;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClassSelectionScreen extends Screen {
    public ClassSelectionScreen() {
        super(Component.literal("Escolha sua Classe"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 40;

        ClassType[] classes = {
                ClassType.GUERREIRO, ClassType.ARQUEIRO,
                ClassType.MAGO, ClassType.SACERDOTE
        };

        for (int i = 0; i < classes.length; i++) {
            final ClassType type = classes[i];
            this.addRenderableWidget(Button.builder(
                    Component.literal(type.getDisplayName()),
                    btn -> selectClass(type)).bounds(centerX - 100, startY + i * 25, 200, 20).build());
        }
    }

    private void selectClass(ClassType type) {
        PacketHandler.INSTANCE.sendToServer(new PacketSelectClass(type));
        this.onClose();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - 60, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}