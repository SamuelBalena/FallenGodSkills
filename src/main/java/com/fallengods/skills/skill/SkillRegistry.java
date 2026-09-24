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

        /** Retorna o id do nó central daquela classe. */
        public static String getCentralNodeId(ClassType type) {
                switch (type) {
                        case GUERREIRO:
                                return "guerreiro_central";
                        case ARQUEIRO:
                                return "arqueiro_central";
                        case MAGO:
                                return "mago_central";
                        case SACERDOTE:
                                return "sacerdote_central";
                        default:
                                return null;
                }
        }

        // =====================================================================
        // GUERREIRO
        // =====================================================================
        private static SkillTree buildGuerreiro() {
                SkillTree tree = new SkillTree(ClassType.GUERREIRO);

                // ================================================================
                // NÓ CENTRAL — ponto de partida da árvore
                // ================================================================
                tree.add(SkillNode.builder("guerreiro_central")
                                .name("Coração de Batalha")
                                .desc("Passiva base: +20% dano com espadas/machados, +10% resistência a knockback.")
                                .branch(SkillBranch.DEFESA)
                                .type(SkillType.PASSIVA)
                                .cost(0)
                                .grid(0, 0)
                                .build());

                // ================================================================
                // RAMO DEFESA — 34 nós, canto superior esquerdo
                //
                // Cada nó tem gridX/gridY ajustados pra região -15..-5 x -8..+8.
                // Os entry nodes (def_01, ofe_01, uti_01) dependem do central.
                //
                // Topologia (visual):
                //
                // [def_01] [central] [ofe_01]
                // │ │ │
                // [def_02] │ [ofe_02]
                // │ │ │
                // [def_03] │ [ofe_03]
                // ╱ ╲ │
                // [def_04] [def_05] │
                // │ │ │
                // [def_06] [def_07] │
                // │ │ │
                // [def_08] [def_09] │
                // ╲ ╱ │
                // [def_10] │
                // ...
                // ================================================================

                SkillBranch D = SkillBranch.DEFESA;

                tree.add(SkillNode.builder("guerreiro_def_01")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-4, -2).prereq("guerreiro_central").build());

                tree.add(SkillNode.builder("guerreiro_def_02")
                                .name("+0.5 Coração").desc("+1 de vida máxima (meio coração).")
                                .branch(D).grid(-4, -1).prereq("guerreiro_def_01").build());

                tree.add(SkillNode.builder("guerreiro_def_03")
                                .name("+3% Resistência a Knockback").desc("Reduz knockback em 3%.")
                                .branch(D).grid(-4, 0).prereq("guerreiro_def_02").build());

                tree.add(SkillNode.builder("guerreiro_def_04")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 1).prereq("guerreiro_def_03").build());

                tree.add(SkillNode.builder("guerreiro_def_05")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-3, 1).prereq("guerreiro_def_03").build());

                tree.add(SkillNode.builder("guerreiro_def_06")
                                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                                .branch(D).grid(-5, 2).prereq("guerreiro_def_04").build());

                tree.add(SkillNode.builder("guerreiro_def_07")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 2).prereq("guerreiro_def_05").build());

                tree.add(SkillNode.builder("guerreiro_def_08")
                                .name("Pele de Ferro").desc("Passiva: +2 Armadura permanente.")
                                .branch(D).grid(-5, 3).prereq("guerreiro_def_06").build());

                tree.add(SkillNode.builder("guerreiro_def_09")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-3, 3).prereq("guerreiro_def_07").build());

                tree.add(SkillNode.builder("guerreiro_def_10")
                                .name("+5% Resistência a Knockback").desc("Reduz knockback em 5%.")
                                .branch(D).grid(-4, 4).prereq("guerreiro_def_08", "guerreiro_def_09").build());

                tree.add(SkillNode.builder("guerreiro_def_11")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-4, 5).prereq("guerreiro_def_10").build());

                tree.add(SkillNode.builder("guerreiro_def_12")
                                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                                .branch(D).grid(-4, 6).prereq("guerreiro_def_11").build());

                tree.add(SkillNode.builder("guerreiro_def_13")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-5, 7).prereq("guerreiro_def_12").build());

                tree.add(SkillNode.builder("guerreiro_def_14")
                                .name("Resistência Ancestral").desc("Passiva: reduz 15% de todo dano recebido.")
                                .branch(D).grid(-3, 7).prereq("guerreiro_def_12").build());

                tree.add(SkillNode.builder("guerreiro_def_15")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 8).prereq("guerreiro_def_13").build());

                tree.add(SkillNode.builder("guerreiro_def_16")
                                .name("+5% Resistência a Knockback").desc("Reduz knockback em 5%.")
                                .branch(D).grid(-3, 8).prereq("guerreiro_def_14").build());

                tree.add(SkillNode.builder("guerreiro_def_17")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-4, 9).prereq("guerreiro_def_15", "guerreiro_def_16").build());

                tree.add(SkillNode.builder("guerreiro_def_18")
                                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                                .branch(D).grid(-4, 10).prereq("guerreiro_def_17").build());

                tree.add(SkillNode.builder("guerreiro_def_19")
                                .name("Postura Defensiva").desc("Passiva: -10% dano com escudo.")
                                .branch(D).grid(-4, 11).prereq("guerreiro_def_18").build());

                tree.add(SkillNode.builder("guerreiro_def_20")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 12).prereq("guerreiro_def_19").build());

                tree.add(SkillNode.builder("guerreiro_def_21")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-3, 12).prereq("guerreiro_def_19").build());

                tree.add(SkillNode.builder("guerreiro_def_22")
                                .name("+5% Resistência a Knockback").desc("Reduz knockback em 5%.")
                                .branch(D).grid(-5, 13).prereq("guerreiro_def_20").build());

                tree.add(SkillNode.builder("guerreiro_def_23")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 13).prereq("guerreiro_def_21").build());

                tree.add(SkillNode.builder("guerreiro_def_24")
                                .name("Vigor Inabalável").desc("Passiva: +20% de resistência a knockback.")
                                .branch(D).grid(-4, 14).prereq("guerreiro_def_22", "guerreiro_def_23").build());

                tree.add(SkillNode.builder("guerreiro_def_25")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-4, 15).prereq("guerreiro_def_24").build());

                tree.add(SkillNode.builder("guerreiro_def_26")
                                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                                .branch(D).grid(-4, 16).prereq("guerreiro_def_25").build());

                tree.add(SkillNode.builder("guerreiro_def_27")
                                .name("Couraça Viva").desc("Passiva: 2 de absorção ao levar crítico (CD 12s).")
                                .branch(D).grid(-4, 17).prereq("guerreiro_def_26").build());

                tree.add(SkillNode.builder("guerreiro_def_28")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 18).prereq("guerreiro_def_27").build());

                tree.add(SkillNode.builder("guerreiro_def_29")
                                .name("+5% Resistência a Knockback").desc("Reduz knockback em 5%.")
                                .branch(D).grid(-3, 18).prereq("guerreiro_def_27").build());

                tree.add(SkillNode.builder("guerreiro_def_30")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-5, 19).prereq("guerreiro_def_28").build());

                tree.add(SkillNode.builder("guerreiro_def_31")
                                .name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 19).prereq("guerreiro_def_29").build());

                tree.add(SkillNode.builder("guerreiro_def_32")
                                .name("Muralha Humana").desc("Passiva: +4 Armadura abaixo de 30% de vida.")
                                .branch(D).grid(-4, 20).prereq("guerreiro_def_30", "guerreiro_def_31").build());

                tree.add(SkillNode.builder("guerreiro_def_33")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-4, 21).prereq("guerreiro_def_32").build());

                tree.add(SkillNode.builder("guerreiro_def_34")
                                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                                .branch(D).grid(-4, 22).prereq("guerreiro_def_33").build());

                // ================================================================
                // RAMO OFENSIVO — placeholders (a popular na 5.6), canto sup.dir
                // ================================================================
                SkillBranch O = SkillBranch.OFENSIVO;

                tree.add(SkillNode.builder("guerreiro_ofe_01")
                                .name("+3% Dano com Espadas").desc("Placeholder.")
                                .branch(O).grid(4, -2).prereq("guerreiro_central").build());

                tree.add(SkillNode.builder("guerreiro_ofe_02")
                                .name("+2% Velocidade de Ataque").desc("Placeholder.")
                                .branch(O).grid(4, -1).prereq("guerreiro_ofe_01").build());

                tree.add(SkillNode.builder("guerreiro_ofe_03")
                                .name("+0.5 Coração").desc("Placeholder.")
                                .branch(O).grid(4, 0).prereq("guerreiro_ofe_02").build());

                // ================================================================
                // RAMO UTILIDADE — placeholders, canto inferior
                // ================================================================
                SkillBranch U = SkillBranch.UTILIDADE;

                tree.add(SkillNode.builder("guerreiro_uti_01")
                                .name("+3% Velocidade de Movimento").desc("Placeholder.")
                                .branch(U).grid(0, 4).prereq("guerreiro_central").build());

                tree.add(SkillNode.builder("guerreiro_uti_02")
                                .name("+3% Resistência a Knockback").desc("Placeholder.")
                                .branch(U).grid(0, 5).prereq("guerreiro_uti_01").build());

                tree.add(SkillNode.builder("guerreiro_uti_03")
                                .name("+0.5 Coração").desc("Placeholder.")
                                .branch(U).grid(0, 6).prereq("guerreiro_uti_02").build());

                return tree;
        }

        // =====================================================================
        // ARQUEIRO (placeholder — a popular na 5.6)
        // =====================================================================
        private static SkillTree buildArqueiro() {
                SkillTree tree = new SkillTree(ClassType.ARQUEIRO);

                tree.add(SkillNode.builder("arqueiro_central")
                                .name("Olhar do Caçador")
                                .desc("Passiva base: +20% dano com arcos, +10% velocidade.")
                                .branch(SkillBranch.OFENSIVO).cost(0).grid(0, 0).build());

                tree.add(SkillNode.builder("arqueiro_pre_01")
                                .name("+3% Dano com Arco").desc("Placeholder.")
                                .branch(SkillBranch.OFENSIVO).grid(4, -2).prereq("arqueiro_central").build());

                tree.add(SkillNode.builder("arqueiro_mob_01")
                                .name("+3% Velocidade de Movimento").desc("Placeholder.")
                                .branch(SkillBranch.UTILIDADE).grid(0, 4).prereq("arqueiro_central").build());

                tree.add(SkillNode.builder("arqueiro_esp_01")
                                .name("Flecha Envenenada").desc("Placeholder.")
                                .branch(SkillBranch.DEFESA).grid(-4, -2).prereq("arqueiro_central").build());

                return tree;
        }

        // =====================================================================
        // MAGO (placeholder — a popular na 5.6)
        // =====================================================================
        private static SkillTree buildMago() {
                SkillTree tree = new SkillTree(ClassType.MAGO);

                tree.add(SkillNode.builder("mago_central")
                                .name("Sangue Arcano")
                                .desc("Passiva base: +20% dano mágico, +15% redução de cooldown.")
                                .branch(SkillBranch.OFENSIVO).cost(0).grid(0, 0).build());

                tree.add(SkillNode.builder("mago_pod_01")
                                .name("+3% Dano Mágico").desc("Placeholder.")
                                .branch(SkillBranch.OFENSIVO).grid(4, -2).prereq("mago_central").build());

                tree.add(SkillNode.builder("mago_def_01")
                                .name("+1 Armadura").desc("Placeholder.")
                                .branch(SkillBranch.DEFESA).grid(-4, -2).prereq("mago_central").build());

                tree.add(SkillNode.builder("mago_ctr_01")
                                .name("Chama Interior").desc("Placeholder.")
                                .branch(SkillBranch.UTILIDADE).grid(0, 4).prereq("mago_central").build());

                return tree;
        }

        // =====================================================================
        // SACERDOTE (placeholder — a popular na 5.6)
        // =====================================================================
        private static SkillTree buildSacerdote() {
                SkillTree tree = new SkillTree(ClassType.SACERDOTE);

                tree.add(SkillNode.builder("sacerdote_central")
                                .name("Graça Divina")
                                .desc("Passiva base: +20% poder de cura, +2 corações.")
                                .branch(SkillBranch.OFENSIVO).cost(0).grid(0, 0).build());

                tree.add(SkillNode.builder("sacerdote_cur_01")
                                .name("+3% Poder de Cura").desc("Placeholder.")
                                .branch(SkillBranch.OFENSIVO).grid(4, -2).prereq("sacerdote_central").build());

                tree.add(SkillNode.builder("sacerdote_pro_01")
                                .name("+1 Armadura").desc("Placeholder.")
                                .branch(SkillBranch.DEFESA).grid(-4, -2).prereq("sacerdote_central").build());

                tree.add(SkillNode.builder("sacerdote_com_01")
                                .name("Castigo Sagrado").desc("Placeholder.")
                                .branch(SkillBranch.UTILIDADE).grid(0, 4).prereq("sacerdote_central").build());

                return tree;
        }
}