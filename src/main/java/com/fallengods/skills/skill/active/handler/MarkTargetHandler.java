package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;

public class MarkTargetHandler implements ActiveSkillHandler {

    public static final String NBT_MARK_UNTIL = "fgs_marked_until";
    public static final String NBT_MARK_MULT = "fgs_marked_mult";

    private final float damageMultiplier; // 1.20 = +20% dano recebido
    private final double radius;
    private final long durationMs;
    private final long cooldownMs;

    public MarkTargetHandler(float damageMultiplier, double radius,
            long durationMs, long cooldownMs) {
        this.damageMultiplier = damageMultiplier;
        this.radius = radius;
        this.durationMs = durationMs;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        AABB box = player.getBoundingBox().inflate(radius);
        List<LivingEntity> targets = player.level().getEntitiesOfClass(
                LivingEntity.class, box,
                e -> e != player && e.isAlive() && e instanceof Enemy);

        if (targets.isEmpty()) {
            player.sendSystemMessage(Component.literal("§cNenhum inimigo próximo para marcar."));
            return;
        }

        LivingEntity target = targets.stream()
                .min(Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                .orElse(null);
        if (target == null)
            return;

        long until = System.currentTimeMillis() + durationMs;
        target.getPersistentData().putLong(NBT_MARK_UNTIL, until);
        target.getPersistentData().putFloat(NBT_MARK_MULT, damageMultiplier);

        player.sendSystemMessage(Component.literal(
                "§6Alvo marcado §7— receberá §f+"
                        + (int) ((damageMultiplier - 1) * 100) + "% §7de dano por "
                        + (durationMs / 1000) + "s."));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}