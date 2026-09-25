package com.fallengods.skills.client;

import com.fallengods.skills.FallenGodsSkills;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = FallenGodsSkills.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {

    // Tecla de abrir árvore (já existia)
    public static final Lazy<KeyMapping> OPEN_SKILL_TREE = Lazy.of(() -> new KeyMapping(
            "key.fallengodsskills.open_skill_tree",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "key.categories.fallengodsskills"));

    // ===== 12 teclas de skill ativa =====
    // Slot 0 → Q
    // Slot 1 → E
    // Slot 2 → R
    // Slot 3 → F
    // Slot 4 → T
    // Slot 5 → G
    // Slot 6 → V
    // Slot 7 → C
    // Slot 8 → X
    // Slot 9 → Z
    // Slot 10 → Shift+Q
    // Slot 11 → Shift+E

    public static final Lazy<KeyMapping> SKILL_1 = makeSkillKey("skill_1", GLFW.GLFW_KEY_Q, 0);
    public static final Lazy<KeyMapping> SKILL_2 = makeSkillKey("skill_2", GLFW.GLFW_KEY_E, 0);
    public static final Lazy<KeyMapping> SKILL_3 = makeSkillKey("skill_3", GLFW.GLFW_KEY_R, 0);
    public static final Lazy<KeyMapping> SKILL_4 = makeSkillKey("skill_4", GLFW.GLFW_KEY_F, 0);
    public static final Lazy<KeyMapping> SKILL_5 = makeSkillKey("skill_5", GLFW.GLFW_KEY_T, 0);
    public static final Lazy<KeyMapping> SKILL_6 = makeSkillKey("skill_6", GLFW.GLFW_KEY_G, 0);
    public static final Lazy<KeyMapping> SKILL_7 = makeSkillKey("skill_7", GLFW.GLFW_KEY_V, 0);
    public static final Lazy<KeyMapping> SKILL_8 = makeSkillKey("skill_8", GLFW.GLFW_KEY_C, 0);
    public static final Lazy<KeyMapping> SKILL_9 = makeSkillKey("skill_9", GLFW.GLFW_KEY_X, 0);
    public static final Lazy<KeyMapping> SKILL_10 = makeSkillKey("skill_10", GLFW.GLFW_KEY_Z, 0);
    public static final Lazy<KeyMapping> SKILL_11 = makeSkillKey("skill_11", GLFW.GLFW_KEY_Q, GLFW.GLFW_MOD_SHIFT);
    public static final Lazy<KeyMapping> SKILL_12 = makeSkillKey("skill_12", GLFW.GLFW_KEY_E, GLFW.GLFW_MOD_SHIFT);

    /** Array ordenado dos 12 slots pra facilitar iteração. */
    public static final Lazy<KeyMapping>[] ALL_SKILL_KEYS = new Lazy[] {
            SKILL_1, SKILL_2, SKILL_3, SKILL_4, SKILL_5, SKILL_6,
            SKILL_7, SKILL_8, SKILL_9, SKILL_10, SKILL_11, SKILL_12
    };

    private static Lazy<KeyMapping> makeSkillKey(String name, int keyCode, int modifiers) {
        return Lazy.of(() -> new KeyMapping(
                "key.fallengodsskills." + name,
                InputConstants.Type.KEYSYM,
                keyCode,
                "key.categories.fallengodsskills"));
    }

    @SubscribeEvent
    public static void onRegisterKeys(RegisterKeyMappingsEvent event) {
        event.register(OPEN_SKILL_TREE.get());
        for (Lazy<KeyMapping> key : ALL_SKILL_KEYS) {
            event.register(key.get());
        }
    }
}