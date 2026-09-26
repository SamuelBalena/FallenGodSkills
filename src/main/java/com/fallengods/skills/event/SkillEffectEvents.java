package com.fallengods.skills.event;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillEffectEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player))
            return;

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
}