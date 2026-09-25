package com.fallengods.skills.skill.active;

import com.fallengods.skills.capability.PlayerSkillData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.HashMap;
import java.util.Map;

public class ActiveSkillHandlers {

    private static final Map<String, ActiveSkillHandler> HANDLERS = new HashMap<>();

    public static void init() {
        // ================================================================
        // GUERREIRO — Golpe Devastador
        // Estilo: DANO DIRETO
        // Próximo ataque causa +50% de dano (CD 12s)
        //
        // Implementação: seta uma tag NBT temporária no jogador. O
        // SkillEffectEvents escuta o próximo dano e consome a tag.
        // ================================================================
        register("guerreiro_ofe_12", new ActiveSkillHandler() {
            @Override
            public void activate(ServerPlayer player, PlayerSkillData data) {
                player.getPersistentData().putBoolean("fgs_golpe_devastador", true);
                player.sendSystemMessage(Component.literal(
                        "§6Golpe Devastador §7ativado — próximo ataque +50% dano."));
            }

            @Override
            public long getCooldownMs() {
                return 12_000L;
            }
        });

        // ================================================================
        // SACERDOTE — Toque Sagrado
        // Estilo: CURA DIRETA
        // Cura 4 corações imediatos (sem CD)
        // ================================================================
        register("sacerdote_cur_10", new ActiveSkillHandler() {
            @Override
            public void activate(ServerPlayer player, PlayerSkillData data) {
                // Pega o bônus de HEAL_POWER do jogador
                double healPower = data.getBonus(
                        com.fallengods.skills.skill.effect.StatType.HEAL_POWER);

                // 4 corações = 8 HP. Aplica o bônus multiplicativo.
                float healAmount = (float) (8.0 * (1.0 + healPower));

                player.heal(healAmount);
                player.sendSystemMessage(Component.literal(
                        "§aToque Sagrado §7curou §f" + String.format("%.1f", healAmount / 2) + " corações."));
            }

            @Override
            public long getCooldownMs() {
                return 0L;
            }
        });

        // ================================================================
        // ARQUEIRO — Fuga Rápida
        // Estilo: BUFF TEMPORÁRIO
        // Speed II + Jump Boost por 5s (CD 14s)
        // ================================================================
        register("arqueiro_mob_10", new ActiveSkillHandler() {
            @Override
            public void activate(ServerPlayer player, PlayerSkillData data) {
                int duration = 5 * 20; // 5 segundos = 100 ticks

                player.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED,
                        duration,
                        1, // nível 2 (II)
                        false,
                        true));
                player.addEffect(new MobEffectInstance(
                        MobEffects.JUMP,
                        duration,
                        1,
                        false,
                        true));

                player.sendSystemMessage(Component.literal(
                        "§bFuga Rápida §7ativada — +velocidade e +salto por 5s."));
            }

            @Override
            public long getCooldownMs() {
                return 14_000L;
            }
        });
    }

    public static ActiveSkillHandler get(String skillId) {
        return HANDLERS.get(skillId);
    }

    public static void register(String skillId, ActiveSkillHandler handler) {
        HANDLERS.put(skillId, handler);
    }
}