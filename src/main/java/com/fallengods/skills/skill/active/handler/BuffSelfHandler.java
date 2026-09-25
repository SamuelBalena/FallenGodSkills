package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class BuffSelfHandler implements ActiveSkillHandler {

    private final MobEffect effect;
    private final int durationTicks;
    private final int amplifier; // 0 = nível I, 1 = nível II, ...
    private final long cooldownMs;

    public BuffSelfHandler(MobEffect effect, int durationTicks, int amplifier, long cooldownMs) {
        this.effect = effect;
        this.durationTicks = durationTicks;
        this.amplifier = amplifier;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        player.addEffect(new MobEffectInstance(
                effect,
                durationTicks,
                amplifier,
                false, // ambient
                true // showParticles
        ));

        player.sendSystemMessage(Component.literal(
                "§bEfeito aplicado por §f" + (durationTicks / 20) + "s"));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}