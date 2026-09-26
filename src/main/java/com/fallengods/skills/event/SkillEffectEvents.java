package com.fallengods.skills.event;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.skill.active.handler.MarkTargetHandler;
import com.fallengods.skills.skill.active.handler.MassacreHandler;
import com.fallengods.skills.skill.active.handler.NextHitBonusHandler;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillEffectEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player))
            return;

        // ===== NextHitBonus (qualquer skill ativa "next hit") =====
        CompoundTag nbt = player.getPersistentData();
        for (String key : nbt.getAllKeys()) {
            if (!key.startsWith(NextHitBonusHandler.NBT_PREFIX))
                continue;
            if (key.endsWith("_mult"))
                continue;
            if (!nbt.getBoolean(key))
                continue;

            float mult = nbt.getFloat(key + "_mult");
            event.setAmount(event.getAmount() * mult);
            nbt.remove(key);
            nbt.remove(key + "_mult");

            player.sendSystemMessage(Component.literal(
                    "§6Bônus de ataque consumido! §7(x" + String.format("%.1f", mult) + ")"));
            break;
        }

        // ===== Alvo marcado recebe dano extra =====
        LivingEntity target = event.getEntity();
        long markUntil = target.getPersistentData().getLong(MarkTargetHandler.NBT_MARK_UNTIL);
        if (markUntil > 0 && System.currentTimeMillis() < markUntil) {
            float mult = target.getPersistentData().getFloat(MarkTargetHandler.NBT_MARK_MULT);
            if (mult > 1.0f) {
                event.setAmount(event.getAmount() * mult);
            }
        } else if (markUntil > 0) {
            // Marca expirou, limpa
            target.getPersistentData().remove(MarkTargetHandler.NBT_MARK_UNTIL);
            target.getPersistentData().remove(MarkTargetHandler.NBT_MARK_MULT);
        }

        // ===== Bônus de dano por stat =====
        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            float bonus = 0f;

            boolean isMelee = event.getSource().getDirectEntity() == player;
            if (isMelee) {
                bonus += (float) (event.getAmount() * data.getBonus(StatType.DAMAGE_SWORD));
            }

            boolean isArrow = event.getSource().getDirectEntity() instanceof AbstractArrow;
            if (isArrow) {
                bonus += (float) (event.getAmount() * data.getBonus(StatType.DAMAGE_BOW));
            }

            if (event.getSource().getMsgId().equals("indirectMagic")
                    || event.getSource().getMsgId().equals("magic")) {
                bonus += (float) (event.getAmount() * data.getBonus(StatType.DAMAGE_MAGIC));
            }

            if (bonus > 0f) {
                event.setAmount(event.getAmount() + bonus);
            }
        });
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            double healPower = data.getBonus(StatType.HEAL_POWER);
            if (healPower > 0) {
                event.setAmount((float) (event.getAmount() * (1.0 + healPower)));
            }
        });
    }

    // =====================================================================
    // Massacre: cada kill dentro da janela reduz cooldowns
    // =====================================================================
    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player))
            return;
        if (player.level().isClientSide())
            return;

        long until = player.getPersistentData().getLong(MassacreHandler.NBT_WINDOW_UNTIL);
        if (until <= 0 || System.currentTimeMillis() > until)
            return;

        // Reduz 2s de todos os cooldowns
        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            long now = System.currentTimeMillis();
            long reductionMs = 2_000L;

            for (Map.Entry<String, Long> entry : data.getCooldowns().entrySet()) {
                long end = entry.getValue();
                if (end > now) {
                    long newEnd = Math.max(now, end - reductionMs);
                    entry.setValue(newEnd);
                }
            }

            player.sendSystemMessage(Component.literal(
                    "§4Massacre §7— cooldowns reduzidos em 2s!"));
        });
    }
}