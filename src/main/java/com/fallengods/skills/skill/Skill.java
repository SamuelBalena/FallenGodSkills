package com.fallengods.skills.skill;

public class Skill {
    private final String id;
    private final String displayName;
    private final int cost;
    private final String description;

    public Skill(String id, String displayName, int cost, String description) {
        this.id = id;
        this.displayName = displayName;
        this.cost = cost;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}