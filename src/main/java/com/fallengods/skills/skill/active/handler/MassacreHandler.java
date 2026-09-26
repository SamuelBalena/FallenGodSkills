package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class MassacreHandler implements ActiveSkillHandler {

    public static final String NBT_WINDOW_UNTIL = "fgs_massacre_until";

    private final long windowMs;
    private final long cooldownMs;

    public MassacreHandler(long windowMs, long cooldownMs) {
        this.windowMs = windowMs;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        long until = System.currentTimeMillis() + windowMs;
        player.getPersistentData().putLong(NBT_WINDOW_UNTIL, until);

        player.sendSystemMessage(Component.literal(
                "§4Massacre §7ativo por §f" + (windowMs / 1000) + "s§7!"));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}