package com.fallengods.skills.client.gui;

import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.client.ClientSkillData;
import com.fallengods.skills.network.PacketHandler;
import com.fallengods.skills.network.PacketUnlockSkill;
import com.fallengods.skills.skill.Skill;
import com.fallengods.skills.skill.SkillRegistry;
import com.fallengods.skills.skill.SkillTree;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SkillTreeScreen extends Screen {

    public SkillTreeScreen() {
        super(Component.literal("Árvore de Habilidades"));
    }

    @Override
    protected void init() {
        buildButtons();

        // Escuta quando o ClientSkillData for atualizado pelo servidor.
        // O rebuild vai rodar DEPOIS do processamento do clique, num momento seguro.
        ClientSkillData.setChangeListener(this::rebuildSafely);
    }

    @Override
    public void removed() {
        // Quando a tela fecha, remove o listener pra não vazar.
        ClientSkillData.clearChangeListener();
        super.removed();
    }

    /**
     * Reconstrói os botões no próximo tick, não no meio do render.
     * Chamar clearWidgets() direto aqui ainda estaria dentro do processamento
     * do pacote, mas como enqueueWork só roda no tick do servidor/cliente,
     * já é seguro o suficiente. Por segurança, fazemos via setScreen em branco
     * não — fazemos rebuild direto, porque estamos fora do render().
     */
    private void rebuildSafely() {
        // Só reconstruímos se a tela ainda está aberta
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

        List<Skill> skills = tree.getSkills();
        int centerX = this.width / 2;
        int startY = 50;
        int spacing = 30;

        for (int i = 0; i < skills.size(); i++) {
            final Skill skill = skills.get(i);
            boolean unlocked = ClientSkillData.get().hasSkill(skill.getId());
            int points = ClientSkillData.get().getSkillPoints();
            boolean canAfford = points >= skill.getCost();

            String label;
            if (unlocked) {
                label = "§a✔ " + skill.getDisplayName() + " §7(já desbloqueada)";
            } else {
                label = "§e" + skill.getDisplayName() + " §7- §f" + skill.getCost()
                        + " pts §7- §f" + skill.getDescription();
            }

            Button btn = Button.builder(
                    Component.literal(label),
                    b -> tryUnlock(skill)).bounds(centerX - 150, startY + i * spacing, 300, 20).build();

            btn.active = !unlocked && canAfford;
            this.addRenderableWidget(btn);
        }

        this.addRenderableWidget(Button.builder(
                Component.literal("Fechar"),
                b -> this.onClose()).bounds(centerX - 40, startY + skills.size() * spacing + 10, 80, 20).build());
    }

    private void tryUnlock(Skill skill) {
        PacketHandler.INSTANCE.sendToServer(new PacketUnlockSkill(skill.getId()));
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

        super.render(graphics, mouseX, mouseY, partialTick);
    }
}