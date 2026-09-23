package com.fallengods.skills.classsystem;

public enum ClassType {
    NONE("Nenhuma"),
    GUERREIRO("Guerreiro"),
    ARQUEIRO("Arqueiro"),
    MAGO("Mago"),
    SACERDOTE("Sacerdote");

    private final String displayName;

    ClassType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}