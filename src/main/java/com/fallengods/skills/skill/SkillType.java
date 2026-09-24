package com.fallengods.skills.skill;

public enum SkillType {
    PASSIVA("Passiva"),
    ATIVA("Ativa");

    private final String displayName;

    SkillType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}