package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DamageAreaHandler implements ActiveSkillHandler {

    private final float baseDamage;
    private final double radius;
    private final long cooldownMs;

    public DamageAreaHandler(float baseDamage, double radius, long cooldownMs) {
        this.baseDamage = baseDamage;
        this.radius = radius;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        // Aplica bônus de dano mágico
        double magicBonus = data.getBonus(StatType.DAMAGE_MAGIC);
        float damage = (float) (baseDamage * (1.0 + magicBonus));

        AABB box = player.getBoundingBox().inflate(radius);
        List<LivingEntity> targets = player.level().getEntitiesOfClass(
                LivingEntity.class, box,
                e -> e != player && e.isAlive() && e instanceof Enemy);

        int count = 0;
        for (LivingEntity target : targets) {
            target.hurt(player.damageSources().magic(), damage);
            count++;
        }

        player.sendSystemMessage(Component.literal(
                "§5Dano em área: §f" + String.format("%.1f", damage)
                        + " §7em §f" + count + " §7inimigos."));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}