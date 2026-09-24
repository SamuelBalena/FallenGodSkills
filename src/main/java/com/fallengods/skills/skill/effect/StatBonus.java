package com.fallengods.skills.skill.effect;

public class StatBonus {
    private final StatType type;
    private final double value;

    public StatBonus(StatType type, double value) {
        this.type = type;
        this.value = value;
    }

    public StatType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    // ===== Helpers =====
    public static StatBonus of(StatType type, double value) {
        return new StatBonus(type, value);
    }

    public static StatBonus armor(double v) {
        return of(StatType.ARMOR, v);
    }

    public static StatBonus health(double v) {
        return of(StatType.MAX_HEALTH, v);
    }

    public static StatBonus knockbackResist(double v) {
        return of(StatType.KNOCKBACK_RESIST, v);
    }

    public static StatBonus damageReduction(double v) {
        return of(StatType.DAMAGE_REDUCTION, v);
    }
}