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
        add("guerreiro_def_14", StatBonus.damageReduction(0.15));
        add("guerreiro_def_15", StatBonus.armor(1));
        add("guerreiro_def_16", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_17", StatBonus.health(1));
        add("guerreiro_def_18", StatBonus.damageReduction(0.03));
        // def_19 DYNAMIC
        add("guerreiro_def_20", StatBonus.armor(1));
        add("guerreiro_def_21", StatBonus.health(1));
        add("guerreiro_def_22", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_23", StatBonus.armor(1));
        add("guerreiro_def_24", StatBonus.knockbackResist(0.20));
        add("guerreiro_def_25", StatBonus.health(1));
        add("guerreiro_def_26", StatBonus.damageReduction(0.03));
        // def_27 DYNAMIC
        add("guerreiro_def_28", StatBonus.armor(1));
        add("guerreiro_def_29", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_30", StatBonus.health(1));
        add("guerreiro_def_31", StatBonus.armor(1));
        // def_32 DYNAMIC
        add("guerreiro_def_33", StatBonus.health(1));
        add("guerreiro_def_34", StatBonus.damageReduction(0.03));

        // ===== GUERREIRO — OFENSIVO =====
        add("guerreiro_ofe_01", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_02", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_03", StatBonus.health(1));
        add("guerreiro_ofe_04", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_05", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("guerreiro_ofe_06", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_07", StatBonus.of(StatType.DAMAGE_SWORD, 0.15));
        add("guerreiro_ofe_08", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_09", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_10", StatBonus.health(1));
        add("guerreiro_ofe_11", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        add("guerreiro_ofe_13", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_14", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_15", StatBonus.health(1));
        // ofe_16 DYNAMIC
        add("guerreiro_ofe_17", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_18", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        add("guerreiro_ofe_19", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_21", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_22", StatBonus.health(1));
        add("guerreiro_ofe_23", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_25", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_26", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        add("guerreiro_ofe_27", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        // ofe_28 DYNAMIC
        add("guerreiro_ofe_29", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_30", StatBonus.health(1));
        add("guerreiro_ofe_32", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_33", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_34", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        // ofe_35 ATIVA, ofe_36 DYNAMIC

        // ===== GUERREIRO — UTILIDADE =====
        add("guerreiro_uti_01", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_02", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_03", StatBonus.health(1));
        add("guerreiro_uti_04", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_05", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_07", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_08", StatBonus.health(1));
        add("guerreiro_uti_10", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_11", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_12", StatBonus.health(1));
        add("guerreiro_uti_14", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_15", StatBonus.knockbackResist(0.03));
        // uti_16 DYNAMIC
        add("guerreiro_uti_17", StatBonus.health(1));
        add("guerreiro_uti_18", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_20", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_21", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_22", StatBonus.health(1));
        // uti_23 DYNAMIC
        add("guerreiro_uti_24", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_25", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_27", StatBonus.health(1));
        add("guerreiro_uti_28", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_29", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // uti_30 DYNAMIC

        // ===== ARQUEIRO — PRECISÃO (→ OFENSIVO) =====
        add("arqueiro_pre_01", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_02", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_03", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_04", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_05", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_06", StatBonus.health(1));
        add("arqueiro_pre_07", StatBonus.of(StatType.DAMAGE_BOW, 0.20)); // Olho de Águia
        add("arqueiro_pre_08", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_09", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_10", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_11", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_13", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_14", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_15", StatBonus.of(StatType.CRIT_CHANCE, 0.15)); // Precisão Letal
        add("arqueiro_pre_16", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_17", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_18", StatBonus.health(1));
        add("arqueiro_pre_19", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        // pre_20 Flecha Perfurante — DYNAMIC (ignora armadura)
        add("arqueiro_pre_21", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_22", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        // pre_23 Tiro na Cabeça — DYNAMIC (só crítico)
        add("arqueiro_pre_24", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_25", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_27", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_28", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_29", StatBonus.health(1));
        // pre_30 Olho de Falcão — DYNAMIC (alcance)
        add("arqueiro_pre_31", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_32", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        // pre_33 Morte Silenciosa — DYNAMIC
        add("arqueiro_pre_34", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_35", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        // pre_36 Tiro Fatal — ATIVA

        // ===== ARQUEIRO — MOBILIDADE (→ UTILIDADE) =====
        add("arqueiro_mob_01", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // mob_02 queda — DYNAMIC (atributo não tem equivalente vanilla simples)
        add("arqueiro_mob_03", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_04", StatBonus.knockbackResist(0.02));
        add("arqueiro_mob_05", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_06", StatBonus.of(StatType.MOVEMENT_SPEED, 0.10)); // Passo Sombrio
        add("arqueiro_mob_07", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // mob_08 queda — DYNAMIC
        add("arqueiro_mob_09", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // mob_10 Fuga Rápida — ATIVA
        add("arqueiro_mob_11", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_12", StatBonus.knockbackResist(0.02));
        add("arqueiro_mob_13", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // mob_14 Camuflagem — DYNAMIC
        add("arqueiro_mob_15", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // mob_16 queda — DYNAMIC
        // mob_17 Passo Leve — DYNAMIC
        add("arqueiro_mob_18", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_19", StatBonus.knockbackResist(0.02));
        // mob_20 Corrida do Vento — ATIVA
        add("arqueiro_mob_21", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // mob_22 queda — DYNAMIC
        // mob_23 Esquiva Instantânea — ATIVA
        add("arqueiro_mob_24", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_25", StatBonus.knockbackResist(0.02));
        // mob_26 Reflexos Felinos — DYNAMIC
        add("arqueiro_mob_27", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // mob_28 queda — DYNAMIC
        add("arqueiro_mob_29", StatBonus.knockbackResist(0.02));
        // mob_30 Salto da Sombra — ATIVA
        add("arqueiro_mob_31", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        // mob_32 Espírito Livre — DYNAMIC

        // ===== ARQUEIRO — ESPECIALIZAÇÃO (→ DEFESA) =====
        add("arqueiro_esp_01", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_02", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_03", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_04", StatBonus.health(1));
        // esp_05 Flecha Envenenada — DYNAMIC
        add("arqueiro_esp_06", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_07", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_08", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        // esp_09 Tiro Múltiplo — ATIVA
        add("arqueiro_esp_10", StatBonus.health(1));
        add("arqueiro_esp_11", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_12", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        // esp_13 Chuva de Flechas — ATIVA
        add("arqueiro_esp_14", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_15", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        // esp_16 Flecha Explosiva — ATIVA
        add("arqueiro_esp_17", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_18", StatBonus.health(1));
        add("arqueiro_esp_19", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        // esp_20 Marcação — ATIVA
        add("arqueiro_esp_21", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_22", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        // esp_23 Flecha de Gelo — DYNAMIC
        add("arqueiro_esp_24", StatBonus.health(1));
        // esp_25 Tiro em Rajada — ATIVA
        add("arqueiro_esp_26", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_27", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_28", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        // esp_29 Caçador Noturno — DYNAMIC (só noite)
        add("arqueiro_esp_30", StatBonus.health(1));
        add("arqueiro_esp_31", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        // esp_32 Mestre Arqueiro — DYNAMIC
    }

    private static void registerClassBase() {
        // vazio
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