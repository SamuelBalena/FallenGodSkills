package com.fallengods.skills.skill.active;

import com.fallengods.skills.capability.PlayerSkillData;
import net.minecraft.server.level.ServerPlayer;

/**
 * Handler de uma skill ativa. Cada skill ativa tem um handler próprio,
 * registrado em ActiveSkillHandlers.
 */
public interface ActiveSkillHandler {

    /** Executa o efeito da skill. */
    void activate(ServerPlayer player, PlayerSkillData data);

    /** Cooldown em milissegundos. 0 = sem cooldown. */
    long getCooldownMs();
}