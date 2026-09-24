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

    private static final UUID MOD_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        if (!(event.player instanceof ServerPlayer player))
            return;
        if (player.tickCount % 20 != 0)
            return;

        player.getCapability(SkillDataCapability.PLAYER_SKILL_DATA).ifPresent(data -> {
            for (StatType type : StatType.values()) {
                Attribute attr = type.getVanillaAttribute();
                if (attr == null)
                    continue;

                AttributeInstance instance = player.getAttribute(attr);
                if (instance == null)
                    continue;

                double value = data.getBonus(type);

                // Remove o modifier antigo (se existir)
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

    private static AttributeModifier.Operation getOperation(StatType type) {
        switch (type) {
            case MOVEMENT_SPEED:
            case ATTACK_SPEED:
                return AttributeModifier.Operation.MULTIPLY_TOTAL;
            case KNOCKBACK_RESISTANCE:
            case ARMOR:
            case ARMOR_TOUGHNESS:
            case MAX_HEALTH:
            case ATTACK_DAMAGE:
            case LUCK:
            default:
                return AttributeModifier.Operation.ADDITION;
        }
    }
}