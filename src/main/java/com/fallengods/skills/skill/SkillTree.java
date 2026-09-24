package com.fallengods.skills.skill;

import com.fallengods.skills.classsystem.ClassType;

import java.util.ArrayList;
import java.util.List;

public class SkillTree {

    private final ClassType classType;
    private final List<SkillNode> nodes = new ArrayList<>();

    public SkillTree(ClassType classType) {
        this.classType = classType;
    }

    public ClassType getClassType() {
        return classType;
    }

    public SkillTree add(SkillNode node) {
        nodes.add(node);
        return this;
    }

    public List<SkillNode> getNodes() {
        return nodes;
    }

    public SkillNode getNode(String id) {
        for (SkillNode n : nodes) {
            if (n.getId().equals(id))
                return n;
        }
        return null;
    }

    public List<SkillNode> getEntryNodes() {
        List<SkillNode> result = new ArrayList<>();
        for (SkillNode n : nodes) {
            if (n.isEntryNode())
                result.add(n);
        }
        return result;
    }

    public List<SkillNode> getNodesByBranch(SkillBranch branch) {
        List<SkillNode> result = new ArrayList<>();
        for (SkillNode n : nodes) {
            if (n.getBranch() == branch)
                result.add(n);
        }
        return result;
    }
}