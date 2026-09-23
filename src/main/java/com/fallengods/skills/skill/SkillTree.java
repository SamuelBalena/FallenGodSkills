package com.fallengods.skills.skill;

import com.fallengods.skills.classsystem.ClassType;

import java.util.ArrayList;
import java.util.List;

public class SkillTree {
    private final ClassType classType;
    private final List<Skill> skills = new ArrayList<>();

    public SkillTree(ClassType classType) {
        this.classType = classType;
    }

    public ClassType getClassType() {
        return classType;
    }

    public SkillTree addSkill(Skill skill) {
        skills.add(skill);
        return this;
    }

    public List<Skill> getSkills() {
        return skills;
    }
}