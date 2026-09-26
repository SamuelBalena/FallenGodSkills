package com.fallengods.skills.skill.active;

import java.util.HashMap;
import java.util.Map;

public class ActiveSkillHandlers {

        private static final Map<String, ActiveSkillHandler> HANDLERS = new HashMap<>();

        public static void init() {
                // Desativado por enquanto — será repopulado quando as skills
                // ativas forem revisadas.
        }

        public static ActiveSkillHandler get(String skillId) {
                return HANDLERS.get(skillId);
        }

        public static void register(String skillId, ActiveSkillHandler handler) {
                HANDLERS.put(skillId, handler);
        }
}