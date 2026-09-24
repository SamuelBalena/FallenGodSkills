package com.fallengods.skills.skill;

import java.util.ArrayList;
import java.util.List;

public class SkillNode {

    private final String id;
    private final String displayName;
    private final String description;
    private final SkillBranch branch;
    private final SkillType type;
    private final int cost;
    private final int gridX;
    private final int gridY;
    private final List<String> prerequisites;

    private SkillNode(Builder builder) {
        this.id = builder.id;
        this.displayName = builder.displayName;
        this.description = builder.description;
        this.branch = builder.branch;
        this.type = builder.type;
        this.cost = builder.cost;
        this.gridX = builder.gridX;
        this.gridY = builder.gridY;
        this.prerequisites = builder.prerequisites;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public SkillBranch getBranch() {
        return branch;
    }

    public SkillType getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public boolean isEntryNode() {
        return prerequisites.isEmpty();
    }

    /** Nó central: o "início" da árvore, comprado automaticamente. */
    public boolean isCentral() {
        return id.endsWith("_central");
    }

    // ===== Builder =====
    public static Builder builder(String id) {
        return new Builder(id);
    }

    public static class Builder {
        private final String id;
        private String displayName = "";
        private String description = "";
        private SkillBranch branch = SkillBranch.DEFESA;
        private SkillType type = SkillType.PASSIVA;
        private int cost = 1;
        private int gridX = 0;
        private int gridY = 0;
        private final List<String> prerequisites = new ArrayList<>();

        private Builder(String id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.displayName = name;
            return this;
        }

        public Builder desc(String desc) {
            this.description = desc;
            return this;
        }

        public Builder branch(SkillBranch b) {
            this.branch = b;
            return this;
        }

        public Builder type(SkillType t) {
            this.type = t;
            return this;
        }

        public Builder cost(int c) {
            this.cost = c;
            return this;
        }

        public Builder grid(int x, int y) {
            this.gridX = x;
            this.gridY = y;
            return this;
        }

        public Builder prereq(String... ids) {
            for (String s : ids)
                this.prerequisites.add(s);
            return this;
        }

        public SkillNode build() {
            return new SkillNode(this);
        }
    }
}