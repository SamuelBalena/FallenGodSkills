package com.fallengods.skills.skill.effect;

import com.fallengods.skills.classsystem.ClassType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkillEffectRegistry {

    private static final Map<String, List<StatBonus>> NODE_EFFECTS = new HashMap<>();
    private static final Map<ClassType, List<StatBonus>> CLASS_BASE = new HashMap<>();

    public static void init() {
        registerNodeEffects();
        registerClassBase();
    }

    private static void registerNodeEffects() {

        // ===== NÓS CENTRAIS =====
        add("guerreiro_central",
                StatBonus.of(StatType.DAMAGE_SWORD, 0.20),
                StatBonus.of(StatType.DAMAGE_AXE, 0.20),
                StatBonus.of(StatType.KNOCKBACK_RESISTANCE, 0.10));
        add("arqueiro_central",
                StatBonus.of(StatType.DAMAGE_BOW, 0.20),
                StatBonus.of(StatType.MOVEMENT_SPEED, 0.10));
        add("mago_central",
                StatBonus.of(StatType.DAMAGE_MAGIC, 0.20),
                StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.15));
        add("sacerdote_central",
                StatBonus.of(StatType.HEAL_POWER, 0.20),
                StatBonus.health(4));

        // ===== GUERREIRO — DEFESA =====
        add("guerreiro_def_01", StatBonus.armor(1));
        add("guerreiro_def_02", StatBonus.health(1));
        add("guerreiro_def_03", StatBonus.knockbackResist(0.03));
        add("guerreiro_def_04", StatBonus.armor(1));
        add("guerreiro_def_05", StatBonus.health(1));
        add("guerreiro_def_06", StatBonus.damageReduction(0.03));
        add("guerreiro_def_07", StatBonus.armor(1));
        add("guerreiro_def_08", StatBonus.armor(2));
        add("guerreiro_def_09", StatBonus.health(1));
        add("guerreiro_def_10", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_11", StatBonus.armor(1));
        add("guerreiro_def_12", StatBonus.damageReduction(0.03));
        add("guerreiro_def_13", StatBonus.health(1));
        add("guerreiro_def_14", StatBonus.damageReduction(0.15)); // Resistência Ancestral
        add("guerreiro_def_15", StatBonus.armor(1));
        add("guerreiro_def_16", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_17", StatBonus.health(1));
        add("guerreiro_def_18", StatBonus.damageReduction(0.03));
        // guerreiro_def_19 (Postura Defensiva) — DYNAMIC
        add("guerreiro_def_20", StatBonus.armor(1));
        add("guerreiro_def_21", StatBonus.health(1));
        add("guerreiro_def_22", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_23", StatBonus.armor(1));
        add("guerreiro_def_24", StatBonus.knockbackResist(0.20)); // Vigor Inabalável
        add("guerreiro_def_25", StatBonus.health(1));
        add("guerreiro_def_26", StatBonus.damageReduction(0.03));
        // guerreiro_def_27 (Couraça Viva) — DYNAMIC
        add("guerreiro_def_28", StatBonus.armor(1));
        add("guerreiro_def_29", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_30", StatBonus.health(1));
        add("guerreiro_def_31", StatBonus.armor(1));
        // guerreiro_def_32 (Muralha Humana) — DYNAMIC
        add("guerreiro_def_33", StatBonus.health(1));
        add("guerreiro_def_34", StatBonus.damageReduction(0.03));

        // ===== GUERREIRO — OFENSIVO =====
        add("guerreiro_ofe_01", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_02", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_03", StatBonus.health(1));
        add("guerreiro_ofe_04", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_05", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("guerreiro_ofe_06", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_07", StatBonus.of(StatType.DAMAGE_SWORD, 0.15)); // Fúria Primordial
        add("guerreiro_ofe_08", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_09", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_10", StatBonus.health(1));
        add("guerreiro_ofe_11", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        // guerreiro_ofe_12 (Golpe Devastador) — ATIVA
        add("guerreiro_ofe_13", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_14", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_15", StatBonus.health(1));
        // guerreiro_ofe_16 (Sangue de Batalha) — DYNAMIC
        add("guerreiro_ofe_17", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_18", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        add("guerreiro_ofe_19", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        // guerreiro_ofe_20 (Execução) — ATIVA
        add("guerreiro_ofe_21", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_22", StatBonus.health(1));
        add("guerreiro_ofe_23", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        // guerreiro_ofe_24 (Fúria Cega) — ATIVA
        add("guerreiro_ofe_25", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_26", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        add("guerreiro_ofe_27", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        // guerreiro_ofe_28 (Corte Profundo) — DYNAMIC
        add("guerreiro_ofe_29", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_30", StatBonus.health(1));
        // guerreiro_ofe_31 (Quebra-Ossos) — ATIVA
        add("guerreiro_ofe_32", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_33", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_34", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        // guerreiro_ofe_35 (Massacre) — ATIVA
        // guerreiro_ofe_36 (Sede de Sangue) — DYNAMIC

        // ===== GUERREIRO — UTILIDADE =====
        add("guerreiro_uti_01", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_02", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_03", StatBonus.health(1));
        add("guerreiro_uti_04", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_05", StatBonus.damageReduction(0.02));
        // guerreiro_uti_06 (Grito de Guerra) — ATIVA
        add("guerreiro_uti_07", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_08", StatBonus.health(1));
        // guerreiro_uti_09 (Impacto Sísmico) — ATIVA
        add("guerreiro_uti_10", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_11", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_12", StatBonus.health(1));
        // guerreiro_uti_13 (Provocar) — ATIVA
        add("guerreiro_uti_14", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_15", StatBonus.knockbackResist(0.03));
        // guerreiro_uti_16 (Passo Pesado) — DYNAMIC
        add("guerreiro_uti_17", StatBonus.health(1));
        add("guerreiro_uti_18", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // guerreiro_uti_19 (Barreira de Aço) — ATIVA
        add("guerreiro_uti_20", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_21", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_22", StatBonus.health(1));
        // guerreiro_uti_23 (Presença Intimidadora) — DYNAMIC
        add("guerreiro_uti_24", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_25", StatBonus.knockbackResist(0.03));
        // guerreiro_uti_26 (Investida) — ATIVA
        add("guerreiro_uti_27", StatBonus.health(1));
        add("guerreiro_uti_28", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_29", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // guerreiro_uti_30 (Último Resistente) — DYNAMIC
    }

    private static void registerClassBase() {
        // vazio — passivas base vivem nos nós centrais
    }

    public static Map<StatType, Double> recalcBonuses(
            ClassType classType,
            Set<String> unlockedNodes) {
        Map<StatType, Double> result = new EnumMap<>(StatType.class);

        List<StatBonus> base = CLASS_BASE.get(classType);
        if (base != null) {
            for (StatBonus b : base) {
                result.merge(b.getType(), b.getValue(), Double::sum);
            }
        }

        for (String nodeId : unlockedNodes) {
            List<StatBonus> effects = NODE_EFFECTS.get(nodeId);
            if (effects == null)
                continue;
            for (StatBonus b : effects) {
                result.merge(b.getType(), b.getValue(), Double::sum);
            }
        }

        return result;
    }

    private static void add(String nodeId, StatBonus... bonuses) {
        List<StatBonus> list = NODE_EFFECTS.computeIfAbsent(nodeId, k -> new ArrayList<>());
        for (StatBonus b : bonuses)
            list.add(b);
    }

    public static List<StatBonus> getNodeEffects(String nodeId) {
        return NODE_EFFECTS.getOrDefault(nodeId, Collections.emptyList());
    }

    public static List<StatBonus> getClassBase(ClassType type) {
        return CLASS_BASE.getOrDefault(type, Collections.emptyList());
    }
}