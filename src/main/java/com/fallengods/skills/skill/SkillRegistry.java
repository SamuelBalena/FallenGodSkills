package com.fallengods.skills.skill;

import com.fallengods.skills.classsystem.ClassType;

import java.util.HashMap;
import java.util.Map;

public class SkillRegistry {

    private static final Map<ClassType, SkillTree> TREES = new HashMap<>();

    public static void init() {
        // ===== GUERREIRO =====
        SkillTree guerreiro = new SkillTree(ClassType.GUERREIRO)
                .addSkill(new Skill("guerreiro_forca", "Força Bruta", 1, "+1 de dano por nível"))
                .addSkill(new Skill("guerreiro_resistencia", "Pele de Ferro", 1, "+1 de armadura por nível"))
                .addSkill(new Skill("guerreiro_furia", "Fúria", 2, "+5% velocidade de ataque"))
                .addSkill(new Skill("guerreiro_golpe", "Golpe Devastador", 3, "Chance de crítico aumentada"));

        // ===== ARQUEIRO =====
        SkillTree arqueiro = new SkillTree(ClassType.ARQUEIRO)
                .addSkill(new Skill("arqueiro_precisao", "Precisão", 1, "+1 de dano com arco"))
                .addSkill(new Skill("arqueiro_agilidade", "Agilidade", 1, "+5% velocidade de movimento"))
                .addSkill(new Skill("arqueiro_penetracao", "Penetração", 2, "Flechas atravessam 1 alvo"))
                .addSkill(new Skill("arqueiro_olho", "Olho de Águia", 3, "+10% alcance de arco"));

        // ===== MAGO =====
        SkillTree mago = new SkillTree(ClassType.MAGO)
                .addSkill(new Skill("mago_intelecto", "Intelecto", 1, "+10 de mana máxima"))
                .addSkill(new Skill("mago_regeneracao", "Regeneração", 1, "Mana regenera mais rápido"))
                .addSkill(new Skill("mago_potencia", "Potência Arcana", 2, "+1 de dano mágico"))
                .addSkill(new Skill("mago_meteoro", "Meteoro", 3, "Skill ativa: chuva de fogo"));

        // ===== SACERDOTE =====
        SkillTree sacerdote = new SkillTree(ClassType.SACERDOTE)
                .addSkill(new Skill("sacerdote_fe", "Fé", 1, "+1 de cura por nível"))
                .addSkill(new Skill("sacerdote_bencao", "Bênção", 1, "Aliados próximos ganham +1 vida"))
                .addSkill(new Skill("sacerdote_escudo", "Escudo Sagrado", 2, "Absorve 2 de dano por 10s"))
                .addSkill(new Skill("sacerdote_ressurreicao", "Ressurreição", 3, "Skill ativa: revive aliado"));

        TREES.put(ClassType.GUERREIRO, guerreiro);
        TREES.put(ClassType.ARQUEIRO, arqueiro);
        TREES.put(ClassType.MAGO, mago);
        TREES.put(ClassType.SACERDOTE, sacerdote);
    }

    public static SkillTree getTree(ClassType type) {
        return TREES.get(type);
    }

    public static Skill getSkill(ClassType type, String skillId) {
        SkillTree tree = TREES.get(type);
        if (tree == null)
            return null;
        for (Skill s : tree.getSkills()) {
            if (s.getId().equals(skillId))
                return s;
        }
        return null;
    }
}