package com.fallengods.skills.event;

import com.fallengods.skills.FallenGodsSkills;
import com.fallengods.skills.capability.SkillDataCapability;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttributeBonusHandler {

    /** UUID fixo do mod — usado para identificar os modifiers que NÓS aplicamos. */
    private static final UUID MOD_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    /** Roda a cada 20 ticks (1 segundo) para reaplicar os modifiers. */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        if (!(event.player instanceof ServerPlayer player))
            return;
        if (player.tickCount % 20 != 0)
            return; // 1x por segundo

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            for (StatType type : StatType.values()) {
                Attribute attr = type.getVanillaAttribute();
                if (attr == null)
                    continue; // bônus do mod, não tem atributo

                AttributeInstance instance = player.getAttribute(attr);
                if (instance == null)
                    continue;

                double value = data.getBonus(type);

                // Remove o modifier antigo (mesmo UUID)
                instance.removeModifier(MOD_UUID);

                // Se tem valor, re-adiciona
                if (value != 0.0) {
                    AttributeModifier.Operation op = getOperation(type);
                    AttributeModifier modifier = new AttributeModifier(
                            MOD_UUID,
                            "fallengods_skill_bonus_" + type.getId(),
                            value,
                            op);
                    instance.addPermanentModifier(modifier);
                }
            }
        });
    }

    /**
     * Define se o bônus é aditivo (+N) ou multiplicativo (+N%).
     * Por padrão, valores < 1.0 são multiplicativos (ex: 0.10 = +10%),
     * e valores >= 1.0 são aditivos (ex: +2 de armadura).
     */
    private static AttributeModifier.Operation getOperation(StatType type) {
        switch (type) {
            case ARMOR:
            case ARMOR_TOUGHNESS:
            case MAX_HEALTH:
            case ATTACK_DAMAGE:
            case LUCK:
                return AttributeModifier.Operation.ADDITION;
            case MOVEMENT_SPEED:
            case ATTACK_SPEED:
                return AttributeModifier.Operation.MULTIPLY_TOTAL;
            case KNOCKBACK_RESISTANCE:
                // Knockback resistance vanilla é 0..1 (0% a 100% de resistência).
                // ADDITION é o que faz +0.13 virar 0.13 de resistência real.
                return AttributeModifier.Operation.ADDITION;
            default:
                return AttributeModifier.Operation.ADDITION;
        }
    }
}