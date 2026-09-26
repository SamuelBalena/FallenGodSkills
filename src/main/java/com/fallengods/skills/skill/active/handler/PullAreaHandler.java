package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PullAreaHandler implements ActiveSkillHandler {

    private final double radius;
    private final double pullStrength; // quantos blocos puxar por tick (impulso)
    private final long cooldownMs;

    public PullAreaHandler(double radius, double pullStrength, long cooldownMs) {
        this.radius = radius;
        this.pullStrength = pullStrength;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        AABB box = player.getBoundingBox().inflate(radius);
        List<LivingEntity> targets = player.level().getEntitiesOfClass(
                LivingEntity.class, box,
                e -> e != player && e.isAlive() && e instanceof Enemy);

        Vec3 center = player.position();

        int count = 0;
        for (LivingEntity target : targets) {
            Vec3 toCenter = center.subtract(target.position()).normalize()
                    .scale(pullStrength);

            target.setDeltaMovement(toCenter.x, 0.2, toCenter.z);
            target.hurtMarked = true; // força o cliente a ver o movimento

            // Aplica lentidão por 6s
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 6 * 20, 2));

            count++;
        }

        if (count > 0) {
            player.sendSystemMessage(Component.literal(
                    "§5Campo de Gravidade §7atraiu §f" + count + " §7inimigos."));
        } else {
            player.sendSystemMessage(Component.literal("§cNenhum inimigo para atrair."));
        }
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}