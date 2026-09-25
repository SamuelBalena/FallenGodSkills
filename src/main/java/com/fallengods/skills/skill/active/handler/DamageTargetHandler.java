package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DamageTargetHandler implements ActiveSkillHandler {

    private final float baseDamage;
    private final double range;
    private final long cooldownMs;

    public DamageTargetHandler(float baseDamage, double range, long cooldownMs) {
        this.baseDamage = baseDamage;
        this.range = range;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        double magicBonus = data.getBonus(StatType.DAMAGE_MAGIC);
        float damage = (float) (baseDamage * (1.0 + magicBonus));

        // Raycast a partir dos olhos do jogador
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 end = eye.add(look.scale(range));

        HitResult hit = player.level().clip(new net.minecraft.world.level.ClipContext(
                eye, end,
                net.minecraft.world.level.ClipContext.Block.COLLIDER,
                net.minecraft.world.level.ClipContext.Fluid.NONE,
                player));

        // Também checa entidades no caminho
        LivingEntity target = null;
        double closestDist = Double.MAX_VALUE;

        Vec3 current = eye;
        for (double d = 0; d <= range; d += 0.5) {
            Vec3 point = eye.add(look.scale(d));
            for (Entity e : player.level().getEntities(player,
                    new net.minecraft.world.phys.AABB(point.subtract(1, 1, 1), point.add(1, 1, 1)))) {
                if (e instanceof LivingEntity le && le != player && le.isAlive()
                        && le instanceof Enemy) {
                    double dist = le.distanceToSqr(player);
                    if (dist < closestDist) {
                        closestDist = dist;
                        target = le;
                    }
                }
            }
            if (target != null)
                break;
        }

        if (target == null) {
            player.sendSystemMessage(Component.literal("§cNenhum alvo à frente."));
            return;
        }

        target.hurt(player.damageSources().magic(), damage);
        player.sendSystemMessage(Component.literal(
                "§5Dano em alvo: §f" + String.format("%.1f", damage)));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}