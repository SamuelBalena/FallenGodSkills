package com.fallengods.skills.capability;

import com.fallengods.skills.classsystem.ClassType;
import net.minecraft.nbt.CompoundTag;

public interface PlayerSkillData {
    ClassType getPlayerClass();

    void setPlayerClass(ClassType classType);

    int getSkillPoints();

    void setSkillPoints(int points);

    void addSkillPoints(int amount);

    boolean hasSkill(String skillId);

    void unlockSkill(String skillId);

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}