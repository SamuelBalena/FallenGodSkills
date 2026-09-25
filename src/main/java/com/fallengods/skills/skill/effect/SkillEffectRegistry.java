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
        add("guerreiro_ofe_17", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_18", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        add("guerreiro_ofe_19", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_21", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_22", StatBonus.health(1));
        add("guerreiro_ofe_23", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_25", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_26", StatBonus.of(StatType.CRIT_CHANCE, 0.03));
        add("guerreiro_ofe_27", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_29", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_30", StatBonus.health(1));
        add("guerreiro_ofe_32", StatBonus.of(StatType.DAMAGE_SWORD, 0.03));
        add("guerreiro_ofe_33", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("guerreiro_ofe_34", StatBonus.of(StatType.CRIT_CHANCE, 0.03));

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
        add("guerreiro_uti_17", StatBonus.health(1));
        add("guerreiro_uti_18", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_20", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_21", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_22", StatBonus.health(1));
        add("guerreiro_uti_24", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("guerreiro_uti_25", StatBonus.knockbackResist(0.03));
        add("guerreiro_uti_27", StatBonus.health(1));
        add("guerreiro_uti_28", StatBonus.damageReduction(0.02));
        add("guerreiro_uti_29", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));

        // ===== ARQUEIRO — PRECISÃO =====
        add("arqueiro_pre_01", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_02", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_03", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_04", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_05", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_06", StatBonus.health(1));
        add("arqueiro_pre_07", StatBonus.of(StatType.DAMAGE_BOW, 0.20));
        add("arqueiro_pre_08", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_09", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_10", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_11", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_13", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_14", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_15", StatBonus.of(StatType.CRIT_CHANCE, 0.15));
        add("arqueiro_pre_16", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_17", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_18", StatBonus.health(1));
        add("arqueiro_pre_19", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_21", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_22", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_24", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_25", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_27", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_28", StatBonus.of(StatType.ATTACK_SPEED, 0.02));
        add("arqueiro_pre_29", StatBonus.health(1));
        add("arqueiro_pre_31", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_32", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_pre_34", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_pre_35", StatBonus.of(StatType.ATTACK_SPEED, 0.02));

        // ===== ARQUEIRO — MOBILIDADE =====
        add("arqueiro_mob_01", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_03", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_04", StatBonus.knockbackResist(0.02));
        add("arqueiro_mob_05", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_06", StatBonus.of(StatType.MOVEMENT_SPEED, 0.10));
        add("arqueiro_mob_07", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_09", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_11", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_12", StatBonus.knockbackResist(0.02));
        add("arqueiro_mob_13", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_15", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_18", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_19", StatBonus.knockbackResist(0.02));
        add("arqueiro_mob_21", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_24", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_25", StatBonus.knockbackResist(0.02));
        add("arqueiro_mob_27", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));
        add("arqueiro_mob_29", StatBonus.knockbackResist(0.02));
        add("arqueiro_mob_31", StatBonus.of(StatType.MOVEMENT_SPEED, 0.03));

        // ===== ARQUEIRO — ESPECIALIZAÇÃO =====
        add("arqueiro_esp_01", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_02", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_03", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_04", StatBonus.health(1));
        add("arqueiro_esp_06", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_07", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_08", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_10", StatBonus.health(1));
        add("arqueiro_esp_11", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_12", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_14", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_15", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_17", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_18", StatBonus.health(1));
        add("arqueiro_esp_19", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_21", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_22", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_24", StatBonus.health(1));
        add("arqueiro_esp_26", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_27", StatBonus.of(StatType.CRIT_CHANCE, 0.02));
        add("arqueiro_esp_28", StatBonus.of(StatType.DAMAGE_BOW, 0.03));
        add("arqueiro_esp_30", StatBonus.health(1));
        add("arqueiro_esp_31", StatBonus.of(StatType.DAMAGE_BOW, 0.03));

        // ===== MAGO — PODER ARCANO =====
        add("mago_pod_01", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_02", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_pod_03", StatBonus.health(1));
        add("mago_pod_04", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_05", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_pod_06", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_07", StatBonus.of(StatType.DAMAGE_MAGIC, 0.15));
        add("mago_pod_08", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_09", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_pod_10", StatBonus.health(1));
        add("mago_pod_11", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.20));
        add("mago_pod_12", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_13", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_pod_15", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_16", StatBonus.health(1));
        add("mago_pod_17", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_pod_18", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_20", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_21", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_pod_23", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_24", StatBonus.health(1));
        add("mago_pod_26", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_28", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_pod_29", StatBonus.health(1));
        add("mago_pod_31", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_32", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_pod_34", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_pod_35", StatBonus.health(1));

        // ===== MAGO — DEFESA MÁGICA =====
        add("mago_def_01", StatBonus.armor(1));
        add("mago_def_02", StatBonus.health(1));
        add("mago_def_03", StatBonus.damageReduction(0.03));
        add("mago_def_04", StatBonus.armor(1));
        add("mago_def_05", StatBonus.health(1));
        add("mago_def_07", StatBonus.armor(1));
        add("mago_def_08", StatBonus.health(1));
        add("mago_def_09", StatBonus.damageReduction(0.03));
        add("mago_def_11", StatBonus.armor(1));
        add("mago_def_12", StatBonus.health(1));
        add("mago_def_14", StatBonus.armor(1));
        add("mago_def_15", StatBonus.damageReduction(0.03));
        add("mago_def_17", StatBonus.health(1));
        add("mago_def_18", StatBonus.armor(1));
        add("mago_def_20", StatBonus.health(1));
        add("mago_def_21", StatBonus.damageReduction(0.03));
        add("mago_def_23", StatBonus.armor(1));
        add("mago_def_24", StatBonus.health(1));
        add("mago_def_26", StatBonus.armor(1));
        add("mago_def_27", StatBonus.damageReduction(0.03));
        add("mago_def_29", StatBonus.health(1));
        add("mago_def_30", StatBonus.armor(1));
        add("mago_def_32",
                StatBonus.armor(2),
                StatBonus.health(2));

        // ===== MAGO — CONTROLE =====
        add("mago_ctr_01", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_02", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_ctr_03", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_04", StatBonus.health(1));
        add("mago_ctr_06", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_07", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_ctr_09", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_10", StatBonus.health(1));
        add("mago_ctr_12", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_13", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_ctr_15", StatBonus.health(1));
        add("mago_ctr_17", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_18", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_ctr_20", StatBonus.health(1));
        add("mago_ctr_22", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_23", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_ctr_25", StatBonus.health(1));
        add("mago_ctr_26", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_27", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_ctr_29", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("mago_ctr_30", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("mago_ctr_31", StatBonus.health(1));

        // ===== SACERDOTE — CURA =====
        add("sacerdote_cur_01", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_02", StatBonus.health(1));
        add("sacerdote_cur_03", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("sacerdote_cur_04", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_05", StatBonus.health(1));
        // cur_06 DYNAMIC
        add("sacerdote_cur_07", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_08", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("sacerdote_cur_09", StatBonus.health(1));
        // cur_10 ATIVA
        add("sacerdote_cur_11", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_12", StatBonus.health(1));
        // cur_13 ATIVA
        add("sacerdote_cur_14", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_15", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("sacerdote_cur_16", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.15));
        add("sacerdote_cur_17", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_18", StatBonus.health(1));
        // cur_19 ATIVA
        add("sacerdote_cur_20", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_21", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        // cur_22 DYNAMIC
        add("sacerdote_cur_23", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_24", StatBonus.health(1));
        // cur_25 ATIVA
        add("sacerdote_cur_26", StatBonus.of(StatType.HEAL_POWER, 0.03));
        // cur_27 DYNAMIC
        add("sacerdote_cur_28", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("sacerdote_cur_29", StatBonus.health(1));
        // cur_30 ATIVA
        add("sacerdote_cur_31", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_32", StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.02));
        add("sacerdote_cur_33", StatBonus.of(StatType.HEAL_POWER, 0.03));
        add("sacerdote_cur_34", StatBonus.health(1));
        add("sacerdote_cur_35", StatBonus.of(StatType.HEAL_POWER, 0.10));
        // cur_36 ATIVA

        // ===== SACERDOTE — PROTEÇÃO =====
        add("sacerdote_pro_01", StatBonus.armor(1));
        add("sacerdote_pro_02", StatBonus.health(1));
        add("sacerdote_pro_03", StatBonus.armor(1));
        add("sacerdote_pro_04", StatBonus.health(1));
        add("sacerdote_pro_05", StatBonus.health(8));
        add("sacerdote_pro_06", StatBonus.armor(1));
        add("sacerdote_pro_07", StatBonus.health(1));
        add("sacerdote_pro_09", StatBonus.armor(1));
        add("sacerdote_pro_10", StatBonus.health(1));
        add("sacerdote_pro_12", StatBonus.armor(1));
        add("sacerdote_pro_13", StatBonus.health(1));
        add("sacerdote_pro_15", StatBonus.armor(1));
        add("sacerdote_pro_17", StatBonus.health(1));
        add("sacerdote_pro_18", StatBonus.armor(1));
        add("sacerdote_pro_20", StatBonus.health(1));
        add("sacerdote_pro_21", StatBonus.armor(1));
        add("sacerdote_pro_23", StatBonus.health(1));
        add("sacerdote_pro_25", StatBonus.armor(1));
        add("sacerdote_pro_26", StatBonus.health(1));
        add("sacerdote_pro_28", StatBonus.armor(1));
        add("sacerdote_pro_29", StatBonus.health(1));
        add("sacerdote_pro_30", StatBonus.armor(1));
        add("sacerdote_pro_31", StatBonus.health(1));
        add("sacerdote_pro_32",
                StatBonus.armor(2),
                StatBonus.health(2));

        // ===== SACERDOTE — COMBATE SAGRADO =====
        add("sacerdote_com_01", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_02", StatBonus.of(StatType.DAMAGE_MAGIC, 0.02));
        add("sacerdote_com_03", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_04", StatBonus.health(1));
        add("sacerdote_com_06", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_07", StatBonus.of(StatType.DAMAGE_MAGIC, 0.02));
        add("sacerdote_com_09", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_10", StatBonus.health(1));
        add("sacerdote_com_12", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_13", StatBonus.of(StatType.DAMAGE_MAGIC, 0.02));
        add("sacerdote_com_15", StatBonus.health(1));
        add("sacerdote_com_17", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_18", StatBonus.of(StatType.DAMAGE_MAGIC, 0.02));
        add("sacerdote_com_20", StatBonus.health(1));
        add("sacerdote_com_22", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_23", StatBonus.of(StatType.DAMAGE_MAGIC, 0.02));
        add("sacerdote_com_25", StatBonus.health(1));
        add("sacerdote_com_26", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_27", StatBonus.of(StatType.DAMAGE_MAGIC, 0.02));
        add("sacerdote_com_29", StatBonus.of(StatType.DAMAGE_MAGIC, 0.03));
        add("sacerdote_com_30", StatBonus.of(StatType.DAMAGE_MAGIC, 0.02));
        add("sacerdote_com_31", StatBonus.health(1));
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