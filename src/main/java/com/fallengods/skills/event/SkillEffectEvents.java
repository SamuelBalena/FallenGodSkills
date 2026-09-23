package com.fallengods.skills.event;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.capability.SkillDataCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillEffectEvents {

    // ============ DANO EXTRA ============
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player))
            return;

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            float bonus = 0f;

            // Guerreiro: +1 de dano por "Força Bruta"
            if (data.hasSkill("guerreiro_forca")) {
                bonus += 1.0f;
            }

            // Arqueiro: +1 de dano com arco se "Precisão"
            boolean isArrow = event.getSource().getDirectEntity() instanceof AbstractArrow;
            if (isArrow && data.hasSkill("arqueiro_precisao")) {
                bonus += 1.0f;
            }

            // Mago: +1 de dano mágico se "Potência Arcana"
            // (detecta por source de magia — usa INDIRECT_MAGIC como aproximação)
            if (event.getSource().getMsgId().equals("indirectMagic")
                    && data.hasSkill("mago_potencia")) {
                bonus += 1.0f;
            }

            if (bonus > 0f) {
                event.setAmount(event.getAmount() + bonus);
            }
        });
    }

    // ============ CURA EXTRA ============
    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            // Sacerdote: +1 de cura por "Fé"
            if (data.hasSkill("sacerdote_fe")) {
                event.setAmount(event.getAmount() + 1.0f);
            }
        });
    }
}