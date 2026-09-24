package com.fallengods.skills.skill.effect;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

public enum StatType {

    // ===== Atributos vanilla =====
    ARMOR("armor", Attributes.ARMOR),
    ARMOR_TOUGHNESS("armor_toughness", Attributes.ARMOR_TOUGHNESS),
    MAX_HEALTH("max_health", Attributes.MAX_HEALTH),
    MOVEMENT_SPEED("movement_speed", Attributes.MOVEMENT_SPEED),
    ATTACK_DAMAGE("attack_damage", Attributes.ATTACK_DAMAGE),
    ATTACK_SPEED("attack_speed", Attributes.ATTACK_SPEED),
    KNOCKBACK_RESISTANCE("knockback_resistance", Attributes.KNOCKBACK_RESISTANCE),
    LUCK("luck", Attributes.LUCK),

    // ===== Bônus do mod (sem atributo vanilla) =====
    DAMAGE_REDUCTION("damage_reduction", null),
    DAMAGE_SWORD("damage_sword", null),
    DAMAGE_AXE("damage_axe", null),
    DAMAGE_BOW("damage_bow", null),
    DAMAGE_MAGIC("damage_magic", null),
    HEAL_POWER("heal_power", null),
    COOLDOWN_REDUCTION("cooldown_reduction", null),
    CRIT_CHANCE("crit_chance", null),
    CRIT_DAMAGE("crit_damage", null);

    private final String id;
    private final Attribute vanillaAttribute;

    StatType(String id, Attribute vanillaAttribute) {
        this.id = id;
        this.vanillaAttribute = vanillaAttribute;
    }

    public String getId() {
        return id;
    }

    public Attribute getVanillaAttribute() {
        return vanillaAttribute;
    }

    public boolean isVanillaAttribute() {
        return vanillaAttribute != null;
    }

    public static StatType byId(String id) {
        for (StatType t : values()) {
            if (t.id.equals(id))
                return t;
        }
        return null;
    }
}