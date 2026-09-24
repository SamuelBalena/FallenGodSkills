package com.fallengods.skills.skill;

public enum SkillBranch {
    DEFESA("Defesa"),
    OFENSIVO("Ofensivo"),
    UTILIDADE("Utilidade");

    private final String displayName;

    SkillBranch(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}