package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;

public class QuebraOssosHandler implements ActiveSkillHandler {

    private static final UUID ARMOR_DEBUFF_UUID = UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f23456789012");

    private final double radius;
    private final int durationTicks;
    private final long cooldownMs;

    public QuebraOssosHandler(double radius, int durationTicks, long cooldownMs) {
        this.radius = radius;
        this.durationTicks = durationTicks;
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
            AttributeInstance armor = target.getAttribute(Attributes.ARMOR);
            if (armor == null)
                continue;

            // Remove modificador antigo se houver
            armor.removeModifier(ARMOR_DEBUFF_UUID);

            // Aplica -30% de armadura
            double currentArmor = armor.getValue();
            double reduction = -currentArmor * 0.30;

            AttributeModifier modifier = new AttributeModifier(
                    ARMOR_DEBUFF_UUID,
                    "fgs_quebra_ossos",
                    reduction,
                    AttributeModifier.Operation.ADDITION);
            armor.addTransientModifier(modifier);

            count++;
        }

        player.sendSystemMessage(Component.literal(
                "§6Quebra-Ossos §7aplicado em §f" + count + " §7inimigos."));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}