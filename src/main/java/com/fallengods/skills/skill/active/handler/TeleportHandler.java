package com.fallengods.skills.skill.active.handler;

import com.fallengods.skills.capability.PlayerSkillData;
import com.fallengods.skills.skill.active.ActiveSkillHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class TeleportHandler implements ActiveSkillHandler {

    private final double distance;
    private final long cooldownMs;

    public TeleportHandler(double distance, long cooldownMs) {
        this.distance = distance;
        this.cooldownMs = cooldownMs;
    }

    @Override
    public void activate(ServerPlayer player, PlayerSkillData data) {
        Vec3 look = player.getLookAngle();
        Vec3 from = player.position();
        Vec3 to = from.add(look.x * distance, 0, look.z * distance);

        // Raycast pra não atravessar parede (checa bloco)
        net.minecraft.world.phys.HitResult hit = player.level().clip(
                new net.minecraft.world.level.ClipContext(
                        player.getEyePosition(),
                        player.getEyePosition().add(look.scale(distance)),
                        net.minecraft.world.level.ClipContext.Block.COLLIDER,
                        net.minecraft.world.level.ClipContext.Fluid.NONE,
                        player));

        Vec3 finalPos;
        if (hit.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
            // Teleporta até a parede
            finalPos = hit.getLocation();
        } else {
            finalPos = to;
        }

        player.teleportTo(finalPos.x, finalPos.y, finalPos.z);
        player.sendSystemMessage(Component.literal("§dTeleporte §7realizado."));
    }

    @Override
    public long getCooldownMs() {
        return cooldownMs;
    }
}