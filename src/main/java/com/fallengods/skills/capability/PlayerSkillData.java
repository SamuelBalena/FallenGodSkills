package com.fallengods.skills.capability;

import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.nbt.CompoundTag;

import java.util.Map;
import java.util.Set;

public interface PlayerSkillData {
    ClassType getPlayerClass();

    void setPlayerClass(ClassType classType);

    int getSkillPoints();

    void setSkillPoints(int points);

    void addSkillPoints(int amount);

    boolean hasSkill(String skillId);

    void unlockSkill(String skillId);

    Set<String> getUnlockedSkillsSet();

    // ===== Cooldowns =====
    Map<String, Long> getCooldowns();

    long getCooldownEnd(String skillId);

    void setCooldownEnd(String skillId, long timestamp);

    // ===== Bindings (slot 0..11 → skillId) =====
    Map<Integer, String> getSkillBindings();

    String getBindingForSlot(int slot);

    void setBinding(int slot, String skillId);

    int getSlotForSkill(String skillId);

    // ===== Bônus acumulados =====
    Map<StatType, Double> getAccumulatedBonuses();

    double getBonus(StatType type);

    void setAccumulatedBonuses(Map<StatType, Double> bonuses);

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}