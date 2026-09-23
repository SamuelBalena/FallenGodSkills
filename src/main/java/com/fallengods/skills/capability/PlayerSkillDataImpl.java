package com.fallengods.skills.capability;

import com.fallengods.skills.classsystem.ClassType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

import java.util.HashSet;
import java.util.Set;

public class PlayerSkillDataImpl implements PlayerSkillData {
    private ClassType playerClass = ClassType.NONE;
    private int skillPoints = 0;
    private final Set<String> unlockedSkills = new HashSet<>();

    @Override
    public ClassType getPlayerClass() {
        return playerClass;
    }

    @Override
    public void setPlayerClass(ClassType classType) {
        this.playerClass = classType;
    }

    @Override
    public int getSkillPoints() {
        return skillPoints;
    }

    @Override
    public void setSkillPoints(int points) {
        this.skillPoints = points;
    }

    @Override
    public void addSkillPoints(int amount) {
        this.skillPoints += amount;
    }

    @Override
    public boolean hasSkill(String skillId) {
        return unlockedSkills.contains(skillId);
    }

    @Override
    public void unlockSkill(String skillId) {
        unlockedSkills.add(skillId);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("PlayerClass", playerClass.name());
        nbt.putInt("SkillPoints", skillPoints);
        ListTag skillList = new ListTag();
        for (String skill : unlockedSkills) {
            skillList.add(StringTag.valueOf(skill));
        }
        nbt.put("UnlockedSkills", skillList);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerClass = ClassType.valueOf(nbt.getString("PlayerClass"));
        skillPoints = nbt.getInt("SkillPoints");
        unlockedSkills.clear();
        ListTag skillList = nbt.getList("UnlockedSkills", 8);
        for (int i = 0; i < skillList.size(); i++) {
            unlockedSkills.add(skillList.getString(i));
        }
    }
}