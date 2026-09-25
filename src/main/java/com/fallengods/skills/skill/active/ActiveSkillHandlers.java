package com.fallengods.skills.skill.active;

import com.fallengods.skills.skill.active.handler.*;
import net.minecraft.world.effect.MobEffects;

import java.util.HashMap;
import java.util.Map;

public class ActiveSkillHandlers {

    private static final Map<String, ActiveSkillHandler> HANDLERS = new HashMap<>();

    public static void init() {
        // ================================================================
        // GUERREIRO — Ofensivo
        // ================================================================
        // Golpe Devastador: próximo ataque +50% dano, CD 12s
        register("guerreiro_ofe_12", new NextHitBonusHandler(
                "guerreiro_ofe_12", 1.5f, "Golpe Devastador", 12_000L));

        // Fúria Cega: +dano e velocidade de ataque por 8s, CD 20s
        // (implementado como buff duplo — vamos usar Speed + Strength)
        register("guerreiro_ofe_24", new BuffSelfHandler(
                MobEffects.DAMAGE_BOOST, 8 * 20, 1, 20_000L));

        // Execução: dano extra em alvo abaixo de 30% de HP
        // Por enquanto, dano direto alto em raio curto
        register("guerreiro_ofe_20", new DamageTargetHandler(
                8.0f, 3.0, 0L));

        // ================================================================
        // GUERREIRO — Utilidade
        // ================================================================
        // Grito de Guerra: buff de força por 8s
        register("guerreiro_uti_06", new BuffSelfHandler(
                MobEffects.DAMAGE_BOOST, 8 * 20, 0, 15_000L));

        // Impacto Sísmico: dano + empurrão em área, CD 15s
        register("guerreiro_uti_09", new DamageAreaHandler(
                4.0f, 5.0, 15_000L));

        // Provocar: puxa inimigos próximos (simplificado como debuff de lentidão)
        register("guerreiro_uti_13", new DebuffTargetHandler(
                MobEffects.MOVEMENT_SLOWDOWN, 5 * 20, 2, 8.0, 12_000L));

        // Barreira de Aço: absorção por 5s, CD 18s
        register("guerreiro_uti_19", new BuffSelfHandler(
                MobEffects.DAMAGE_RESISTANCE, 5 * 20, 2, 18_000L));

        // Investida: dash pra frente, CD 10s
        register("guerreiro_uti_26", new TeleportHandler(6.0, 10_000L));

        // ================================================================
        // ARQUEIRO — Precisão
        // ================================================================
        // Tiro Preciso: próximo tiro crítico garantido, CD 10s
        register("arqueiro_pre_12", new NextHitBonusHandler(
                "arqueiro_pre_12", 2.0f, "Tiro Preciso", 10_000L));

        // Disparo Concentrado: próximo tiro +dano e empurra, CD 12s
        register("arqueiro_pre_26", new NextHitBonusHandler(
                "arqueiro_pre_26", 2.5f, "Disparo Concentrado", 12_000L));

        // Tiro Fatal: executa alvo abaixo de 25% HP
        register("arqueiro_pre_36", new DamageTargetHandler(
                12.0f, 30.0, 30_000L));

        // ================================================================
        // ARQUEIRO — Mobilidade
        // ================================================================
        // Fuga Rápida: Speed II + Jump Boost 5s, CD 14s
        register("arqueiro_mob_10", new BuffSelfHandler(
                MobEffects.MOVEMENT_SPEED, 5 * 20, 1, 14_000L));

        // Corrida do Vento: Speed III por 4s, CD 16s
        register("arqueiro_mob_20", new BuffSelfHandler(
                MobEffects.MOVEMENT_SPEED, 4 * 20, 2, 16_000L));

        // Esquiva Instantânea: dash curto, CD 8s
        register("arqueiro_mob_23", new TeleportHandler(4.0, 8_000L));

        // Salto da Sombra: teleporte curto, CD 12s
        register("arqueiro_mob_30", new TeleportHandler(8.0, 12_000L));

        // ================================================================
        // ARQUEIRO — Especialização
        // ================================================================
        // Tiro Múltiplo: próximo tiro 3 flechas (simplificado como +dano), CD 14s
        register("arqueiro_esp_09", new NextHitBonusHandler(
                "arqueiro_esp_09", 1.8f, "Tiro Múltiplo", 14_000L));

        // Chuva de Flechas: dano em área, CD 20s
        register("arqueiro_esp_13", new DamageAreaHandler(
                6.0f, 6.0, 20_000L));

        // Flecha Explosiva: próximo tiro explosivo, CD 15s
        register("arqueiro_esp_16", new NextHitBonusHandler(
                "arqueiro_esp_16", 2.2f, "Flecha Explosiva", 15_000L));

        // Marcação: alvo recebe +20% dano (simplificado como debuff), CD 8s
        register("arqueiro_esp_20", new DebuffTargetHandler(
                MobEffects.WEAKNESS, 8 * 20, 0, 20.0, 8_000L));

        // Tiro em Rajada: 5 flechas rápidas (simplificado), CD 18s
        register("arqueiro_esp_25", new NextHitBonusHandler(
                "arqueiro_esp_25", 2.0f, "Tiro em Rajada", 18_000L));

        // ================================================================
        // MAGO — Poder Arcano
        // ================================================================
        // Tempestade Arcana: raios em área, CD 25s
        register("mago_pod_19", new DamageAreaHandler(
                10.0f, 6.0, 25_000L));

        // Orbe Arcano: projétil explosivo (simplificado como dano no alvo)
        register("mago_pod_22", new DamageTargetHandler(
                8.0f, 20.0, 0L));

        // Chuva de Meteoros: dano em área, CD 22s
        register("mago_pod_27", new DamageAreaHandler(
                12.0f, 8.0, 22_000L));

        // Explosão de Mana: dano em área ao redor, CD 18s
        register("mago_pod_33", new DamageAreaHandler(
                8.0f, 4.0, 18_000L));

        // ================================================================
        // MAGO — Defesa Mágica
        // ================================================================
        // Barreira Arcana: absorção por 6s, CD 20s
        register("mago_def_10", new BuffSelfHandler(
                MobEffects.DAMAGE_RESISTANCE, 6 * 20, 2, 20_000L));

        // Escudo de Mana: absorção, CD 15s
        register("mago_def_16", new BuffSelfHandler(
                MobEffects.ABSORPTION, 10 * 20, 2, 15_000L));

        // Campo de Força: barreira, CD 18s
        register("mago_def_22", new BuffSelfHandler(
                MobEffects.DAMAGE_RESISTANCE, 8 * 20, 1, 18_000L));

        // Reflexão: reflete projétil (aproximação: resistência), CD 12s
        register("mago_def_28", new BuffSelfHandler(
                MobEffects.DAMAGE_RESISTANCE, 4 * 20, 3, 12_000L));

        // ================================================================
        // MAGO — Controle
        // ================================================================
        // Chama Interior: dano direto pequeno
        register("mago_ctr_05", new DamageTargetHandler(4.0f, 20.0, 0L));

        // Congelamento: lentidão forte por 4s, CD 10s
        register("mago_ctr_08", new DebuffTargetHandler(
                MobEffects.MOVEMENT_SLOWDOWN, 4 * 20, 3, 15.0, 10_000L));

        // Explosão Rúnica: dano em área pequena
        register("mago_ctr_11", new DamageAreaHandler(5.0f, 3.0, 8_000L));

        // Correntes Arcanas: prende alvo (lentidão forte), CD 14s
        register("mago_ctr_14", new DebuffTargetHandler(
                MobEffects.MOVEMENT_SLOWDOWN, 3 * 20, 4, 12.0, 14_000L));

        // Nova de Gelo: congela inimigos próximos, CD 16s
        register("mago_ctr_16", new DebuffTargetHandler(
                MobEffects.MOVEMENT_SLOWDOWN, 5 * 20, 2, 8.0, 16_000L));

        // Teleporte Curto: dash, CD 8s
        register("mago_ctr_19", new TeleportHandler(8.0, 8_000L));

        // Silêncio: impede uso (aproximação: fraqueza), CD 18s
        register("mago_ctr_21", new DebuffTargetHandler(
                MobEffects.WEAKNESS, 4 * 20, 1, 15.0, 18_000L));

        // Campo de Gravidade: puxa inimigos (lentidão), CD 20s
        register("mago_ctr_24", new DebuffTargetHandler(
                MobEffects.MOVEMENT_SLOWDOWN, 6 * 20, 2, 10.0, 20_000L));

        // Ilusão: distrai inimigos (aproximação: invisibilidade própria), CD 15s
        register("mago_ctr_28", new BuffSelfHandler(
                MobEffects.INVISIBILITY, 5 * 20, 0, 15_000L));

        // ================================================================
        // SACERDOTE — Cura
        // ================================================================
        // Toque Sagrado: cura 4 corações
        register("sacerdote_cur_10", new HealSelfHandler(8.0f, 0L));

        // Luz Restauradora: cura em área, CD 12s
        register("sacerdote_cur_13", new HealAreaHandler(6.0f, 8.0, 12_000L));

        // Cura em Massa: cura forte em área maior, CD 20s
        register("sacerdote_cur_19", new HealAreaHandler(10.0f, 12.0, 20_000L));

        // Fonte da Vida: zona de cura (aproximação: cura forte), CD 25s
        register("sacerdote_cur_25", new HealAreaHandler(8.0f, 6.0, 25_000L));

        // Milagre: cura completa, CD 60s
        register("sacerdote_cur_30", new HealSelfHandler(20.0f, 60_000L));

        // Salvação: revive aliado (aproximação: cura em área massiva), CD 120s
        register("sacerdote_cur_36", new HealAreaHandler(20.0f, 8.0, 120_000L));

        // ================================================================
        // SACERDOTE — Proteção
        // ================================================================
        // Escudo da Fé: absorção, CD 15s
        register("sacerdote_pro_11", new BuffSelfHandler(
                MobEffects.ABSORPTION, 10 * 20, 2, 15_000L));

        // Ressurgimento: evita morte (aproximação: absorção forte), CD 180s
        register("sacerdote_pro_14", new BuffSelfHandler(
                MobEffects.ABSORPTION, 15 * 20, 4, 180_000L));

        // Barreira Sagrada: resistência, CD 18s
        register("sacerdote_pro_16", new BuffSelfHandler(
                MobEffects.DAMAGE_RESISTANCE, 6 * 20, 2, 18_000L));

        // Proteção em Massa: resistência em área (própria), CD 20s
        register("sacerdote_pro_22", new BuffSelfHandler(
                MobEffects.DAMAGE_RESISTANCE, 8 * 20, 1, 20_000L));

        // Última Proteção: absorção forte, CD 60s
        register("sacerdote_pro_27", new BuffSelfHandler(
                MobEffects.ABSORPTION, 12 * 20, 3, 60_000L));

        // ================================================================
        // SACERDOTE — Combate Sagrado
        // ================================================================
        // Purificação: remove efeitos negativos (aproximação: buff)
        register("sacerdote_com_08", new BuffSelfHandler(
                MobEffects.HEAL, 1, 0, 15_000L));

        // Luz Purificadora: dano em área, CD 12s
        register("sacerdote_com_11", new DamageAreaHandler(6.0f, 5.0, 12_000L));

        // Julgamento: marca alvo (aproximação: fraqueza), CD 14s
        register("sacerdote_com_14", new DebuffTargetHandler(
                MobEffects.WEAKNESS, 8 * 20, 1, 15.0, 14_000L));

        // Expulsão: empurra e dano (aproximação: dano em área), CD 12s
        register("sacerdote_com_16", new DamageAreaHandler(5.0f, 4.0, 12_000L));

        // Fogo Sagrado: área queimando (aproximação: dano + fogo), CD 16s
        register("sacerdote_com_19", new DamageAreaHandler(7.0f, 5.0, 16_000L));

        // Martelo Divino: dano forte em alvo, CD 18s
        register("sacerdote_com_24", new DamageTargetHandler(10.0f, 4.0, 18_000L));

        // Exorcismo: remove buffs + dano (aproximação: dano em alvo), CD 20s
        register("sacerdote_com_32", new DamageTargetHandler(8.0f, 10.0, 20_000L));
    }

    public static ActiveSkillHandler get(String skillId) {
        return HANDLERS.get(skillId);
    }

    public static void register(String skillId, ActiveSkillHandler handler) {
        HANDLERS.put(skillId, handler);
    }
}