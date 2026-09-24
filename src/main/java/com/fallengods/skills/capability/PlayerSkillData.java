package com.fallengods.skills.capability;

import com.fallengods.skills.classsystem.ClassType;
import net.minecraft.nbt.CompoundTag;

import java.util.Map;

public interface PlayerSkillData {
    ClassType getPlayerClass();

    void setPlayerClass(ClassType classType);

    int getSkillPoints();

    void setSkillPoints(int points);

    void addSkillPoints(int amount);

    boolean hasSkill(String skillId);

    void unlockSkill(String skillId);

    // ===== Cooldowns (skillId -> timestamp do próximo uso permitido, em ms) =====
    Map<String, Long> getCooldowns();

    long getCooldownEnd(String skillId);

    void setCooldownEnd(String skillId, long timestamp);

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}