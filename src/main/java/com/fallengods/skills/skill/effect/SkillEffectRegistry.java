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

    // nodeId -> List<StatBonus>
    private static final Map<String, List<StatBonus>> NODE_EFFECTS = new HashMap<>();

    // classType -> List<StatBonus> (passivas base, aplicadas automaticamente)
    private static final Map<ClassType, List<StatBonus>> CLASS_BASE = new HashMap<>();

    public static void init() {
        registerNodeEffects();
        registerClassBase();
    }

    // =====================================================================
    // Efeitos dos nós — RAMO DEFESA DO GUERREIRO
    // =====================================================================
    private static void registerNodeEffects() {
        add("guerreiro_def_01", StatBonus.armor(1));
        add("guerreiro_def_02", StatBonus.health(1));
        add("guerreiro_def_03", StatBonus.knockbackResist(0.03));

        add("guerreiro_def_04", StatBonus.armor(1));
        add("guerreiro_def_05", StatBonus.health(1));

        add("guerreiro_def_06", StatBonus.damageReduction(0.03));
        add("guerreiro_def_07", StatBonus.armor(1));

        add("guerreiro_def_08", StatBonus.armor(2)); // Pele de Ferro
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
        add("guerreiro_def_19", StatBonus.damageReduction(0.10)); // Postura Defensiva (TODO 5.4)

        add("guerreiro_def_20", StatBonus.armor(1));
        add("guerreiro_def_21", StatBonus.health(1));
        add("guerreiro_def_22", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_23", StatBonus.armor(1));

        add("guerreiro_def_24", StatBonus.knockbackResist(0.20)); // Vigor Inabalável

        add("guerreiro_def_25", StatBonus.health(1));
        add("guerreiro_def_26", StatBonus.damageReduction(0.03));
        // guerreiro_def_27 (Couraça Viva) -> efeito dinâmico, vai pra 5.4

        add("guerreiro_def_28", StatBonus.armor(1));
        add("guerreiro_def_29", StatBonus.knockbackResist(0.05));
        add("guerreiro_def_30", StatBonus.health(1));
        add("guerreiro_def_31", StatBonus.armor(1));

        // guerreiro_def_32 (Muralha Humana) -> efeito dinâmico, vai pra 5.4
        add("guerreiro_def_33", StatBonus.health(1));
        add("guerreiro_def_34", StatBonus.damageReduction(0.03));
    }

    // =====================================================================
    // Passivas base — aplicadas automaticamente ao escolher a classe
    // =====================================================================
    private static void registerClassBase() {
        // Guerreiro: +20% dano com Espadas e Machados, +10% resistência a knockback
        List<StatBonus> guerreiro = new ArrayList<>();
        guerreiro.add(StatBonus.of(StatType.DAMAGE_SWORD, 0.20));
        guerreiro.add(StatBonus.of(StatType.DAMAGE_AXE, 0.20));
        guerreiro.add(StatBonus.of(StatType.KNOCKBACK_RESISTANCE, 0.10));
        CLASS_BASE.put(ClassType.GUERREIRO, guerreiro);

        // Arqueiro: +20% dano com Arcos, +10% velocidade de movimento
        List<StatBonus> arqueiro = new ArrayList<>();
        arqueiro.add(StatBonus.of(StatType.DAMAGE_BOW, 0.20));
        arqueiro.add(StatBonus.of(StatType.MOVEMENT_SPEED, 0.10));
        CLASS_BASE.put(ClassType.ARQUEIRO, arqueiro);

        // Mago: +20% dano mágico, +15% redução de cooldown
        List<StatBonus> mago = new ArrayList<>();
        mago.add(StatBonus.of(StatType.DAMAGE_MAGIC, 0.20));
        mago.add(StatBonus.of(StatType.COOLDOWN_REDUCTION, 0.15));
        CLASS_BASE.put(ClassType.MAGO, mago);

        // Sacerdote: +20% poder de cura, +2 corações
        List<StatBonus> sacerdote = new ArrayList<>();
        sacerdote.add(StatBonus.of(StatType.HEAL_POWER, 0.20));
        sacerdote.add(StatBonus.health(4));
        CLASS_BASE.put(ClassType.SACERDOTE, sacerdote);
    }

    // =====================================================================
    // Recálculo
    // =====================================================================
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

    // =====================================================================
    // Helpers
    // =====================================================================
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