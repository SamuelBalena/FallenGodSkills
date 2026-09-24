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
        // GUERREIRO — completo (Defesa + Ofensivo + Utilidade)
        // =====================================================================
        private static SkillTree buildGuerreiro() {
                SkillTree tree = new SkillTree(ClassType.GUERREIRO);

                // ================================================================
                // NÓ CENTRAL
                // ================================================================
                tree.add(SkillNode.builder("guerreiro_central")
                                .name("Coração de Batalha")
                                .desc("Passiva base: +20% dano com espadas/machados, +10% resistência a knockback.")
                                .branch(SkillBranch.DEFESA).type(SkillType.PASSIVA)
                                .cost(0).grid(0, 0).build());

                // ================================================================
                // RAMO DEFESA (34 nós) — canto superior esquerdo
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

                // ================================================================
                // RAMO OFENSIVO (36 nós) — canto superior direito
                // ================================================================
                SkillBranch O = SkillBranch.OFENSIVO;

                tree.add(SkillNode.builder("guerreiro_ofe_01")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(4, -2).prereq("guerreiro_central").build());
                tree.add(SkillNode.builder("guerreiro_ofe_02")
                                .name("+2% Velocidade de Ataque").desc("+2% de velocidade de ataque.")
                                .branch(O).grid(4, -1).prereq("guerreiro_ofe_01").build());
                tree.add(SkillNode.builder("guerreiro_ofe_03")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(O).grid(4, 0).prereq("guerreiro_ofe_02").build());
                tree.add(SkillNode.builder("guerreiro_ofe_04")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(5, 1).prereq("guerreiro_ofe_03").build());
                tree.add(SkillNode.builder("guerreiro_ofe_05")
                                .name("+2% Chance de Crítico").desc("+2% de chance de crítico.")
                                .branch(O).grid(3, 1).prereq("guerreiro_ofe_03").build());
                tree.add(SkillNode.builder("guerreiro_ofe_06")
                                .name("+2% Velocidade de Ataque").desc("+2% de velocidade de ataque.")
                                .branch(O).grid(5, 2).prereq("guerreiro_ofe_04").build());
                tree.add(SkillNode.builder("guerreiro_ofe_07")
                                .name("Fúria Primordial").desc("Passiva: +15% de dano com Espadas e Machados.")
                                .branch(O).grid(3, 2).prereq("guerreiro_ofe_05").build());
                tree.add(SkillNode.builder("guerreiro_ofe_08")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(5, 3).prereq("guerreiro_ofe_06").build());
                tree.add(SkillNode.builder("guerreiro_ofe_09")
                                .name("+2% Velocidade de Ataque").desc("+2% de velocidade de ataque.")
                                .branch(O).grid(3, 3).prereq("guerreiro_ofe_07").build());
                tree.add(SkillNode.builder("guerreiro_ofe_10")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(O).grid(4, 4).prereq("guerreiro_ofe_08", "guerreiro_ofe_09").build());
                tree.add(SkillNode.builder("guerreiro_ofe_11")
                                .name("+3% Chance de Crítico").desc("+3% de chance de crítico.")
                                .branch(O).grid(5, 5).prereq("guerreiro_ofe_10").build());
                tree.add(SkillNode.builder("guerreiro_ofe_12")
                                .name("Golpe Devastador").desc("Ativa: próximo ataque +50% dano. (CD 12s)")
                                .branch(O).type(SkillType.ATIVA).grid(3, 5).prereq("guerreiro_ofe_10").build());
                tree.add(SkillNode.builder("guerreiro_ofe_13")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(5, 6).prereq("guerreiro_ofe_11").build());
                tree.add(SkillNode.builder("guerreiro_ofe_14")
                                .name("+2% Velocidade de Ataque").desc("+2% de velocidade de ataque.")
                                .branch(O).grid(3, 6).prereq("guerreiro_ofe_12").build());
                tree.add(SkillNode.builder("guerreiro_ofe_15")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(O).grid(4, 7).prereq("guerreiro_ofe_13", "guerreiro_ofe_14").build());
                tree.add(SkillNode.builder("guerreiro_ofe_16")
                                .name("Sangue de Batalha").desc("Passiva: cura 1 coração ao matar. (efeito dinâmico)")
                                .branch(O).grid(5, 8).prereq("guerreiro_ofe_15").build());
                tree.add(SkillNode.builder("guerreiro_ofe_17")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(3, 8).prereq("guerreiro_ofe_15").build());
                tree.add(SkillNode.builder("guerreiro_ofe_18")
                                .name("+3% Chance de Crítico").desc("+3% de chance de crítico.")
                                .branch(O).grid(5, 9).prereq("guerreiro_ofe_16").build());
                tree.add(SkillNode.builder("guerreiro_ofe_19")
                                .name("+2% Velocidade de Ataque").desc("+2% de velocidade de ataque.")
                                .branch(O).grid(3, 9).prereq("guerreiro_ofe_17").build());
                tree.add(SkillNode.builder("guerreiro_ofe_20")
                                .name("Execução").desc("Ativa: dano alto em alvo abaixo de 30% de vida.")
                                .branch(O).type(SkillType.ATIVA).grid(4, 10)
                                .prereq("guerreiro_ofe_18", "guerreiro_ofe_19").build());
                tree.add(SkillNode.builder("guerreiro_ofe_21")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(5, 11).prereq("guerreiro_ofe_20").build());
                tree.add(SkillNode.builder("guerreiro_ofe_22")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(O).grid(3, 11).prereq("guerreiro_ofe_20").build());
                tree.add(SkillNode.builder("guerreiro_ofe_23")
                                .name("+2% Velocidade de Ataque").desc("+2% de velocidade de ataque.")
                                .branch(O).grid(5, 12).prereq("guerreiro_ofe_21").build());
                tree.add(SkillNode.builder("guerreiro_ofe_24")
                                .name("Fúria Cega")
                                .desc("Ativa: +dano e +vel. ataque por 8s, +20% dano recebido. (CD 20s)")
                                .branch(O).type(SkillType.ATIVA).grid(3, 12).prereq("guerreiro_ofe_22").build());
                tree.add(SkillNode.builder("guerreiro_ofe_25")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(5, 13).prereq("guerreiro_ofe_23").build());
                tree.add(SkillNode.builder("guerreiro_ofe_26")
                                .name("+3% Chance de Crítico").desc("+3% de chance de crítico.")
                                .branch(O).grid(3, 13).prereq("guerreiro_ofe_24").build());
                tree.add(SkillNode.builder("guerreiro_ofe_27")
                                .name("+2% Velocidade de Ataque").desc("+2% de velocidade de ataque.")
                                .branch(O).grid(4, 14).prereq("guerreiro_ofe_25", "guerreiro_ofe_26").build());
                tree.add(SkillNode.builder("guerreiro_ofe_28")
                                .name("Corte Profundo").desc("Passiva: 15% chance de sangramento. (efeito dinâmico)")
                                .branch(O).grid(5, 15).prereq("guerreiro_ofe_27").build());
                tree.add(SkillNode.builder("guerreiro_ofe_29")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(3, 15).prereq("guerreiro_ofe_27").build());
                tree.add(SkillNode.builder("guerreiro_ofe_30")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(O).grid(5, 16).prereq("guerreiro_ofe_28").build());
                tree.add(SkillNode.builder("guerreiro_ofe_31")
                                .name("Quebra-Ossos").desc("Ativa: -30% armadura do alvo por 8s.")
                                .branch(O).type(SkillType.ATIVA).grid(3, 16).prereq("guerreiro_ofe_29").build());
                tree.add(SkillNode.builder("guerreiro_ofe_32")
                                .name("+3% Dano com Espadas").desc("+3% de dano com espadas.")
                                .branch(O).grid(5, 17).prereq("guerreiro_ofe_30").build());
                tree.add(SkillNode.builder("guerreiro_ofe_33")
                                .name("+2% Velocidade de Ataque").desc("+2% de velocidade de ataque.")
                                .branch(O).grid(3, 17).prereq("guerreiro_ofe_31").build());
                tree.add(SkillNode.builder("guerreiro_ofe_34")
                                .name("+3% Chance de Crítico").desc("+3% de chance de crítico.")
                                .branch(O).grid(4, 18).prereq("guerreiro_ofe_32", "guerreiro_ofe_33").build());
                tree.add(SkillNode.builder("guerreiro_ofe_35")
                                .name("Massacre").desc("Ativa: cada kill reduz cooldowns em 2s. (6s)")
                                .branch(O).type(SkillType.ATIVA).grid(4, 19).prereq("guerreiro_ofe_34").build());
                tree.add(SkillNode.builder("guerreiro_ofe_36")
                                .name("Sede de Sangue").desc("Passiva: +dano conforme menos vida. (efeito dinâmico)")
                                .branch(O).grid(4, 20).prereq("guerreiro_ofe_35").build());

                // ================================================================
                // RAMO UTILIDADE (30 nós) — canto inferior
                // ================================================================
                SkillBranch U = SkillBranch.UTILIDADE;

                tree.add(SkillNode.builder("guerreiro_uti_01")
                                .name("+3% Velocidade de Movimento").desc("+3% de velocidade.")
                                .branch(U).grid(0, 4).prereq("guerreiro_central").build());
                tree.add(SkillNode.builder("guerreiro_uti_02")
                                .name("+3% Resistência a Knockback").desc("Reduz knockback em 3%.")
                                .branch(U).grid(0, 5).prereq("guerreiro_uti_01").build());
                tree.add(SkillNode.builder("guerreiro_uti_03")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(U).grid(0, 6).prereq("guerreiro_uti_02").build());
                tree.add(SkillNode.builder("guerreiro_uti_04")
                                .name("+3% Velocidade de Movimento").desc("+3% de velocidade.")
                                .branch(U).grid(-1, 7).prereq("guerreiro_uti_03").build());
                tree.add(SkillNode.builder("guerreiro_uti_05")
                                .name("+2% Redução de Dano").desc("Reduz 2% de dano recebido.")
                                .branch(U).grid(1, 7).prereq("guerreiro_uti_03").build());
                tree.add(SkillNode.builder("guerreiro_uti_06")
                                .name("Grito de Guerra").desc("Ativa: buff de ataque pra aliados. (8s)")
                                .branch(U).type(SkillType.ATIVA).grid(-1, 8).prereq("guerreiro_uti_04").build());
                tree.add(SkillNode.builder("guerreiro_uti_07")
                                .name("+3% Resistência a Knockback").desc("Reduz knockback em 3%.")
                                .branch(U).grid(1, 8).prereq("guerreiro_uti_05").build());
                tree.add(SkillNode.builder("guerreiro_uti_08")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(U).grid(0, 9).prereq("guerreiro_uti_06", "guerreiro_uti_07").build());
                tree.add(SkillNode.builder("guerreiro_uti_09")
                                .name("Impacto Sísmico").desc("Ativa: empurra inimigos próximos. (CD 15s)")
                                .branch(U).type(SkillType.ATIVA).grid(-1, 10).prereq("guerreiro_uti_08").build());
                tree.add(SkillNode.builder("guerreiro_uti_10")
                                .name("+3% Velocidade de Movimento").desc("+3% de velocidade.")
                                .branch(U).grid(1, 10).prereq("guerreiro_uti_08").build());
                tree.add(SkillNode.builder("guerreiro_uti_11")
                                .name("+2% Redução de Dano").desc("Reduz 2% de dano recebido.")
                                .branch(U).grid(-1, 11).prereq("guerreiro_uti_09").build());
                tree.add(SkillNode.builder("guerreiro_uti_12")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(U).grid(1, 11).prereq("guerreiro_uti_10").build());
                tree.add(SkillNode.builder("guerreiro_uti_13")
                                .name("Provocar").desc("Ativa: força inimigos a te atacarem. (5s)")
                                .branch(U).type(SkillType.ATIVA).grid(0, 12)
                                .prereq("guerreiro_uti_11", "guerreiro_uti_12").build());
                tree.add(SkillNode.builder("guerreiro_uti_14")
                                .name("+3% Velocidade de Movimento").desc("+3% de velocidade.")
                                .branch(U).grid(-1, 13).prereq("guerreiro_uti_13").build());
                tree.add(SkillNode.builder("guerreiro_uti_15")
                                .name("+3% Resistência a Knockback").desc("Reduz knockback em 3%.")
                                .branch(U).grid(1, 13).prereq("guerreiro_uti_13").build());
                tree.add(SkillNode.builder("guerreiro_uti_16")
                                .name("Passo Pesado").desc("Passiva: reduz knockback recebido. (efeito dinâmico)")
                                .branch(U).grid(-1, 14).prereq("guerreiro_uti_14").build());
                tree.add(SkillNode.builder("guerreiro_uti_17")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(U).grid(1, 14).prereq("guerreiro_uti_15").build());
                tree.add(SkillNode.builder("guerreiro_uti_18")
                                .name("+3% Velocidade de Movimento").desc("+3% de velocidade.")
                                .branch(U).grid(0, 15).prereq("guerreiro_uti_16", "guerreiro_uti_17").build());
                tree.add(SkillNode.builder("guerreiro_uti_19")
                                .name("Barreira de Aço").desc("Ativa: -40% dano por 5s. (CD 18s)")
                                .branch(U).type(SkillType.ATIVA).grid(-1, 16).prereq("guerreiro_uti_18").build());
                tree.add(SkillNode.builder("guerreiro_uti_20")
                                .name("+3% Resistência a Knockback").desc("Reduz knockback em 3%.")
                                .branch(U).grid(1, 16).prereq("guerreiro_uti_18").build());
                tree.add(SkillNode.builder("guerreiro_uti_21")
                                .name("+2% Redução de Dano").desc("Reduz 2% de dano recebido.")
                                .branch(U).grid(-1, 17).prereq("guerreiro_uti_19").build());
                tree.add(SkillNode.builder("guerreiro_uti_22")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(U).grid(1, 17).prereq("guerreiro_uti_20").build());
                tree.add(SkillNode.builder("guerreiro_uti_23")
                                .name("Presença Intimidadora")
                                .desc("Passiva: inimigos próximos -10% vel. ataque. (efeito dinâmico)")
                                .branch(U).grid(0, 18).prereq("guerreiro_uti_21", "guerreiro_uti_22").build());
                tree.add(SkillNode.builder("guerreiro_uti_24")
                                .name("+3% Velocidade de Movimento").desc("+3% de velocidade.")
                                .branch(U).grid(-1, 19).prereq("guerreiro_uti_23").build());
                tree.add(SkillNode.builder("guerreiro_uti_25")
                                .name("+3% Resistência a Knockback").desc("Reduz knockback em 3%.")
                                .branch(U).grid(1, 19).prereq("guerreiro_uti_23").build());
                tree.add(SkillNode.builder("guerreiro_uti_26")
                                .name("Investida").desc("Ativa: avança rapidamente causando dano. (CD 10s)")
                                .branch(U).type(SkillType.ATIVA).grid(-1, 20).prereq("guerreiro_uti_24").build());
                tree.add(SkillNode.builder("guerreiro_uti_27")
                                .name("+0.5 Coração").desc("+1 de vida máxima.")
                                .branch(U).grid(1, 20).prereq("guerreiro_uti_25").build());
                tree.add(SkillNode.builder("guerreiro_uti_28")
                                .name("+2% Redução de Dano").desc("Reduz 2% de dano recebido.")
                                .branch(U).grid(0, 21).prereq("guerreiro_uti_26", "guerreiro_uti_27").build());
                tree.add(SkillNode.builder("guerreiro_uti_29")
                                .name("+3% Velocidade de Movimento").desc("+3% de velocidade.")
                                .branch(U).grid(0, 22).prereq("guerreiro_uti_28").build());
                tree.add(SkillNode.builder("guerreiro_uti_30")
                                .name("Último Resistente")
                                .desc("Passiva: Resistência II + Força I abaixo de 25%. (efeito dinâmico)")
                                .branch(U).grid(0, 23).prereq("guerreiro_uti_29").build());

                return tree;
        }

        // =====================================================================
        // ARQUEIRO — placeholder (a popular na 5.6.2)
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
        // MAGO — placeholder (a popular na 5.6.3)
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
        // SACERDOTE — placeholder (a popular na 5.6.4)
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