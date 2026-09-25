package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class HealSelfHandler implements ActiveSkillHandler {

    private final float baseHealAmount; // em HP (1 coração = 2 HP)
    private final long cooldownMs;

    public HealSelfHandler(float baseHealAmount, long cooldownMs) {
        this.baseHealAmount = baseHealAmount;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        double healPower = data.getBonus(StatType.HEAL_POWER);
        float amount = (float) (baseHealAmount * (1.0 + healPower));

        player.heal(amount);

        player.sendSystemMessage(Component.literal(
                "§aCurou §f" + String.format("%.1f", amount / 2) + " §acorações."));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}