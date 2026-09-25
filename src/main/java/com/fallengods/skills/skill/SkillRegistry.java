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

                tree.add(SkillNode.builder("guerreiro_central")
                                .name("Coração de Batalha")
                                .desc("Passiva base: +20% dano com espadas/machados, +10% resistência a knockback.")
                                .branch(SkillBranch.DEFESA).type(SkillType.PASSIVA)
                                .cost(0).grid(0, 0).build());

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
                                .name("Postura Defensiva").desc("Passiva: -10% dano com escudo. (efeito dinâmico)")
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
                                .name("Couraça Viva").desc("Passiva: absorção ao levar crítico. (efeito dinâmico)")
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
                                .name("Muralha Humana")
                                .desc("Passiva: +4 Armadura abaixo de 30% de vida. (efeito dinâmico)")
                                .branch(D).grid(-4, 20).prereq("guerreiro_def_30", "guerreiro_def_31").build());
                tree.add(SkillNode.builder("guerreiro_def_33")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(D).grid(-4, 21).prereq("guerreiro_def_32").build());
                tree.add(SkillNode.builder("guerreiro_def_34")
                                .name("+3% Redução de Dano").desc("Reduz 3% de todo dano recebido.")
                                .branch(D).grid(-4, 22).prereq("guerreiro_def_33").build());

                SkillBranch O = SkillBranch.OFENSIVO;

                tree.add(SkillNode.builder("guerreiro_ofe_01").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(4, -2).prereq("guerreiro_central")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_02").name("+2% Velocidade de Ataque")
                                .desc("+2% de velocidade de ataque.").branch(O).grid(4, -1).prereq("guerreiro_ofe_01")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_03").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 0).prereq("guerreiro_ofe_02").build());
                tree.add(SkillNode.builder("guerreiro_ofe_04").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(5, 1).prereq("guerreiro_ofe_03")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_05").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(O).grid(3, 1).prereq("guerreiro_ofe_03")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_06").name("+2% Velocidade de Ataque")
                                .desc("+2% de velocidade de ataque.").branch(O).grid(5, 2).prereq("guerreiro_ofe_04")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_07").name("Fúria Primordial")
                                .desc("Passiva: +15% de dano com Espadas e Machados.").branch(O).grid(3, 2)
                                .prereq("guerreiro_ofe_05").build());
                tree.add(SkillNode.builder("guerreiro_ofe_08").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(5, 3).prereq("guerreiro_ofe_06")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_09").name("+2% Velocidade de Ataque")
                                .desc("+2% de velocidade de ataque.").branch(O).grid(3, 3).prereq("guerreiro_ofe_07")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_10").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 4).prereq("guerreiro_ofe_08", "guerreiro_ofe_09").build());
                tree.add(SkillNode.builder("guerreiro_ofe_11").name("+3% Chance de Crítico")
                                .desc("+3% de chance de crítico.").branch(O).grid(5, 5).prereq("guerreiro_ofe_10")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_12").name("Golpe Devastador")
                                .desc("Ativa: próximo ataque +50% dano. (CD 12s)").branch(O).type(SkillType.ATIVA)
                                .grid(3, 5).prereq("guerreiro_ofe_10").build());
                tree.add(SkillNode.builder("guerreiro_ofe_13").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(5, 6).prereq("guerreiro_ofe_11")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_14").name("+2% Velocidade de Ataque")
                                .desc("+2% de velocidade de ataque.").branch(O).grid(3, 6).prereq("guerreiro_ofe_12")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_15").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 7).prereq("guerreiro_ofe_13", "guerreiro_ofe_14").build());
                tree.add(SkillNode.builder("guerreiro_ofe_16").name("Sangue de Batalha")
                                .desc("Passiva: cura 1 coração ao matar. (efeito dinâmico)").branch(O).grid(5, 8)
                                .prereq("guerreiro_ofe_15").build());
                tree.add(SkillNode.builder("guerreiro_ofe_17").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(3, 8).prereq("guerreiro_ofe_15")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_18").name("+3% Chance de Crítico")
                                .desc("+3% de chance de crítico.").branch(O).grid(5, 9).prereq("guerreiro_ofe_16")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_19").name("+2% Velocidade de Ataque")
                                .desc("+2% de velocidade de ataque.").branch(O).grid(3, 9).prereq("guerreiro_ofe_17")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_20").name("Execução")
                                .desc("Ativa: dano alto em alvo abaixo de 30% de vida.").branch(O).type(SkillType.ATIVA)
                                .grid(4, 10).prereq("guerreiro_ofe_18", "guerreiro_ofe_19").build());
                tree.add(SkillNode.builder("guerreiro_ofe_21").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(5, 11).prereq("guerreiro_ofe_20")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_22").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(3, 11).prereq("guerreiro_ofe_20").build());
                tree.add(SkillNode.builder("guerreiro_ofe_23").name("+2% Velocidade de Ataque")
                                .desc("+2% de velocidade de ataque.").branch(O).grid(5, 12).prereq("guerreiro_ofe_21")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_24").name("Fúria Cega")
                                .desc("Ativa: +dano e +vel. ataque por 8s, +20% dano recebido. (CD 20s)").branch(O)
                                .type(SkillType.ATIVA).grid(3, 12).prereq("guerreiro_ofe_22").build());
                tree.add(SkillNode.builder("guerreiro_ofe_25").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(5, 13).prereq("guerreiro_ofe_23")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_26").name("+3% Chance de Crítico")
                                .desc("+3% de chance de crítico.").branch(O).grid(3, 13).prereq("guerreiro_ofe_24")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_27").name("+2% Velocidade de Ataque")
                                .desc("+2% de velocidade de ataque.").branch(O).grid(4, 14)
                                .prereq("guerreiro_ofe_25", "guerreiro_ofe_26").build());
                tree.add(SkillNode.builder("guerreiro_ofe_28").name("Corte Profundo")
                                .desc("Passiva: 15% chance de sangramento. (efeito dinâmico)").branch(O).grid(5, 15)
                                .prereq("guerreiro_ofe_27").build());
                tree.add(SkillNode.builder("guerreiro_ofe_29").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(3, 15).prereq("guerreiro_ofe_27")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_30").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(5, 16).prereq("guerreiro_ofe_28").build());
                tree.add(SkillNode.builder("guerreiro_ofe_31").name("Quebra-Ossos")
                                .desc("Ativa: -30% armadura do alvo por 8s.").branch(O).type(SkillType.ATIVA)
                                .grid(3, 16).prereq("guerreiro_ofe_29").build());
                tree.add(SkillNode.builder("guerreiro_ofe_32").name("+3% Dano com Espadas")
                                .desc("+3% de dano com espadas.").branch(O).grid(5, 17).prereq("guerreiro_ofe_30")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_33").name("+2% Velocidade de Ataque")
                                .desc("+2% de velocidade de ataque.").branch(O).grid(3, 17).prereq("guerreiro_ofe_31")
                                .build());
                tree.add(SkillNode.builder("guerreiro_ofe_34").name("+3% Chance de Crítico")
                                .desc("+3% de chance de crítico.").branch(O).grid(4, 18)
                                .prereq("guerreiro_ofe_32", "guerreiro_ofe_33").build());
                tree.add(SkillNode.builder("guerreiro_ofe_35").name("Massacre")
                                .desc("Ativa: cada kill reduz cooldowns em 2s. (6s)").branch(O).type(SkillType.ATIVA)
                                .grid(4, 19).prereq("guerreiro_ofe_34").build());
                tree.add(SkillNode.builder("guerreiro_ofe_36").name("Sede de Sangue")
                                .desc("Passiva: +dano conforme menos vida. (efeito dinâmico)").branch(O).grid(4, 20)
                                .prereq("guerreiro_ofe_35").build());

                SkillBranch U = SkillBranch.UTILIDADE;

                tree.add(SkillNode.builder("guerreiro_uti_01").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(U).grid(0, 4).prereq("guerreiro_central").build());
                tree.add(SkillNode.builder("guerreiro_uti_02").name("+3% Resistência a Knockback")
                                .desc("Reduz knockback em 3%.").branch(U).grid(0, 5).prereq("guerreiro_uti_01")
                                .build());
                tree.add(SkillNode.builder("guerreiro_uti_03").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 6).prereq("guerreiro_uti_02").build());
                tree.add(SkillNode.builder("guerreiro_uti_04").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(U).grid(-1, 7).prereq("guerreiro_uti_03").build());
                tree.add(SkillNode.builder("guerreiro_uti_05").name("+2% Redução de Dano")
                                .desc("Reduz 2% de dano recebido.").branch(U).grid(1, 7).prereq("guerreiro_uti_03")
                                .build());
                tree.add(SkillNode.builder("guerreiro_uti_06").name("Grito de Guerra")
                                .desc("Ativa: buff de ataque pra aliados. (8s)").branch(U).type(SkillType.ATIVA)
                                .grid(-1, 8).prereq("guerreiro_uti_04").build());
                tree.add(SkillNode.builder("guerreiro_uti_07").name("+3% Resistência a Knockback")
                                .desc("Reduz knockback em 3%.").branch(U).grid(1, 8).prereq("guerreiro_uti_05")
                                .build());
                tree.add(SkillNode.builder("guerreiro_uti_08").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 9).prereq("guerreiro_uti_06", "guerreiro_uti_07").build());
                tree.add(SkillNode.builder("guerreiro_uti_09").name("Impacto Sísmico")
                                .desc("Ativa: empurra inimigos próximos. (CD 15s)").branch(U).type(SkillType.ATIVA)
                                .grid(-1, 10).prereq("guerreiro_uti_08").build());
                tree.add(SkillNode.builder("guerreiro_uti_10").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(U).grid(1, 10).prereq("guerreiro_uti_08").build());
                tree.add(SkillNode.builder("guerreiro_uti_11").name("+2% Redução de Dano")
                                .desc("Reduz 2% de dano recebido.").branch(U).grid(-1, 11).prereq("guerreiro_uti_09")
                                .build());
                tree.add(SkillNode.builder("guerreiro_uti_12").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(1, 11).prereq("guerreiro_uti_10").build());
                tree.add(SkillNode.builder("guerreiro_uti_13").name("Provocar")
                                .desc("Ativa: força inimigos a te atacarem. (5s)").branch(U).type(SkillType.ATIVA)
                                .grid(0, 12).prereq("guerreiro_uti_11", "guerreiro_uti_12").build());
                tree.add(SkillNode.builder("guerreiro_uti_14").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(U).grid(-1, 13).prereq("guerreiro_uti_13").build());
                tree.add(SkillNode.builder("guerreiro_uti_15").name("+3% Resistência a Knockback")
                                .desc("Reduz knockback em 3%.").branch(U).grid(1, 13).prereq("guerreiro_uti_13")
                                .build());
                tree.add(SkillNode.builder("guerreiro_uti_16").name("Passo Pesado")
                                .desc("Passiva: reduz knockback recebido. (efeito dinâmico)").branch(U).grid(-1, 14)
                                .prereq("guerreiro_uti_14").build());
                tree.add(SkillNode.builder("guerreiro_uti_17").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(1, 14).prereq("guerreiro_uti_15").build());
                tree.add(SkillNode.builder("guerreiro_uti_18").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(U).grid(0, 15)
                                .prereq("guerreiro_uti_16", "guerreiro_uti_17").build());
                tree.add(SkillNode.builder("guerreiro_uti_19").name("Barreira de Aço")
                                .desc("Ativa: -40% dano por 5s. (CD 18s)").branch(U).type(SkillType.ATIVA).grid(-1, 16)
                                .prereq("guerreiro_uti_18").build());
                tree.add(SkillNode.builder("guerreiro_uti_20").name("+3% Resistência a Knockback")
                                .desc("Reduz knockback em 3%.").branch(U).grid(1, 16).prereq("guerreiro_uti_18")
                                .build());
                tree.add(SkillNode.builder("guerreiro_uti_21").name("+2% Redução de Dano")
                                .desc("Reduz 2% de dano recebido.").branch(U).grid(-1, 17).prereq("guerreiro_uti_19")
                                .build());
                tree.add(SkillNode.builder("guerreiro_uti_22").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(1, 17).prereq("guerreiro_uti_20").build());
                tree.add(SkillNode.builder("guerreiro_uti_23").name("Presença Intimidadora")
                                .desc("Passiva: inimigos -10% vel. ataque. (efeito dinâmico)").branch(U).grid(0, 18)
                                .prereq("guerreiro_uti_21", "guerreiro_uti_22").build());
                tree.add(SkillNode.builder("guerreiro_uti_24").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(U).grid(-1, 19).prereq("guerreiro_uti_23").build());
                tree.add(SkillNode.builder("guerreiro_uti_25").name("+3% Resistência a Knockback")
                                .desc("Reduz knockback em 3%.").branch(U).grid(1, 19).prereq("guerreiro_uti_23")
                                .build());
                tree.add(SkillNode.builder("guerreiro_uti_26").name("Investida")
                                .desc("Ativa: avança rapidamente causando dano. (CD 10s)").branch(U)
                                .type(SkillType.ATIVA).grid(-1, 20).prereq("guerreiro_uti_24").build());
                tree.add(SkillNode.builder("guerreiro_uti_27").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(1, 20).prereq("guerreiro_uti_25").build());
                tree.add(SkillNode.builder("guerreiro_uti_28").name("+2% Redução de Dano")
                                .desc("Reduz 2% de dano recebido.").branch(U).grid(0, 21)
                                .prereq("guerreiro_uti_26", "guerreiro_uti_27").build());
                tree.add(SkillNode.builder("guerreiro_uti_29").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(U).grid(0, 22).prereq("guerreiro_uti_28").build());
                tree.add(SkillNode.builder("guerreiro_uti_30").name("Último Resistente")
                                .desc("Passiva: Resistência II + Força I abaixo de 25%. (efeito dinâmico)").branch(U)
                                .grid(0, 23).prereq("guerreiro_uti_29").build());

                return tree;
        }

        // =====================================================================
        // ARQUEIRO
        // =====================================================================
        private static SkillTree buildArqueiro() {
                SkillTree tree = new SkillTree(ClassType.ARQUEIRO);

                tree.add(SkillNode.builder("arqueiro_central")
                                .name("Olhar do Caçador")
                                .desc("Passiva base: +20% dano com arcos, +10% velocidade de movimento.")
                                .branch(SkillBranch.OFENSIVO).type(SkillType.PASSIVA)
                                .cost(0).grid(0, 0).build());

                SkillBranch P = SkillBranch.OFENSIVO;

                tree.add(SkillNode.builder("arqueiro_pre_01").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(4, -2).prereq("arqueiro_central").build());
                tree.add(SkillNode.builder("arqueiro_pre_02").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico com arco.").branch(P).grid(4, -1)
                                .prereq("arqueiro_pre_01").build());
                tree.add(SkillNode.builder("arqueiro_pre_03").name("+2% Velocidade de Ataque com Arco")
                                .desc("+2% de velocidade de ataque com arco.").branch(P).grid(4, 0)
                                .prereq("arqueiro_pre_02").build());
                tree.add(SkillNode.builder("arqueiro_pre_04").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(5, 1).prereq("arqueiro_pre_03").build());
                tree.add(SkillNode.builder("arqueiro_pre_05").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(P).grid(3, 1).prereq("arqueiro_pre_03")
                                .build());
                tree.add(SkillNode.builder("arqueiro_pre_06").name("+0.5 Coração").desc("+1 de vida máxima.").branch(P)
                                .grid(5, 2).prereq("arqueiro_pre_04").build());
                tree.add(SkillNode.builder("arqueiro_pre_07").name("Olho de Águia")
                                .desc("Passiva: +20% de dano com Arcos.").branch(P).grid(3, 2).prereq("arqueiro_pre_05")
                                .build());
                tree.add(SkillNode.builder("arqueiro_pre_08").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(5, 3).prereq("arqueiro_pre_06").build());
                tree.add(SkillNode.builder("arqueiro_pre_09").name("+2% Velocidade de Ataque com Arco")
                                .desc("+2% de velocidade.").branch(P).grid(3, 3).prereq("arqueiro_pre_07").build());
                tree.add(SkillNode.builder("arqueiro_pre_10").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(P).grid(4, 4)
                                .prereq("arqueiro_pre_08", "arqueiro_pre_09").build());
                tree.add(SkillNode.builder("arqueiro_pre_11").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(5, 5).prereq("arqueiro_pre_10").build());
                tree.add(SkillNode.builder("arqueiro_pre_12").name("Tiro Preciso")
                                .desc("Ativa: próximo tiro é crítico garantido. (CD 10s)").branch(P)
                                .type(SkillType.ATIVA).grid(3, 5).prereq("arqueiro_pre_10").build());
                tree.add(SkillNode.builder("arqueiro_pre_13").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(5, 6).prereq("arqueiro_pre_11").build());
                tree.add(SkillNode.builder("arqueiro_pre_14").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(P).grid(3, 6).prereq("arqueiro_pre_12")
                                .build());
                tree.add(SkillNode.builder("arqueiro_pre_15").name("Precisão Letal")
                                .desc("Passiva: +15% de chance de crítico com arco.").branch(P).grid(5, 7)
                                .prereq("arqueiro_pre_13").build());
                tree.add(SkillNode.builder("arqueiro_pre_16").name("+2% Velocidade de Ataque com Arco")
                                .desc("+2% de velocidade.").branch(P).grid(3, 7).prereq("arqueiro_pre_14").build());
                tree.add(SkillNode.builder("arqueiro_pre_17").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(5, 8).prereq("arqueiro_pre_15").build());
                tree.add(SkillNode.builder("arqueiro_pre_18").name("+0.5 Coração").desc("+1 de vida máxima.").branch(P)
                                .grid(3, 8).prereq("arqueiro_pre_16").build());
                tree.add(SkillNode.builder("arqueiro_pre_19").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(P).grid(4, 9)
                                .prereq("arqueiro_pre_17", "arqueiro_pre_18").build());
                tree.add(SkillNode.builder("arqueiro_pre_20").name("Flecha Perfurante")
                                .desc("Passiva: flechas ignoram 20% da armadura.").branch(P).grid(5, 10)
                                .prereq("arqueiro_pre_19").build());
                tree.add(SkillNode.builder("arqueiro_pre_21").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(3, 10).prereq("arqueiro_pre_19").build());
                tree.add(SkillNode.builder("arqueiro_pre_22").name("+2% Velocidade de Ataque com Arco")
                                .desc("+2% de velocidade.").branch(P).grid(5, 11).prereq("arqueiro_pre_20").build());
                tree.add(SkillNode.builder("arqueiro_pre_23").name("Tiro na Cabeça")
                                .desc("Passiva: críticos com arco +30% dano.").branch(P).grid(3, 11)
                                .prereq("arqueiro_pre_21").build());
                tree.add(SkillNode.builder("arqueiro_pre_24").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(4, 12).prereq("arqueiro_pre_22", "arqueiro_pre_23").build());
                tree.add(SkillNode.builder("arqueiro_pre_25").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(P).grid(5, 13).prereq("arqueiro_pre_24")
                                .build());
                tree.add(SkillNode.builder("arqueiro_pre_26").name("Disparo Concentrado")
                                .desc("Ativa: próximo tiro +dano e empurra. (CD 12s)").branch(P).type(SkillType.ATIVA)
                                .grid(3, 13).prereq("arqueiro_pre_24").build());
                tree.add(SkillNode.builder("arqueiro_pre_27").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(5, 14).prereq("arqueiro_pre_25").build());
                tree.add(SkillNode.builder("arqueiro_pre_28").name("+2% Velocidade de Ataque com Arco")
                                .desc("+2% de velocidade.").branch(P).grid(3, 14).prereq("arqueiro_pre_26").build());
                tree.add(SkillNode.builder("arqueiro_pre_29").name("+0.5 Coração").desc("+1 de vida máxima.").branch(P)
                                .grid(4, 15).prereq("arqueiro_pre_27", "arqueiro_pre_28").build());
                tree.add(SkillNode.builder("arqueiro_pre_30").name("Olho de Falcão")
                                .desc("Passiva: aumenta o alcance dos arcos.").branch(P).grid(5, 16)
                                .prereq("arqueiro_pre_29").build());
                tree.add(SkillNode.builder("arqueiro_pre_31").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(3, 16).prereq("arqueiro_pre_29").build());
                tree.add(SkillNode.builder("arqueiro_pre_32").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(P).grid(5, 17).prereq("arqueiro_pre_30")
                                .build());
                tree.add(SkillNode.builder("arqueiro_pre_33").name("Morte Silenciosa")
                                .desc("Passiva: críticos 20% chance de silenciar. (efeito dinâmico)").branch(P)
                                .grid(3, 17).prereq("arqueiro_pre_31").build());
                tree.add(SkillNode.builder("arqueiro_pre_34").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(P).grid(4, 18).prereq("arqueiro_pre_32", "arqueiro_pre_33").build());
                tree.add(SkillNode.builder("arqueiro_pre_35").name("+2% Velocidade de Ataque com Arco")
                                .desc("+2% de velocidade.").branch(P).grid(4, 19).prereq("arqueiro_pre_34").build());
                tree.add(SkillNode.builder("arqueiro_pre_36").name("Tiro Fatal")
                                .desc("Ativa: executa alvo abaixo de 25% de vida.").branch(P).type(SkillType.ATIVA)
                                .grid(4, 20).prereq("arqueiro_pre_35").build());

                SkillBranch M = SkillBranch.UTILIDADE;

                tree.add(SkillNode.builder("arqueiro_mob_01").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(0, 4).prereq("arqueiro_central").build());
                tree.add(SkillNode.builder("arqueiro_mob_02").name("+1 Bloco de Altura de Queda")
                                .desc("Reduz dano de queda em 1 bloco.").branch(M).grid(0, 5).prereq("arqueiro_mob_01")
                                .build());
                tree.add(SkillNode.builder("arqueiro_mob_03").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(0, 6).prereq("arqueiro_mob_02").build());
                tree.add(SkillNode.builder("arqueiro_mob_04").name("+2% Resistência a Knockback")
                                .desc("Reduz knockback em 2%.").branch(M).grid(-1, 7).prereq("arqueiro_mob_03")
                                .build());
                tree.add(SkillNode.builder("arqueiro_mob_05").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(1, 7).prereq("arqueiro_mob_03").build());
                tree.add(SkillNode.builder("arqueiro_mob_06").name("Passo Sombrio")
                                .desc("Passiva: +10% de velocidade de movimento.").branch(M).grid(-1, 8)
                                .prereq("arqueiro_mob_04").build());
                tree.add(SkillNode.builder("arqueiro_mob_07").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(1, 8).prereq("arqueiro_mob_05").build());
                tree.add(SkillNode.builder("arqueiro_mob_08").name("+1 Bloco de Altura de Queda")
                                .desc("Reduz dano de queda em 1 bloco.").branch(M).grid(0, 9)
                                .prereq("arqueiro_mob_06", "arqueiro_mob_07").build());
                tree.add(SkillNode.builder("arqueiro_mob_09").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(-1, 10).prereq("arqueiro_mob_08").build());
                tree.add(SkillNode.builder("arqueiro_mob_10").name("Fuga Rápida")
                                .desc("Ativa: Speed II + Jump Boost por 5s. (CD 14s)").branch(M).type(SkillType.ATIVA)
                                .grid(1, 10).prereq("arqueiro_mob_08").build());
                tree.add(SkillNode.builder("arqueiro_mob_11").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(-1, 11).prereq("arqueiro_mob_09").build());
                tree.add(SkillNode.builder("arqueiro_mob_12").name("+2% Resistência a Knockback")
                                .desc("Reduz knockback em 2%.").branch(M).grid(1, 11).prereq("arqueiro_mob_10")
                                .build());
                tree.add(SkillNode.builder("arqueiro_mob_13").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(0, 12)
                                .prereq("arqueiro_mob_11", "arqueiro_mob_12").build());
                tree.add(SkillNode.builder("arqueiro_mob_14").name("Camuflagem")
                                .desc("Passiva: semi-invisível quando parado.").branch(M).grid(-1, 13)
                                .prereq("arqueiro_mob_13").build());
                tree.add(SkillNode.builder("arqueiro_mob_15").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(1, 13).prereq("arqueiro_mob_13").build());
                tree.add(SkillNode.builder("arqueiro_mob_16").name("+1 Bloco de Altura de Queda")
                                .desc("Reduz dano de queda em 1 bloco.").branch(M).grid(-1, 14)
                                .prereq("arqueiro_mob_14").build());
                tree.add(SkillNode.builder("arqueiro_mob_17").name("Passo Leve")
                                .desc("Passiva: reduz o barulho dos passos.").branch(M).grid(1, 14)
                                .prereq("arqueiro_mob_15").build());
                tree.add(SkillNode.builder("arqueiro_mob_18").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(0, 15)
                                .prereq("arqueiro_mob_16", "arqueiro_mob_17").build());
                tree.add(SkillNode.builder("arqueiro_mob_19").name("+2% Resistência a Knockback")
                                .desc("Reduz knockback em 2%.").branch(M).grid(-1, 16).prereq("arqueiro_mob_18")
                                .build());
                tree.add(SkillNode.builder("arqueiro_mob_20").name("Corrida do Vento")
                                .desc("Ativa: +velocidade por 4s. (CD 16s)").branch(M).type(SkillType.ATIVA).grid(1, 16)
                                .prereq("arqueiro_mob_18").build());
                tree.add(SkillNode.builder("arqueiro_mob_21").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(-1, 17).prereq("arqueiro_mob_19").build());
                tree.add(SkillNode.builder("arqueiro_mob_22").name("+1 Bloco de Altura de Queda")
                                .desc("Reduz dano de queda em 1 bloco.").branch(M).grid(1, 17).prereq("arqueiro_mob_20")
                                .build());
                tree.add(SkillNode.builder("arqueiro_mob_23").name("Esquiva Instantânea")
                                .desc("Ativa: dash curto. (CD 8s)").branch(M).type(SkillType.ATIVA).grid(0, 18)
                                .prereq("arqueiro_mob_21", "arqueiro_mob_22").build());
                tree.add(SkillNode.builder("arqueiro_mob_24").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(-1, 19).prereq("arqueiro_mob_23").build());
                tree.add(SkillNode.builder("arqueiro_mob_25").name("+2% Resistência a Knockback")
                                .desc("Reduz knockback em 2%.").branch(M).grid(1, 19).prereq("arqueiro_mob_23")
                                .build());
                tree.add(SkillNode.builder("arqueiro_mob_26").name("Reflexos Felinos")
                                .desc("Passiva: +15% chance de evitar ataques à distância. (efeito dinâmico)").branch(M)
                                .grid(-1, 20).prereq("arqueiro_mob_24").build());
                tree.add(SkillNode.builder("arqueiro_mob_27").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(1, 20).prereq("arqueiro_mob_25").build());
                tree.add(SkillNode.builder("arqueiro_mob_28").name("+1 Bloco de Altura de Queda")
                                .desc("Reduz dano de queda em 1 bloco.").branch(M).grid(0, 21)
                                .prereq("arqueiro_mob_26", "arqueiro_mob_27").build());
                tree.add(SkillNode.builder("arqueiro_mob_29").name("+2% Resistência a Knockback")
                                .desc("Reduz knockback em 2%.").branch(M).grid(0, 22).prereq("arqueiro_mob_28")
                                .build());
                tree.add(SkillNode.builder("arqueiro_mob_30").name("Salto da Sombra")
                                .desc("Ativa: teleporta curta distância. (CD 12s)").branch(M).type(SkillType.ATIVA)
                                .grid(-1, 23).prereq("arqueiro_mob_29").build());
                tree.add(SkillNode.builder("arqueiro_mob_31").name("+3% Velocidade de Movimento")
                                .desc("+3% de velocidade.").branch(M).grid(1, 23).prereq("arqueiro_mob_29").build());
                tree.add(SkillNode.builder("arqueiro_mob_32").name("Espírito Livre")
                                .desc("Passiva: remove lentidão a cada 15s. (efeito dinâmico)").branch(M).grid(0, 24)
                                .prereq("arqueiro_mob_30", "arqueiro_mob_31").build());

                SkillBranch E = SkillBranch.DEFESA;

                tree.add(SkillNode.builder("arqueiro_esp_01").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-4, -2).prereq("arqueiro_central").build());
                tree.add(SkillNode.builder("arqueiro_esp_02").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(E).grid(-4, -1).prereq("arqueiro_esp_01")
                                .build());
                tree.add(SkillNode.builder("arqueiro_esp_03").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-4, 0).prereq("arqueiro_esp_02").build());
                tree.add(SkillNode.builder("arqueiro_esp_04").name("+0.5 Coração").desc("+1 de vida máxima.").branch(E)
                                .grid(-5, 1).prereq("arqueiro_esp_03").build());
                tree.add(SkillNode.builder("arqueiro_esp_05").name("Flecha Envenenada")
                                .desc("Passiva: flechas aplicam veneno 4s.").branch(E).grid(-3, 1)
                                .prereq("arqueiro_esp_03").build());
                tree.add(SkillNode.builder("arqueiro_esp_06").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-5, 2).prereq("arqueiro_esp_04").build());
                tree.add(SkillNode.builder("arqueiro_esp_07").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(E).grid(-3, 2).prereq("arqueiro_esp_05")
                                .build());
                tree.add(SkillNode.builder("arqueiro_esp_08").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-5, 3).prereq("arqueiro_esp_06").build());
                tree.add(SkillNode.builder("arqueiro_esp_09").name("Tiro Múltiplo")
                                .desc("Ativa: atira 3 flechas. (CD 14s)").branch(E).type(SkillType.ATIVA).grid(-3, 3)
                                .prereq("arqueiro_esp_07").build());
                tree.add(SkillNode.builder("arqueiro_esp_10").name("+0.5 Coração").desc("+1 de vida máxima.").branch(E)
                                .grid(-4, 4).prereq("arqueiro_esp_08", "arqueiro_esp_09").build());
                tree.add(SkillNode.builder("arqueiro_esp_11").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-5, 5).prereq("arqueiro_esp_10").build());
                tree.add(SkillNode.builder("arqueiro_esp_12").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(E).grid(-3, 5).prereq("arqueiro_esp_10")
                                .build());
                tree.add(SkillNode.builder("arqueiro_esp_13").name("Chuva de Flechas")
                                .desc("Ativa: chove flechas em área. (CD 20s)").branch(E).type(SkillType.ATIVA)
                                .grid(-5, 6).prereq("arqueiro_esp_11").build());
                tree.add(SkillNode.builder("arqueiro_esp_14").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-3, 6).prereq("arqueiro_esp_12").build());
                tree.add(SkillNode.builder("arqueiro_esp_15").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(E).grid(-5, 7).prereq("arqueiro_esp_13")
                                .build());
                tree.add(SkillNode.builder("arqueiro_esp_16").name("Flecha Explosiva")
                                .desc("Ativa: próxima flecha explode. (CD 15s)").branch(E).type(SkillType.ATIVA)
                                .grid(-3, 7).prereq("arqueiro_esp_14").build());
                tree.add(SkillNode.builder("arqueiro_esp_17").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-5, 8).prereq("arqueiro_esp_15").build());
                tree.add(SkillNode.builder("arqueiro_esp_18").name("+0.5 Coração").desc("+1 de vida máxima.").branch(E)
                                .grid(-3, 8).prereq("arqueiro_esp_16").build());
                tree.add(SkillNode.builder("arqueiro_esp_19").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(E).grid(-4, 9)
                                .prereq("arqueiro_esp_17", "arqueiro_esp_18").build());
                tree.add(SkillNode.builder("arqueiro_esp_20").name("Marcação")
                                .desc("Ativa: alvo recebe +20% dano por 8s.").branch(E).type(SkillType.ATIVA)
                                .grid(-5, 10).prereq("arqueiro_esp_19").build());
                tree.add(SkillNode.builder("arqueiro_esp_21").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-3, 10).prereq("arqueiro_esp_19").build());
                tree.add(SkillNode.builder("arqueiro_esp_22").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(E).grid(-5, 11).prereq("arqueiro_esp_20")
                                .build());
                tree.add(SkillNode.builder("arqueiro_esp_23").name("Flecha de Gelo")
                                .desc("Passiva: flechas aplicam lentidão. (efeito dinâmico)").branch(E).grid(-3, 11)
                                .prereq("arqueiro_esp_21").build());
                tree.add(SkillNode.builder("arqueiro_esp_24").name("+0.5 Coração").desc("+1 de vida máxima.").branch(E)
                                .grid(-4, 12).prereq("arqueiro_esp_22", "arqueiro_esp_23").build());
                tree.add(SkillNode.builder("arqueiro_esp_25").name("Tiro em Rajada")
                                .desc("Ativa: 5 flechas rápidas. (CD 18s)").branch(E).type(SkillType.ATIVA).grid(-5, 13)
                                .prereq("arqueiro_esp_24").build());
                tree.add(SkillNode.builder("arqueiro_esp_26").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-3, 13).prereq("arqueiro_esp_24").build());
                tree.add(SkillNode.builder("arqueiro_esp_27").name("+2% Chance de Crítico")
                                .desc("+2% de chance de crítico.").branch(E).grid(-5, 14).prereq("arqueiro_esp_25")
                                .build());
                tree.add(SkillNode.builder("arqueiro_esp_28").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-3, 14).prereq("arqueiro_esp_26").build());
                tree.add(SkillNode.builder("arqueiro_esp_29").name("Caçador Noturno")
                                .desc("Passiva: +15% de dano durante a noite.").branch(E).grid(-4, 15)
                                .prereq("arqueiro_esp_27", "arqueiro_esp_28").build());
                tree.add(SkillNode.builder("arqueiro_esp_30").name("+0.5 Coração").desc("+1 de vida máxima.").branch(E)
                                .grid(-5, 16).prereq("arqueiro_esp_29").build());
                tree.add(SkillNode.builder("arqueiro_esp_31").name("+3% Dano com Arco").desc("+3% de dano com arcos.")
                                .branch(E).grid(-3, 16).prereq("arqueiro_esp_29").build());
                tree.add(SkillNode.builder("arqueiro_esp_32").name("Mestre Arqueiro")
                                .desc("Passiva: flechas +10% velocidade e alcance.").branch(E).grid(-4, 17)
                                .prereq("arqueiro_esp_30", "arqueiro_esp_31").build());

                return tree;
        }

        // =====================================================================
        // MAGO
        // =====================================================================
        private static SkillTree buildMago() {
                SkillTree tree = new SkillTree(ClassType.MAGO);

                tree.add(SkillNode.builder("mago_central")
                                .name("Sangue Arcano")
                                .desc("Passiva base: +20% dano mágico, +15% redução de cooldown.")
                                .branch(SkillBranch.OFENSIVO).type(SkillType.PASSIVA)
                                .cost(0).grid(0, 0).build());

                SkillBranch O = SkillBranch.OFENSIVO;

                tree.add(SkillNode.builder("mago_pod_01").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(4, -2).prereq("mago_central").build());
                tree.add(SkillNode.builder("mago_pod_02").name("+2% Redução de Cooldown")
                                .desc("-2% de cooldown de skills.").branch(O).grid(4, -1).prereq("mago_pod_01")
                                .build());
                tree.add(SkillNode.builder("mago_pod_03").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 0).prereq("mago_pod_02").build());
                tree.add(SkillNode.builder("mago_pod_04").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(5, 1).prereq("mago_pod_03").build());
                tree.add(SkillNode.builder("mago_pod_05").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(O).grid(3, 1).prereq("mago_pod_03").build());
                tree.add(SkillNode.builder("mago_pod_06").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(5, 2).prereq("mago_pod_04").build());
                tree.add(SkillNode.builder("mago_pod_07").name("Afinidade Arcana").desc("Passiva: +15% de dano mágico.")
                                .branch(O).grid(3, 2).prereq("mago_pod_05").build());
                tree.add(SkillNode.builder("mago_pod_08").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(5, 3).prereq("mago_pod_06").build());
                tree.add(SkillNode.builder("mago_pod_09").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(O).grid(3, 3).prereq("mago_pod_07").build());
                tree.add(SkillNode.builder("mago_pod_10").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 4).prereq("mago_pod_08", "mago_pod_09").build());
                tree.add(SkillNode.builder("mago_pod_11").name("Mente Clara")
                                .desc("Passiva: -20% de cooldown de todas as skills.").branch(O).grid(5, 5)
                                .prereq("mago_pod_10").build());
                tree.add(SkillNode.builder("mago_pod_12").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(3, 5).prereq("mago_pod_10").build());
                tree.add(SkillNode.builder("mago_pod_13").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(O).grid(5, 6).prereq("mago_pod_11").build());
                tree.add(SkillNode.builder("mago_pod_14").name("Poder das Runas")
                                .desc("Passiva: +10% dano mágico por skill desbloqueada. (efeito dinâmico)").branch(O)
                                .grid(3, 6).prereq("mago_pod_12").build());
                tree.add(SkillNode.builder("mago_pod_15").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(5, 7).prereq("mago_pod_13").build());
                tree.add(SkillNode.builder("mago_pod_16").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(3, 7).prereq("mago_pod_14").build());
                tree.add(SkillNode.builder("mago_pod_17").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(O).grid(5, 8).prereq("mago_pod_15").build());
                tree.add(SkillNode.builder("mago_pod_18").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(3, 8).prereq("mago_pod_16").build());
                tree.add(SkillNode.builder("mago_pod_19").name("Tempestade Arcana")
                                .desc("Ativa: raios em área. (CD 25s)").branch(O).type(SkillType.ATIVA).grid(4, 9)
                                .prereq("mago_pod_17", "mago_pod_18").build());
                tree.add(SkillNode.builder("mago_pod_20").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(5, 10).prereq("mago_pod_19").build());
                tree.add(SkillNode.builder("mago_pod_21").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(O).grid(3, 10).prereq("mago_pod_19").build());
                tree.add(SkillNode.builder("mago_pod_22").name("Orbe Arcano")
                                .desc("Ativa: orbe que explode ao contato.").branch(O).type(SkillType.ATIVA).grid(5, 11)
                                .prereq("mago_pod_20").build());
                tree.add(SkillNode.builder("mago_pod_23").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(3, 11).prereq("mago_pod_21").build());
                tree.add(SkillNode.builder("mago_pod_24").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 12).prereq("mago_pod_22", "mago_pod_23").build());
                tree.add(SkillNode.builder("mago_pod_25").name("Sobrecarga")
                                .desc("Passiva: 15% chance de não gastar cooldown. (efeito dinâmico)").branch(O)
                                .grid(5, 13).prereq("mago_pod_24").build());
                tree.add(SkillNode.builder("mago_pod_26").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(3, 13).prereq("mago_pod_24").build());
                tree.add(SkillNode.builder("mago_pod_27").name("Chuva de Meteoros")
                                .desc("Ativa: pequenos meteoros em área. (CD 22s)").branch(O).type(SkillType.ATIVA)
                                .grid(5, 14).prereq("mago_pod_25").build());
                tree.add(SkillNode.builder("mago_pod_28").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(O).grid(3, 14).prereq("mago_pod_26").build());
                tree.add(SkillNode.builder("mago_pod_29").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 15).prereq("mago_pod_27", "mago_pod_28").build());
                tree.add(SkillNode.builder("mago_pod_30").name("Catalisador")
                                .desc("Passiva: +dano conforme mana gasta. (efeito dinâmico)").branch(O).grid(5, 16)
                                .prereq("mago_pod_29").build());
                tree.add(SkillNode.builder("mago_pod_31").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(3, 16).prereq("mago_pod_29").build());
                tree.add(SkillNode.builder("mago_pod_32").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(O).grid(5, 17).prereq("mago_pod_30").build());
                tree.add(SkillNode.builder("mago_pod_33").name("Explosão de Mana")
                                .desc("Ativa: explosão grande ao redor.").branch(O).type(SkillType.ATIVA).grid(3, 17)
                                .prereq("mago_pod_31").build());
                tree.add(SkillNode.builder("mago_pod_34").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(O)
                                .grid(5, 18).prereq("mago_pod_32").build());
                tree.add(SkillNode.builder("mago_pod_35").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(3, 18).prereq("mago_pod_33").build());
                tree.add(SkillNode.builder("mago_pod_36").name("Arquimago")
                                .desc("Passiva: +25% dano mágico acima de 70% de vida. (efeito dinâmico)").branch(O)
                                .grid(4, 19).prereq("mago_pod_34", "mago_pod_35").build());

                SkillBranch D = SkillBranch.DEFESA;

                tree.add(SkillNode.builder("mago_def_01").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-4, -2).prereq("mago_central").build());
                tree.add(SkillNode.builder("mago_def_02").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-4, -1).prereq("mago_def_01").build());
                tree.add(SkillNode.builder("mago_def_03").name("+3% Redução de Dano").desc("Reduz 3% de dano.")
                                .branch(D).grid(-4, 0).prereq("mago_def_02").build());
                tree.add(SkillNode.builder("mago_def_04").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 1).prereq("mago_def_03").build());
                tree.add(SkillNode.builder("mago_def_05").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-3, 1).prereq("mago_def_03").build());
                tree.add(SkillNode.builder("mago_def_06").name("Escudo Místico")
                                .desc("Passiva: absorção ao tomar dano. (CD 15s) (efeito dinâmico)").branch(D)
                                .grid(-5, 2).prereq("mago_def_04").build());
                tree.add(SkillNode.builder("mago_def_07").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 2).prereq("mago_def_05").build());
                tree.add(SkillNode.builder("mago_def_08").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-5, 3).prereq("mago_def_06").build());
                tree.add(SkillNode.builder("mago_def_09").name("+3% Redução de Dano").desc("Reduz 3% de dano.")
                                .branch(D).grid(-3, 3).prereq("mago_def_07").build());
                tree.add(SkillNode.builder("mago_def_10").name("Barreira Arcana")
                                .desc("Ativa: bloqueia projéteis por 6s.").branch(D).type(SkillType.ATIVA).grid(-4, 4)
                                .prereq("mago_def_08", "mago_def_09").build());
                tree.add(SkillNode.builder("mago_def_11").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 5).prereq("mago_def_10").build());
                tree.add(SkillNode.builder("mago_def_12").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-3, 5).prereq("mago_def_10").build());
                tree.add(SkillNode.builder("mago_def_13").name("Drenar Essência")
                                .desc("Passiva: cura 1 coração a cada 3 feitiços. (efeito dinâmico)").branch(D)
                                .grid(-5, 6).prereq("mago_def_11").build());
                tree.add(SkillNode.builder("mago_def_14").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 6).prereq("mago_def_12").build());
                tree.add(SkillNode.builder("mago_def_15").name("+3% Redução de Dano").desc("Reduz 3% de dano.")
                                .branch(D).grid(-5, 7).prereq("mago_def_13").build());
                tree.add(SkillNode.builder("mago_def_16").name("Escudo de Mana")
                                .desc("Ativa: consome mana para escudo forte.").branch(D).type(SkillType.ATIVA)
                                .grid(-3, 7).prereq("mago_def_14").build());
                tree.add(SkillNode.builder("mago_def_17").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-5, 8).prereq("mago_def_15").build());
                tree.add(SkillNode.builder("mago_def_18").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 8).prereq("mago_def_16").build());
                tree.add(SkillNode.builder("mago_def_19").name("Absorção Arcana")
                                .desc("Passiva: parte do dano vira mana. (efeito dinâmico)").branch(D).grid(-4, 9)
                                .prereq("mago_def_17", "mago_def_18").build());
                tree.add(SkillNode.builder("mago_def_20").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-5, 10).prereq("mago_def_19").build());
                tree.add(SkillNode.builder("mago_def_21").name("+3% Redução de Dano").desc("Reduz 3% de dano.")
                                .branch(D).grid(-3, 10).prereq("mago_def_19").build());
                tree.add(SkillNode.builder("mago_def_22").name("Campo de Força")
                                .desc("Ativa: barreira que reduz dano em área.").branch(D).type(SkillType.ATIVA)
                                .grid(-5, 11).prereq("mago_def_20").build());
                tree.add(SkillNode.builder("mago_def_23").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 11).prereq("mago_def_21").build());
                tree.add(SkillNode.builder("mago_def_24").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-4, 12).prereq("mago_def_22", "mago_def_23").build());
                tree.add(SkillNode.builder("mago_def_25").name("Pele Mágica")
                                .desc("Passiva: +10% de resistência a dano mágico. (efeito dinâmico)").branch(D)
                                .grid(-5, 13).prereq("mago_def_24").build());
                tree.add(SkillNode.builder("mago_def_26").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 13).prereq("mago_def_24").build());
                tree.add(SkillNode.builder("mago_def_27").name("+3% Redução de Dano").desc("Reduz 3% de dano.")
                                .branch(D).grid(-5, 14).prereq("mago_def_25").build());
                tree.add(SkillNode.builder("mago_def_28").name("Reflexão")
                                .desc("Ativa: reflete o próximo projétil recebido.").branch(D).type(SkillType.ATIVA)
                                .grid(-3, 14).prereq("mago_def_26").build());
                tree.add(SkillNode.builder("mago_def_29").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-4, 15).prereq("mago_def_27", "mago_def_28").build());
                tree.add(SkillNode.builder("mago_def_30").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 16).prereq("mago_def_29").build());
                tree.add(SkillNode.builder("mago_def_31").name("Última Barreira")
                                .desc("Passiva: escudo forte abaixo de 20%. (efeito dinâmico)").branch(D).grid(-3, 16)
                                .prereq("mago_def_29").build());
                tree.add(SkillNode.builder("mago_def_32").name("Fortaleza Arcana")
                                .desc("Passiva: +2 Armadura e +1 Coração permanente.").branch(D).grid(-4, 17)
                                .prereq("mago_def_30", "mago_def_31").build());

                SkillBranch U = SkillBranch.UTILIDADE;

                tree.add(SkillNode.builder("mago_ctr_01").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(0, 4).prereq("mago_central").build());
                tree.add(SkillNode.builder("mago_ctr_02").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(U).grid(0, 5).prereq("mago_ctr_01").build());
                tree.add(SkillNode.builder("mago_ctr_03").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(0, 6).prereq("mago_ctr_02").build());
                tree.add(SkillNode.builder("mago_ctr_04").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(-1, 7).prereq("mago_ctr_03").build());
                tree.add(SkillNode.builder("mago_ctr_05").name("Chama Interior").desc("Ativa: bola de fogo pequena.")
                                .branch(U).type(SkillType.ATIVA).grid(1, 7).prereq("mago_ctr_03").build());
                tree.add(SkillNode.builder("mago_ctr_06").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(-1, 8).prereq("mago_ctr_04").build());
                tree.add(SkillNode.builder("mago_ctr_07").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(U).grid(1, 8).prereq("mago_ctr_05").build());
                tree.add(SkillNode.builder("mago_ctr_08").name("Congelamento").desc("Ativa: lentidão forte por 4s.")
                                .branch(U).type(SkillType.ATIVA).grid(-1, 9).prereq("mago_ctr_06").build());
                tree.add(SkillNode.builder("mago_ctr_09").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(1, 9).prereq("mago_ctr_07").build());
                tree.add(SkillNode.builder("mago_ctr_10").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 10).prereq("mago_ctr_08", "mago_ctr_09").build());
                tree.add(SkillNode.builder("mago_ctr_11").name("Explosão Rúnica")
                                .desc("Ativa: pequena explosão no local.").branch(U).type(SkillType.ATIVA).grid(-1, 11)
                                .prereq("mago_ctr_10").build());
                tree.add(SkillNode.builder("mago_ctr_12").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(1, 11).prereq("mago_ctr_10").build());
                tree.add(SkillNode.builder("mago_ctr_13").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(U).grid(-1, 12).prereq("mago_ctr_11").build());
                tree.add(SkillNode.builder("mago_ctr_14").name("Correntes Arcanas")
                                .desc("Ativa: prende alvo no lugar por 3s.").branch(U).type(SkillType.ATIVA).grid(1, 12)
                                .prereq("mago_ctr_12").build());
                tree.add(SkillNode.builder("mago_ctr_15").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 13).prereq("mago_ctr_13", "mago_ctr_14").build());
                tree.add(SkillNode.builder("mago_ctr_16").name("Nova de Gelo").desc("Ativa: congela inimigos próximos.")
                                .branch(U).type(SkillType.ATIVA).grid(-1, 14).prereq("mago_ctr_15").build());
                tree.add(SkillNode.builder("mago_ctr_17").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(1, 14).prereq("mago_ctr_15").build());
                tree.add(SkillNode.builder("mago_ctr_18").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(U).grid(-1, 15).prereq("mago_ctr_16").build());
                tree.add(SkillNode.builder("mago_ctr_19").name("Teleporte Curto")
                                .desc("Ativa: teleporta pequena distância.").branch(U).type(SkillType.ATIVA).grid(1, 15)
                                .prereq("mago_ctr_17").build());
                tree.add(SkillNode.builder("mago_ctr_20").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 16).prereq("mago_ctr_18", "mago_ctr_19").build());
                tree.add(SkillNode.builder("mago_ctr_21").name("Silêncio")
                                .desc("Ativa: impede alvo de usar habilidades por 4s.").branch(U).type(SkillType.ATIVA)
                                .grid(-1, 17).prereq("mago_ctr_20").build());
                tree.add(SkillNode.builder("mago_ctr_22").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(1, 17).prereq("mago_ctr_20").build());
                tree.add(SkillNode.builder("mago_ctr_23").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(U).grid(-1, 18).prereq("mago_ctr_21").build());
                tree.add(SkillNode.builder("mago_ctr_24").name("Campo de Gravidade")
                                .desc("Ativa: puxa inimigos para o centro.").branch(U).type(SkillType.ATIVA).grid(1, 18)
                                .prereq("mago_ctr_22").build());
                tree.add(SkillNode.builder("mago_ctr_25").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 19).prereq("mago_ctr_23", "mago_ctr_24").build());
                tree.add(SkillNode.builder("mago_ctr_26").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(-1, 20).prereq("mago_ctr_25").build());
                tree.add(SkillNode.builder("mago_ctr_27").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(U).grid(1, 20).prereq("mago_ctr_25").build());
                tree.add(SkillNode.builder("mago_ctr_28").name("Ilusão")
                                .desc("Ativa: cria ilusão que distrai inimigos.").branch(U).type(SkillType.ATIVA)
                                .grid(-1, 21).prereq("mago_ctr_26").build());
                tree.add(SkillNode.builder("mago_ctr_29").name("+3% Dano Mágico").desc("+3% de dano mágico.").branch(U)
                                .grid(1, 21).prereq("mago_ctr_27").build());
                tree.add(SkillNode.builder("mago_ctr_30").name("+2% Redução de Cooldown").desc("-2% de cooldown.")
                                .branch(U).grid(0, 22).prereq("mago_ctr_28", "mago_ctr_29").build());
                tree.add(SkillNode.builder("mago_ctr_31").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 23).prereq("mago_ctr_30").build());
                tree.add(SkillNode.builder("mago_ctr_32").name("Domínio Arcano")
                                .desc("Passiva: inimigos próximos -15% velocidade. (efeito dinâmico)").branch(U)
                                .grid(0, 24).prereq("mago_ctr_31").build());

                return tree;
        }

        // =====================================================================
        // SACERDOTE — completo (Cura = OFENSIVO, Proteção = DEFESA,
        // Combate Sagrado = UTILIDADE)
        // =====================================================================
        private static SkillTree buildSacerdote() {
                SkillTree tree = new SkillTree(ClassType.SACERDOTE);

                tree.add(SkillNode.builder("sacerdote_central")
                                .name("Graça Divina")
                                .desc("Passiva base: +20% poder de cura, +2 corações de vida máxima.")
                                .branch(SkillBranch.OFENSIVO).type(SkillType.PASSIVA)
                                .cost(0).grid(0, 0).build());

                // ================================================================
                // RAMO CURA (36 nós) → OFENSIVO, canto superior direito
                // ================================================================
                SkillBranch O = SkillBranch.OFENSIVO;

                tree.add(SkillNode.builder("sacerdote_cur_01").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(4, -2).prereq("sacerdote_central").build());
                tree.add(SkillNode.builder("sacerdote_cur_02").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, -1).prereq("sacerdote_cur_01").build());
                tree.add(SkillNode.builder("sacerdote_cur_03").name("+2% Redução de Cooldown de Cura")
                                .desc("-2% de cooldown de skills de cura.").branch(O).grid(4, 0)
                                .prereq("sacerdote_cur_02").build());
                tree.add(SkillNode.builder("sacerdote_cur_04").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(5, 1).prereq("sacerdote_cur_03").build());
                tree.add(SkillNode.builder("sacerdote_cur_05").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(3, 1).prereq("sacerdote_cur_03").build());
                tree.add(SkillNode.builder("sacerdote_cur_06").name("Bênção Divina")
                                .desc("Passiva: regenera vida fora de combate. (efeito dinâmico)").branch(O).grid(5, 2)
                                .prereq("sacerdote_cur_04").build());
                tree.add(SkillNode.builder("sacerdote_cur_07").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(3, 2).prereq("sacerdote_cur_05").build());
                tree.add(SkillNode.builder("sacerdote_cur_08").name("+2% Redução de Cooldown de Cura")
                                .desc("-2% de cooldown de cura.").branch(O).grid(5, 3).prereq("sacerdote_cur_06")
                                .build());
                tree.add(SkillNode.builder("sacerdote_cur_09").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(3, 3).prereq("sacerdote_cur_07").build());
                tree.add(SkillNode.builder("sacerdote_cur_10").name("Toque Sagrado").desc("Ativa: cura 4 corações.")
                                .branch(O).type(SkillType.ATIVA).grid(4, 4)
                                .prereq("sacerdote_cur_08", "sacerdote_cur_09").build());
                tree.add(SkillNode.builder("sacerdote_cur_11").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(5, 5).prereq("sacerdote_cur_10").build());
                tree.add(SkillNode.builder("sacerdote_cur_12").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(3, 5).prereq("sacerdote_cur_10").build());
                tree.add(SkillNode.builder("sacerdote_cur_13").name("Luz Restauradora")
                                .desc("Ativa: cura em área. (CD 12s)").branch(O).type(SkillType.ATIVA).grid(5, 6)
                                .prereq("sacerdote_cur_11").build());
                tree.add(SkillNode.builder("sacerdote_cur_14").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(3, 6).prereq("sacerdote_cur_12").build());
                tree.add(SkillNode.builder("sacerdote_cur_15").name("+2% Redução de Cooldown de Cura")
                                .desc("-2% de cooldown de cura.").branch(O).grid(5, 7).prereq("sacerdote_cur_13")
                                .build());
                tree.add(SkillNode.builder("sacerdote_cur_16").name("Fé Inabalável")
                                .desc("Passiva: -15% de cooldown de skills de cura.").branch(O).grid(3, 7)
                                .prereq("sacerdote_cur_14").build());
                tree.add(SkillNode.builder("sacerdote_cur_17").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(5, 8).prereq("sacerdote_cur_15").build());
                tree.add(SkillNode.builder("sacerdote_cur_18").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(3, 8).prereq("sacerdote_cur_16").build());
                tree.add(SkillNode.builder("sacerdote_cur_19").name("Cura em Massa")
                                .desc("Ativa: cura forte em área maior.").branch(O).type(SkillType.ATIVA).grid(4, 9)
                                .prereq("sacerdote_cur_17", "sacerdote_cur_18").build());
                tree.add(SkillNode.builder("sacerdote_cur_20").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(5, 10).prereq("sacerdote_cur_19").build());
                tree.add(SkillNode.builder("sacerdote_cur_21").name("+2% Redução de Cooldown de Cura")
                                .desc("-2% de cooldown de cura.").branch(O).grid(3, 10).prereq("sacerdote_cur_19")
                                .build());
                tree.add(SkillNode.builder("sacerdote_cur_22").name("Renovação")
                                .desc("Passiva: curas 20% chance de curar de novo após 2s. (efeito dinâmico)").branch(O)
                                .grid(5, 11).prereq("sacerdote_cur_20").build());
                tree.add(SkillNode.builder("sacerdote_cur_23").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(3, 11).prereq("sacerdote_cur_21").build());
                tree.add(SkillNode.builder("sacerdote_cur_24").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 12).prereq("sacerdote_cur_22", "sacerdote_cur_23").build());
                tree.add(SkillNode.builder("sacerdote_cur_25").name("Fonte da Vida")
                                .desc("Ativa: zona que cura aliados periodicamente.").branch(O).type(SkillType.ATIVA)
                                .grid(5, 13).prereq("sacerdote_cur_24").build());
                tree.add(SkillNode.builder("sacerdote_cur_26").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(3, 13).prereq("sacerdote_cur_24").build());
                tree.add(SkillNode.builder("sacerdote_cur_27").name("Graça Persistente")
                                .desc("Passiva: efeitos de cura duram mais. (efeito dinâmico)").branch(O).grid(5, 14)
                                .prereq("sacerdote_cur_25").build());
                tree.add(SkillNode.builder("sacerdote_cur_28").name("+2% Redução de Cooldown de Cura")
                                .desc("-2% de cooldown de cura.").branch(O).grid(3, 14).prereq("sacerdote_cur_26")
                                .build());
                tree.add(SkillNode.builder("sacerdote_cur_29").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(4, 15).prereq("sacerdote_cur_27", "sacerdote_cur_28").build());
                tree.add(SkillNode.builder("sacerdote_cur_30").name("Milagre")
                                .desc("Ativa: cura completamente um alvo. (CD longo)").branch(O).type(SkillType.ATIVA)
                                .grid(5, 16).prereq("sacerdote_cur_29").build());
                tree.add(SkillNode.builder("sacerdote_cur_31").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(3, 16).prereq("sacerdote_cur_29").build());
                tree.add(SkillNode.builder("sacerdote_cur_32").name("+2% Redução de Cooldown de Cura")
                                .desc("-2% de cooldown de cura.").branch(O).grid(5, 17).prereq("sacerdote_cur_30")
                                .build());
                tree.add(SkillNode.builder("sacerdote_cur_33").name("+3% Poder de Cura").desc("+3% de poder de cura.")
                                .branch(O).grid(3, 17).prereq("sacerdote_cur_31").build());
                tree.add(SkillNode.builder("sacerdote_cur_34").name("+0.5 Coração").desc("+1 de vida máxima.").branch(O)
                                .grid(5, 18).prereq("sacerdote_cur_32").build());
                tree.add(SkillNode.builder("sacerdote_cur_35").name("Luz Eterna")
                                .desc("Passiva: +10% de poder de cura permanente.").branch(O).grid(3, 18)
                                .prereq("sacerdote_cur_33").build());
                tree.add(SkillNode.builder("sacerdote_cur_36").name("Salvação")
                                .desc("Ativa: revive aliado com 30% de vida. (CD muito longo)").branch(O)
                                .type(SkillType.ATIVA).grid(4, 19).prereq("sacerdote_cur_34", "sacerdote_cur_35")
                                .build());

                // ================================================================
                // RAMO PROTEÇÃO (32 nós) → DEFESA, canto superior esquerdo
                // ================================================================
                SkillBranch D = SkillBranch.DEFESA;

                tree.add(SkillNode.builder("sacerdote_pro_01").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-4, -2).prereq("sacerdote_central").build());
                tree.add(SkillNode.builder("sacerdote_pro_02").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-4, -1).prereq("sacerdote_pro_01").build());
                tree.add(SkillNode.builder("sacerdote_pro_03").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-4, 0).prereq("sacerdote_pro_02").build());
                tree.add(SkillNode.builder("sacerdote_pro_04").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-5, 1).prereq("sacerdote_pro_03").build());
                tree.add(SkillNode.builder("sacerdote_pro_05").name("Aura de Vida")
                                .desc("Passiva: +4 corações de vida máxima.").branch(D).grid(-3, 1)
                                .prereq("sacerdote_pro_03").build());
                tree.add(SkillNode.builder("sacerdote_pro_06").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 2).prereq("sacerdote_pro_04").build());
                tree.add(SkillNode.builder("sacerdote_pro_07").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-3, 2).prereq("sacerdote_pro_05").build());
                tree.add(SkillNode.builder("sacerdote_pro_08").name("Proteção dos Deuses")
                                .desc("Passiva: aliados próximos ganham Resistência I. (efeito dinâmico)").branch(D)
                                .grid(-5, 3).prereq("sacerdote_pro_06").build());
                tree.add(SkillNode.builder("sacerdote_pro_09").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 3).prereq("sacerdote_pro_07").build());
                tree.add(SkillNode.builder("sacerdote_pro_10").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-4, 4).prereq("sacerdote_pro_08", "sacerdote_pro_09").build());
                tree.add(SkillNode.builder("sacerdote_pro_11").name("Escudo da Fé")
                                .desc("Ativa: absorção para si e aliados.").branch(D).type(SkillType.ATIVA).grid(-5, 5)
                                .prereq("sacerdote_pro_10").build());
                tree.add(SkillNode.builder("sacerdote_pro_12").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 5).prereq("sacerdote_pro_10").build());
                tree.add(SkillNode.builder("sacerdote_pro_13").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-5, 6).prereq("sacerdote_pro_11").build());
                tree.add(SkillNode.builder("sacerdote_pro_14").name("Ressurgimento")
                                .desc("Ativa: evita a morte 1x a cada 3 min. (efeito dinâmico)").branch(D).grid(-3, 6)
                                .prereq("sacerdote_pro_12").build());
                tree.add(SkillNode.builder("sacerdote_pro_15").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 7).prereq("sacerdote_pro_13").build());
                tree.add(SkillNode.builder("sacerdote_pro_16").name("Barreira Sagrada")
                                .desc("Ativa: cria barreira que reduz dano.").branch(D).type(SkillType.ATIVA)
                                .grid(-3, 7).prereq("sacerdote_pro_14").build());
                tree.add(SkillNode.builder("sacerdote_pro_17").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-5, 8).prereq("sacerdote_pro_15").build());
                tree.add(SkillNode.builder("sacerdote_pro_18").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 8).prereq("sacerdote_pro_16").build());
                tree.add(SkillNode.builder("sacerdote_pro_19").name("Guardião Divino")
                                .desc("Passiva: parte do dano de aliados vai pra você. (efeito dinâmico)").branch(D)
                                .grid(-4, 9).prereq("sacerdote_pro_17", "sacerdote_pro_18").build());
                tree.add(SkillNode.builder("sacerdote_pro_20").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-5, 10).prereq("sacerdote_pro_19").build());
                tree.add(SkillNode.builder("sacerdote_pro_21").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 10).prereq("sacerdote_pro_19").build());
                tree.add(SkillNode.builder("sacerdote_pro_22").name("Proteção em Massa")
                                .desc("Ativa: Resistência II em área.").branch(D).type(SkillType.ATIVA).grid(-5, 11)
                                .prereq("sacerdote_pro_20").build());
                tree.add(SkillNode.builder("sacerdote_pro_23").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-3, 11).prereq("sacerdote_pro_21").build());
                tree.add(SkillNode.builder("sacerdote_pro_24").name("Escudo Coletivo")
                                .desc("Passiva: aliados próximos compartilham armadura. (efeito dinâmico)").branch(D)
                                .grid(-4, 12).prereq("sacerdote_pro_22", "sacerdote_pro_23").build());
                tree.add(SkillNode.builder("sacerdote_pro_25").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 13).prereq("sacerdote_pro_24").build());
                tree.add(SkillNode.builder("sacerdote_pro_26").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-3, 13).prereq("sacerdote_pro_24").build());
                tree.add(SkillNode.builder("sacerdote_pro_27").name("Última Proteção")
                                .desc("Ativa: escudo forte em aliado prestes a morrer. (efeito dinâmico)").branch(D)
                                .grid(-5, 14).prereq("sacerdote_pro_25").build());
                tree.add(SkillNode.builder("sacerdote_pro_28").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-3, 14).prereq("sacerdote_pro_26").build());
                tree.add(SkillNode.builder("sacerdote_pro_29").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-4, 15).prereq("sacerdote_pro_27", "sacerdote_pro_28").build());
                tree.add(SkillNode.builder("sacerdote_pro_30").name("+1 Armadura").desc("+1 de armadura permanente.")
                                .branch(D).grid(-5, 16).prereq("sacerdote_pro_29").build());
                tree.add(SkillNode.builder("sacerdote_pro_31").name("+0.5 Coração").desc("+1 de vida máxima.").branch(D)
                                .grid(-3, 16).prereq("sacerdote_pro_29").build());
                tree.add(SkillNode.builder("sacerdote_pro_32").name("Fortaleza da Fé")
                                .desc("Passiva: +2 Armadura e +1 Coração permanente.").branch(D).grid(-4, 17)
                                .prereq("sacerdote_pro_30", "sacerdote_pro_31").build());

                // ================================================================
                // RAMO COMBATE SAGRADO (32 nós) → UTILIDADE, canto inferior
                // ================================================================
                SkillBranch U = SkillBranch.UTILIDADE;

                tree.add(SkillNode.builder("sacerdote_com_01").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(0, 4)
                                .prereq("sacerdote_central").build());
                tree.add(SkillNode.builder("sacerdote_com_02").name("+2% Dano Mágico").desc("+2% de dano mágico.")
                                .branch(U).grid(0, 5).prereq("sacerdote_com_01").build());
                tree.add(SkillNode.builder("sacerdote_com_03").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(0, 6)
                                .prereq("sacerdote_com_02").build());
                tree.add(SkillNode.builder("sacerdote_com_04").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(-1, 7).prereq("sacerdote_com_03").build());
                tree.add(SkillNode.builder("sacerdote_com_05").name("Castigo Sagrado")
                                .desc("Passiva: dano extra em Undead e mobs das trevas. (efeito dinâmico)").branch(U)
                                .grid(1, 7).prereq("sacerdote_com_03").build());
                tree.add(SkillNode.builder("sacerdote_com_06").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(-1, 8)
                                .prereq("sacerdote_com_04").build());
                tree.add(SkillNode.builder("sacerdote_com_07").name("+2% Dano Mágico").desc("+2% de dano mágico.")
                                .branch(U).grid(1, 8).prereq("sacerdote_com_05").build());
                tree.add(SkillNode.builder("sacerdote_com_08").name("Purificação")
                                .desc("Ativa: remove efeitos negativos de si mesmo.").branch(U).type(SkillType.ATIVA)
                                .grid(-1, 9).prereq("sacerdote_com_06").build());
                tree.add(SkillNode.builder("sacerdote_com_09").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(1, 9)
                                .prereq("sacerdote_com_07").build());
                tree.add(SkillNode.builder("sacerdote_com_10").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 10).prereq("sacerdote_com_08", "sacerdote_com_09").build());
                tree.add(SkillNode.builder("sacerdote_com_11").name("Luz Purificadora")
                                .desc("Ativa: dano em área em Undead.").branch(U).type(SkillType.ATIVA).grid(-1, 11)
                                .prereq("sacerdote_com_10").build());
                tree.add(SkillNode.builder("sacerdote_com_12").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(1, 11)
                                .prereq("sacerdote_com_10").build());
                tree.add(SkillNode.builder("sacerdote_com_13").name("+2% Dano Mágico").desc("+2% de dano mágico.")
                                .branch(U).grid(-1, 12).prereq("sacerdote_com_11").build());
                tree.add(SkillNode.builder("sacerdote_com_14").name("Julgamento")
                                .desc("Ativa: marca alvo com dano sagrado ao longo do tempo.").branch(U)
                                .type(SkillType.ATIVA).grid(1, 12).prereq("sacerdote_com_12").build());
                tree.add(SkillNode.builder("sacerdote_com_15").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 13).prereq("sacerdote_com_13", "sacerdote_com_14").build());
                tree.add(SkillNode.builder("sacerdote_com_16").name("Expulsão")
                                .desc("Ativa: empurra e causa dano extra em Undead.").branch(U).type(SkillType.ATIVA)
                                .grid(-1, 14).prereq("sacerdote_com_15").build());
                tree.add(SkillNode.builder("sacerdote_com_17").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(1, 14)
                                .prereq("sacerdote_com_15").build());
                tree.add(SkillNode.builder("sacerdote_com_18").name("+2% Dano Mágico").desc("+2% de dano mágico.")
                                .branch(U).grid(-1, 15).prereq("sacerdote_com_16").build());
                tree.add(SkillNode.builder("sacerdote_com_19").name("Fogo Sagrado")
                                .desc("Ativa: cria área que queima Undead.").branch(U).type(SkillType.ATIVA).grid(1, 15)
                                .prereq("sacerdote_com_17").build());
                tree.add(SkillNode.builder("sacerdote_com_20").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 16).prereq("sacerdote_com_18", "sacerdote_com_19").build());
                tree.add(SkillNode.builder("sacerdote_com_21").name("Consagração")
                                .desc("Passiva: parado gera aura que danifica Undead. (efeito dinâmico)").branch(U)
                                .grid(-1, 17).prereq("sacerdote_com_20").build());
                tree.add(SkillNode.builder("sacerdote_com_22").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(1, 17)
                                .prereq("sacerdote_com_20").build());
                tree.add(SkillNode.builder("sacerdote_com_23").name("+2% Dano Mágico").desc("+2% de dano mágico.")
                                .branch(U).grid(-1, 18).prereq("sacerdote_com_21").build());
                tree.add(SkillNode.builder("sacerdote_com_24").name("Martelo Divino")
                                .desc("Ativa: golpe forte que causa muito dano a Undead.").branch(U)
                                .type(SkillType.ATIVA).grid(1, 18).prereq("sacerdote_com_22").build());
                tree.add(SkillNode.builder("sacerdote_com_25").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 19).prereq("sacerdote_com_23", "sacerdote_com_24").build());
                tree.add(SkillNode.builder("sacerdote_com_26").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(-1, 20)
                                .prereq("sacerdote_com_25").build());
                tree.add(SkillNode.builder("sacerdote_com_27").name("+2% Dano Mágico").desc("+2% de dano mágico.")
                                .branch(U).grid(1, 20).prereq("sacerdote_com_25").build());
                tree.add(SkillNode.builder("sacerdote_com_28").name("Ira Sagrada")
                                .desc("Passiva: +dano conforme mais Undead próximos. (efeito dinâmico)").branch(U)
                                .grid(-1, 21).prereq("sacerdote_com_26").build());
                tree.add(SkillNode.builder("sacerdote_com_29").name("+3% Dano contra Undead")
                                .desc("+3% de dano contra mortos-vivos.").branch(U).grid(1, 21)
                                .prereq("sacerdote_com_27").build());
                tree.add(SkillNode.builder("sacerdote_com_30").name("+2% Dano Mágico").desc("+2% de dano mágico.")
                                .branch(U).grid(0, 22).prereq("sacerdote_com_28", "sacerdote_com_29").build());
                tree.add(SkillNode.builder("sacerdote_com_31").name("+0.5 Coração").desc("+1 de vida máxima.").branch(U)
                                .grid(0, 23).prereq("sacerdote_com_30").build());
                tree.add(SkillNode.builder("sacerdote_com_32").name("Exorcismo")
                                .desc("Ativa: remove buffs de inimigos e causa dano sagrado.").branch(U)
                                .type(SkillType.ATIVA).grid(0, 24).prereq("sacerdote_com_31").build());

                return tree;
        }
}