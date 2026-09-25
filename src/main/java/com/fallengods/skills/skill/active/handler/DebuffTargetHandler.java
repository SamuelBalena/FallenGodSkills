package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DebuffTargetHandler implements ActiveSkillHandler {

    private final MobEffect effect;
    private final int durationTicks;
    private final int amplifier;
    private final double radius;
    private final long cooldownMs;

    public DebuffTargetHandler(MobEffect effect, int durationTicks, int amplifier,
            double radius, long cooldownMs) {
        this.effect = effect;
        this.durationTicks = durationTicks;
        this.amplifier = amplifier;
        this.radius = radius;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        AABB box = player.getBoundingBox().inflate(radius);
        List<LivingEntity> targets = player.level().getEntitiesOfClass(
                LivingEntity.class, box,
                e -> e != player && e.isAlive() && e instanceof Enemy);

        if (targets.isEmpty()) {
            player.sendSystemMessage(Component.literal("§cNenhum inimigo próximo."));
            return;
        }

        int count = 0;
        for (LivingEntity target : targets) {
            target.addEffect(new MobEffectInstance(effect, durationTicks, amplifier));
            count++;
        }

        player.sendSystemMessage(Component.literal(
                "§5Debuff aplicado em §f" + count + " §5inimigos."));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}