package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class NextHitBonusHandler implements ActiveSkillHandler {

    /** Nome da tag NBT. Cada skill tem a sua pra não confundir. */
    public static final String NBT_PREFIX = "fgs_next_hit_";

    private final String skillId;
    private final float multiplier; // 1.5 = +50%
    private final String displayName;
    private final long cooldownMs;

    public NextHitBonusHandler(String skillId, float multiplier,
            String displayName, long cooldownMs) {
        this.skillId = skillId;
        this.multiplier = multiplier;
        this.displayName = displayName;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        player.getPersistentData().putBoolean(NBT_PREFIX + skillId, true);
        player.getPersistentData().putFloat(NBT_PREFIX + skillId + "_mult", multiplier);

        player.sendSystemMessage(Component.literal(
                "§6" + displayName + " §7ativado — próximo ataque com §f"
                        + String.format("%.0f", (multiplier - 1) * 100) + "% §7de dano extra."));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}