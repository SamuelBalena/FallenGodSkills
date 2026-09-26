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
                register("guerreiro_ofe_12", new NextHitBonusHandler(
                                "guerreiro_ofe_12", 1.5f, "Golpe Devastador", 12_000L));

                register("guerreiro_ofe_24", new BuffSelfHandler(
                                MobEffects.DAMAGE_BOOST, 8 * 20, 1, 20_000L));

                register("guerreiro_ofe_20", new ExecuteHandler(
                                8.0f, 3.0f, 0.30f, 3.0, 0L));

                register("guerreiro_ofe_31", new QuebraOssosHandler(
                                6.0, 8 * 20, 18_000L));

                register("guerreiro_ofe_35", new MassacreHandler(
                                6_000L, 45_000L));

                // ================================================================
                // GUERREIRO — Utilidade
                // ================================================================
                register("guerreiro_uti_06", new BuffSelfHandler(
                                MobEffects.DAMAGE_BOOST, 8 * 20, 0, 15_000L));

                register("guerreiro_uti_09", new DamageAreaHandler(
                                4.0f, 5.0, 15_000L));

                register("guerreiro_uti_13", new DebuffTargetHandler(
                                MobEffects.MOVEMENT_SLOWDOWN, 5 * 20, 2, 8.0, 12_000L));

                register("guerreiro_uti_19", new BuffSelfHandler(
                                MobEffects.DAMAGE_RESISTANCE, 5 * 20, 2, 18_000L));

                register("guerreiro_uti_26", new TeleportHandler(6.0, 10_000L));

                // ================================================================
                // ARQUEIRO — Precisão
                // ================================================================
                register("arqueiro_pre_12", new NextHitBonusHandler(
                                "arqueiro_pre_12", 2.0f, "Tiro Preciso", 10_000L));

                register("arqueiro_pre_26", new NextHitBonusHandler(
                                "arqueiro_pre_26", 2.5f, "Disparo Concentrado", 12_000L));

                register("arqueiro_pre_36", new ExecuteHandler(
                                12.0f, 4.0f, 0.25f, 30.0, 30_000L));

                // ================================================================
                // ARQUEIRO — Mobilidade
                // ================================================================
                register("arqueiro_mob_10", new BuffSelfHandler(
                                MobEffects.MOVEMENT_SPEED, 5 * 20, 1, 14_000L));

                register("arqueiro_mob_20", new BuffSelfHandler(
                                MobEffects.MOVEMENT_SPEED, 4 * 20, 2, 16_000L));

                register("arqueiro_mob_23", new TeleportHandler(4.0, 8_000L));

                register("arqueiro_mob_30", new TeleportHandler(8.0, 12_000L));

                // ================================================================
                // ARQUEIRO — Especialização
                // ================================================================
                register("arqueiro_esp_09", new NextHitBonusHandler(
                                "arqueiro_esp_09", 1.8f, "Tiro Múltiplo", 14_000L));

                register("arqueiro_esp_13", new DamageAreaHandler(
                                6.0f, 6.0, 20_000L));

                register("arqueiro_esp_16", new NextHitBonusHandler(
                                "arqueiro_esp_16", 2.2f, "Flecha Explosiva", 15_000L));

                register("arqueiro_esp_20", new MarkTargetHandler(
                                1.20f, 20.0, 8_000L, 8_000L));

                register("arqueiro_esp_25", new NextHitBonusHandler(
                                "arqueiro_esp_25", 2.0f, "Tiro em Rajada", 18_000L));

                // ================================================================
                // MAGO — Poder Arcano
                // ================================================================
                register("mago_pod_19", new DamageAreaHandler(
                                10.0f, 6.0, 25_000L));

                register("mago_pod_22", new DamageTargetHandler(
                                8.0f, 20.0, 0L));

                register("mago_pod_27", new DamageAreaHandler(
                                12.0f, 8.0, 22_000L));

                register("mago_pod_33", new DamageAreaHandler(
                                8.0f, 4.0, 18_000L));

                // ================================================================
                // MAGO — Defesa Mágica
                // ================================================================
                register("mago_def_10", new BuffSelfHandler(
                                MobEffects.DAMAGE_RESISTANCE, 6 * 20, 2, 20_000L));

                register("mago_def_16", new BuffSelfHandler(
                                MobEffects.ABSORPTION, 10 * 20, 2, 15_000L));

                register("mago_def_22", new BuffSelfHandler(
                                MobEffects.DAMAGE_RESISTANCE, 8 * 20, 1, 18_000L));

                register("mago_def_28", new BuffSelfHandler(
                                MobEffects.DAMAGE_RESISTANCE, 4 * 20, 3, 12_000L));

                // ================================================================
                // MAGO — Controle
                // ================================================================
                register("mago_ctr_05", new DamageTargetHandler(4.0f, 20.0, 0L));

                register("mago_ctr_08", new DebuffTargetHandler(
                                MobEffects.MOVEMENT_SLOWDOWN, 4 * 20, 3, 15.0, 10_000L));

                register("mago_ctr_11", new DamageAreaHandler(5.0f, 3.0, 8_000L));

                register("mago_ctr_14", new DebuffTargetHandler(
                                MobEffects.MOVEMENT_SLOWDOWN, 3 * 20, 4, 12.0, 14_000L));

                register("mago_ctr_16", new DebuffTargetHandler(
                                MobEffects.MOVEMENT_SLOWDOWN, 5 * 20, 2, 8.0, 16_000L));

                register("mago_ctr_19", new TeleportHandler(8.0, 8_000L));

                register("mago_ctr_21", new DebuffTargetHandler(
                                MobEffects.WEAKNESS, 4 * 20, 1, 15.0, 18_000L));

                register("mago_ctr_24", new PullAreaHandler(
                                10.0, 1.5, 20_000L));

                register("mago_ctr_28", new BuffSelfHandler(
                                MobEffects.INVISIBILITY, 5 * 20, 0, 15_000L));

                // ================================================================
                // SACERDOTE — Cura
                // ================================================================
                register("sacerdote_cur_10", new HealSelfHandler(8.0f, 0L));
                register("sacerdote_cur_13", new HealAreaHandler(6.0f, 8.0, 12_000L));
                register("sacerdote_cur_19", new HealAreaHandler(10.0f, 12.0, 20_000L));
                register("sacerdote_cur_25", new HealAreaHandler(8.0f, 6.0, 25_000L));
                register("sacerdote_cur_30", new HealSelfHandler(20.0f, 60_000L));
                register("sacerdote_cur_36", new HealAreaHandler(20.0f, 8.0, 120_000L));

                // ================================================================
                // SACERDOTE — Proteção
                // ================================================================
                register("sacerdote_pro_11", new BuffSelfHandler(
                                MobEffects.ABSORPTION, 10 * 20, 2, 15_000L));

                register("sacerdote_pro_14", new BuffSelfHandler(
                                MobEffects.ABSORPTION, 15 * 20, 4, 180_000L));

                register("sacerdote_pro_16", new BuffSelfHandler(
                                MobEffects.DAMAGE_RESISTANCE, 6 * 20, 2, 18_000L));

                register("sacerdote_pro_22", new BuffSelfHandler(
                                MobEffects.DAMAGE_RESISTANCE, 8 * 20, 1, 20_000L));

                register("sacerdote_pro_27", new BuffSelfHandler(
                                MobEffects.ABSORPTION, 12 * 20, 3, 60_000L));

                // ================================================================
                // SACERDOTE — Combate Sagrado
                // ================================================================
                register("sacerdote_com_08", new BuffSelfHandler(
                                MobEffects.HEAL, 1, 0, 15_000L));

                register("sacerdote_com_11", new DamageAreaHandler(6.0f, 5.0, 12_000L));

                register("sacerdote_com_14", new DebuffTargetHandler(
                                MobEffects.WEAKNESS, 8 * 20, 1, 15.0, 14_000L));

                register("sacerdote_com_16", new DamageAreaHandler(5.0f, 4.0, 12_000L));

                register("sacerdote_com_19", new DamageAreaHandler(7.0f, 5.0, 16_000L));

                register("sacerdote_com_24", new DamageTargetHandler(10.0f, 4.0, 18_000L));

                register("sacerdote_com_32", new DamageTargetHandler(8.0f, 10.0, 20_000L));
        }

        public static ActiveSkillHandler get(String skillId) {
                return HANDLERS.get(skillId);
        }

        public static void register(String skillId, ActiveSkillHandler handler) {
                HANDLERS.put(skillId, handler);
        }
}