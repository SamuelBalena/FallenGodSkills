package com.fallengods.skills.event;

import com.fallengods.skills.FallenGodsSkills;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Efeitos passivos das skills.
 * TODO: reimplementar na sub-fase 5.4 usando os novos ids do SkillNode.
 */
@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillEffectEvents {

    // Efeitos serão reimplementados na sub-fase 5.4.
    // Os ids antigos ("guerreiro_forca", etc.) não existem mais.
    // Novos ids são do tipo "guerreiro_def_08", "guerreiro_ofe_15", etc.
}