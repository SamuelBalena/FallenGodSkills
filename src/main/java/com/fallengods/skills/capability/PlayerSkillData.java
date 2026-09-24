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

    Map<String, Long> getCooldowns();

    long getCooldownEnd(String skillId);

    void setCooldownEnd(String skillId, long timestamp);

    // ===== Nível de XP (pra calcular pontos ganhos no level up) =====
    int getLastKnownLevel();

    void setLastKnownLevel(int level);

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}