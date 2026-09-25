package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class HealAreaHandler implements ActiveSkillHandler {

    private final float baseHealAmount;
    private final double radius;
    private final long cooldownMs;

    public HealAreaHandler(float baseHealAmount, double radius, long cooldownMs) {
        this.baseHealAmount = baseHealAmount;
        this.radius = radius;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        double healPower = data.getBonus(StatType.HEAL_POWER);
        float amount = (float) (baseHealAmount * (1.0 + healPower));

        // Cura a si
        player.heal(amount);

        // Cura aliados no raio
        AABB box = player.getBoundingBox().inflate(radius);
        List<Player> allies = player.level().getEntitiesOfClass(Player.class, box,
                p -> p != player && p.isAlive());

        int count = 0;
        for (Player ally : allies) {
            ally.heal(amount);
            count++;
        }

        player.sendSystemMessage(Component.literal(
                "§aCura em área: §f" + String.format("%.1f", amount / 2)
                        + " §acorações §7(" + count + " aliados)"));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}