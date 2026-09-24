package com.fallengods.skills.skill;

import com.fallengods.skills.classsystem.ClassType;

import java.util.HashMap;
import java.util.Map;

public class SkillRegistry {

    private static final Map<ClassType, SkillTree> TREES = new HashMap<>();

    public static void init() {
        TREES.put(ClassType.GUERREIRO, buildGuerreiro());
        TREES.put(ClassType.ARQUEIRO, buildArqueiro());
        TREES.put(ClassType.MAGO, buildMago());
        TREES.put(ClassType.SACERDOTE, buildSacerdote());
    }

    public static SkillTree getTree(ClassType type) {
        return TREES.get(type);
    }

    public static SkillNode getNode(ClassType type, String nodeId) {
        SkillTree tree = TREES.get(type);
        if (tree == null)
            return null;
        return tree.getNode(nodeId);
    }

    // =====================================================================
    // GUERREIRO
    // =====================================================================
    private static SkillTree buildGuerreiro() {
        SkillTree tree = new SkillTree(ClassType.GUERREIRO);

        // ================================================================
        // RAMO DEFESA — 34 nós
        //
        // Topologia (cada linha representa um nó; '│' = filho único,
        // '╱ ╲' = ramificação em 2 caminhos paralelos, '╲ ╱' = junção):
        //
        // [def_01]
        // │
        // [def_02]
        // │
        // [def_03]
        // ╱ ╲
        // [def_04] [def_05]
        // │ │
        // [def_06] [def_07]
        // │ │
        // [def_08] [def_09]
        // ╲ ╱
        // [def_10] Pele de Ferro
        // │
        // [def_11]
        // │
        // [def_12]
        // ╱ ╲
        // [def_13] [def_14]
        // │ │
        // [def_15] [def_16]
        // ╲ ╱
        // [def_17] Resistência Ancestral
        // │
        // [def_18]
        // │
        // [def_19]
        // ╱ ╲
        // [def_20] [def_21]
        // │ │
        // [def_22] [def_23]
        // ╲ ╱
        // [def_24] Postura Defensiva
        // │
        // [def_25]
        // │
        // [def_26] Vigor Inabalável
        // │
        // [def_27]
        // ╱ ╲
        // [def_28] [def_29]
        // ╲ ╱
        // [def_30] Couraça Viva
        // │
        // [def_31]
        // │
        // [def_32] Muralha Humana
        // ================================================================

        SkillBranch D = SkillBranch.DEFESA;

        tree.add(SkillNode.builder("guerreiro_def_01")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(0, 0).build());

        tree.add(SkillNode.builder("guerreiro_def_02")
                .name("+0.5 Coração").desc("+1 de vida máxima (meio coração).")
                .branch(D).grid(0, 1).prereq("guerreiro_def_01").build());

        tree.add(SkillNode.builder("guerreiro_def_03")
                .name("+3% Resistência a Knockback").desc("Reduz knockback em 3%.")
                .branch(D).grid(0, 2).prereq("guerreiro_def_02").build());

        // Ramificação: def_04 e def_05 em paralelo
        tree.add(SkillNode.builder("guerreiro_def_04")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(-1, 3).prereq("guerreiro_def_03").build());

        tree.add(SkillNode.builder("guerreiro_def_05")
                .name("+0.5 Coração").desc("+1 de vida máxima.")
                .branch(D).grid(1, 3).prereq("guerreiro_def_03").build());

        tree.add(SkillNode.builder("guerreiro_def_06")
                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                .branch(D).grid(-1, 4).prereq("guerreiro_def_04").build());

        tree.add(SkillNode.builder("guerreiro_def_07")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(1, 4).prereq("guerreiro_def_05").build());

        tree.add(SkillNode.builder("guerreiro_def_08")
                .name("Pele de Ferro")
                .desc("Passiva: +2 Armadura permanente.")
                .branch(D).grid(-1, 5).prereq("guerreiro_def_06").build());

        tree.add(SkillNode.builder("guerreiro_def_09")
                .name("+0.5 Coração").desc("+1 de vida máxima.")
                .branch(D).grid(1, 5).prereq("guerreiro_def_07").build());

        // Junção
        tree.add(SkillNode.builder("guerreiro_def_10")
                .name("+5% Resistência a Knockback").desc("Reduz knockback em 5%.")
                .branch(D).grid(0, 6).prereq("guerreiro_def_08", "guerreiro_def_09").build());

        tree.add(SkillNode.builder("guerreiro_def_11")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(0, 7).prereq("guerreiro_def_10").build());

        tree.add(SkillNode.builder("guerreiro_def_12")
                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                .branch(D).grid(0, 8).prereq("guerreiro_def_11").build());

        // Ramificação
        tree.add(SkillNode.builder("guerreiro_def_13")
                .name("+0.5 Coração").desc("+1 de vida máxima.")
                .branch(D).grid(-1, 9).prereq("guerreiro_def_12").build());

        tree.add(SkillNode.builder("guerreiro_def_14")
                .name("Resistência Ancestral")
                .desc("Passiva: reduz 15% de todo dano recebido.")
                .branch(D).grid(1, 9).prereq("guerreiro_def_12").build());

        tree.add(SkillNode.builder("guerreiro_def_15")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(-1, 10).prereq("guerreiro_def_13").build());

        tree.add(SkillNode.builder("guerreiro_def_16")
                .name("+5% Resistência a Knockback").desc("Reduz knockback em 5%.")
                .branch(D).grid(1, 10).prereq("guerreiro_def_14").build());

        // Junção
        tree.add(SkillNode.builder("guerreiro_def_17")
                .name("+0.5 Coração").desc("+1 de vida máxima.")
                .branch(D).grid(0, 11).prereq("guerreiro_def_15", "guerreiro_def_16").build());

        tree.add(SkillNode.builder("guerreiro_def_18")
                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                .branch(D).grid(0, 12).prereq("guerreiro_def_17").build());

        tree.add(SkillNode.builder("guerreiro_def_19")
                .name("Postura Defensiva")
                .desc("Passiva: reduz mais 10% de dano enquanto estiver com escudo.")
                .branch(D).grid(0, 13).prereq("guerreiro_def_18").build());

        // Ramificação
        tree.add(SkillNode.builder("guerreiro_def_20")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(-1, 14).prereq("guerreiro_def_19").build());

        tree.add(SkillNode.builder("guerreiro_def_21")
                .name("+0.5 Coração").desc("+1 de vida máxima.")
                .branch(D).grid(1, 14).prereq("guerreiro_def_19").build());

        tree.add(SkillNode.builder("guerreiro_def_22")
                .name("+5% Resistência a Knockback").desc("Reduz knockback em 5%.")
                .branch(D).grid(-1, 15).prereq("guerreiro_def_20").build());

        tree.add(SkillNode.builder("guerreiro_def_23")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(1, 15).prereq("guerreiro_def_21").build());

        // Junção
        tree.add(SkillNode.builder("guerreiro_def_24")
                .name("Vigor Inabalável")
                .desc("Passiva: +20% de resistência a knockback.")
                .branch(D).grid(0, 16).prereq("guerreiro_def_22", "guerreiro_def_23").build());

        tree.add(SkillNode.builder("guerreiro_def_25")
                .name("+0.5 Coração").desc("+1 de vida máxima.")
                .branch(D).grid(0, 17).prereq("guerreiro_def_24").build());

        tree.add(SkillNode.builder("guerreiro_def_26")
                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                .branch(D).grid(0, 18).prereq("guerreiro_def_25").build());

        tree.add(SkillNode.builder("guerreiro_def_27")
                .name("Couraça Viva")
                .desc("Passiva: ganha 2 de absorção ao receber dano crítico (CD 12s).")
                .branch(D).grid(0, 19).prereq("guerreiro_def_26").build());

        // Ramificação final
        tree.add(SkillNode.builder("guerreiro_def_28")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(-1, 20).prereq("guerreiro_def_27").build());

        tree.add(SkillNode.builder("guerreiro_def_29")
                .name("+5% Resistência a Knockback").desc("Reduz knockback em 5%.")
                .branch(D).grid(1, 20).prereq("guerreiro_def_27").build());

        tree.add(SkillNode.builder("guerreiro_def_30")
                .name("+0.5 Coração").desc("+1 de vida máxima.")
                .branch(D).grid(-1, 21).prereq("guerreiro_def_28").build());

        tree.add(SkillNode.builder("guerreiro_def_31")
                .name("+1 Armadura").desc("+1 de armadura permanente.")
                .branch(D).grid(1, 21).prereq("guerreiro_def_29").build());

        // Junção final
        tree.add(SkillNode.builder("guerreiro_def_32")
                .name("Muralha Humana")
                .desc("Passiva: abaixo de 30% de vida, ganha +4 Armadura.")
                .branch(D).grid(0, 22).prereq("guerreiro_def_30", "guerreiro_def_31").build());

        tree.add(SkillNode.builder("guerreiro_def_33")
                .name("+0.5 Coração").desc("+1 de vida máxima.")
                .branch(D).grid(0, 23).prereq("guerreiro_def_32").build());

        tree.add(SkillNode.builder("guerreiro_def_34")
                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                .branch(D).grid(0, 24).prereq("guerreiro_def_33").build());

        // ================================================================
        // RAMO OFENSIVO — placeholders (a popular na sub-fase 5.6)
        // ================================================================
        SkillBranch O = SkillBranch.OFENSIVO;

        tree.add(SkillNode.builder("guerreiro_ofe_01")
                .name("+3% Dano com Espadas").desc("Placeholder — a popular.")
                .branch(O).grid(4, 0).build());

        tree.add(SkillNode.builder("guerreiro_ofe_02")
                .name("+2% Velocidade de Ataque").desc("Placeholder — a popular.")
                .branch(O).grid(4, 1).prereq("guerreiro_ofe_01").build());

        tree.add(SkillNode.builder("guerreiro_ofe_03")
                .name("+0.5 Coração").desc("Placeholder — a popular.")
                .branch(O).grid(4, 2).prereq("guerreiro_ofe_02").build());

        // ================================================================
        // RAMO UTILIDADE — placeholders
        // ================================================================
        SkillBranch U = SkillBranch.UTILIDADE;

        tree.add(SkillNode.builder("guerreiro_uti_01")
                .name("+3% Velocidade de Movimento").desc("Placeholder — a popular.")
                .branch(U).grid(0, 6).build());

        tree.add(SkillNode.builder("guerreiro_uti_02")
                .name("+3% Resistência a Knockback").desc("Placeholder — a popular.")
                .branch(U).grid(0, 7).prereq("guerreiro_uti_01").build());

        tree.add(SkillNode.builder("guerreiro_uti_03")
                .name("+0.5 Coração").desc("Placeholder — a popular.")
                .branch(U).grid(0, 8).prereq("guerreiro_uti_02").build());

        return tree;
    }

    // =====================================================================
    // ARQUEIRO (placeholder — a popular na sub-fase 5.6)
    // =====================================================================
    private static SkillTree buildArqueiro() {
        SkillTree tree = new SkillTree(ClassType.ARQUEIRO);

        tree.add(SkillNode.builder("arqueiro_pre_01")
                .name("+3% Dano com Arco").desc("Placeholder — a popular.")
                .branch(SkillBranch.OFENSIVO).grid(0, 0).build());

        tree.add(SkillNode.builder("arqueiro_mob_01")
                .name("+3% Velocidade de Movimento").desc("Placeholder — a popular.")
                .branch(SkillBranch.UTILIDADE).grid(0, 4).build());

        tree.add(SkillNode.builder("arqueiro_esp_01")
                .name("Flecha Envenenada").desc("Placeholder — a popular.")
                .branch(SkillBranch.DEFESA).grid(4, 4).build());

        return tree;
    }

    // =====================================================================
    // MAGO (placeholder — a popular na sub-fase 5.6)
    // =====================================================================
    private static SkillTree buildMago() {
        SkillTree tree = new SkillTree(ClassType.MAGO);

        tree.add(SkillNode.builder("mago_pod_01")
                .name("+3% Dano Mágico").desc("Placeholder — a popular.")
                .branch(SkillBranch.OFENSIVO).grid(0, 0).build());

        tree.add(SkillNode.builder("mago_def_01")
                .name("+1 Armadura").desc("Placeholder — a popular.")
                .branch(SkillBranch.DEFESA).grid(0, 4).build());

        tree.add(SkillNode.builder("mago_ctr_01")
                .name("Chama Interior").desc("Placeholder — a popular.")
                .branch(SkillBranch.UTILIDADE).grid(4, 4).build());

        return tree;
    }

    // =====================================================================
    // SACERDOTE (placeholder — a popular na sub-fase 5.6)
    // =====================================================================
    private static SkillTree buildSacerdote() {
        SkillTree tree = new SkillTree(ClassType.SACERDOTE);

        tree.add(SkillNode.builder("sacerdote_cur_01")
                .name("+3% Poder de Cura").desc("Placeholder — a popular.")
                .branch(SkillBranch.OFENSIVO).grid(0, 0).build());

        tree.add(SkillNode.builder("sacerdote_pro_01")
                .name("+1 Armadura").desc("Placeholder — a popular.")
                .branch(SkillBranch.DEFESA).grid(0, 4).build());

        tree.add(SkillNode.builder("sacerdote_com_01")
                .name("Castigo Sagrado").desc("Placeholder — a popular.")
                .branch(SkillBranch.UTILIDADE).grid(4, 4).build());

        return tree;
    }
}