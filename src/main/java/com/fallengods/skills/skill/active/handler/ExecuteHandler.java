package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;

public class ExecuteHandler implements ActiveSkillHandler {

    private final float executeDamage;
    private final float normalDamage;
    private final float hpThreshold; // 0.25 = 25% de HP
    private final double radius;
    private final long cooldownMs;

    public ExecuteHandler(float executeDamage, float normalDamage,
            float hpThreshold, double radius, long cooldownMs) {
        this.executeDamage = executeDamage;
        this.normalDamage = normalDamage;
        this.hpThreshold = hpThreshold;
        this.radius = radius;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        double magicBonus = data.getBonus(StatType.DAMAGE_MAGIC);

        AABB box = player.getBoundingBox().inflate(radius);
        List<LivingEntity> targets = player.level().getEntitiesOfClass(
                LivingEntity.class, box,
                e -> e != player && e.isAlive() && e instanceof Enemy);

        if (targets.isEmpty()) {
            player.sendSystemMessage(Component.literal("§cNenhum inimigo próximo."));
            return;
        }

        // Pega o alvo mais próximo
        LivingEntity target = targets.stream()
                .min(Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                .orElse(null);

        if (target == null)
            return;

        float hpRatio = target.getHealth() / target.getMaxHealth();
        boolean canExecute = hpRatio <= hpThreshold;

        float damage = (canExecute ? executeDamage : normalDamage) * (float) (1.0 + magicBonus);

        target.hurt(player.damageSources().magic(), damage);

        if (canExecute) {
            player.sendSystemMessage(Component.literal(
                    "§4EXECUÇÃO! §f" + String.format("%.1f", damage) + " §4de dano!"));
        } else {
            player.sendSystemMessage(Component.literal(
                    "§7Alvo acima de §f" + (int) (hpThreshold * 100)
                            + "% §7de vida — dano normal: §f"
                            + String.format("%.1f", damage)));
        }
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}