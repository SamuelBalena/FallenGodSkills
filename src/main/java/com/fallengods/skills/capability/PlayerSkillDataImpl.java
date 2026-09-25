package com.fallengods.skills.capability;

import com.fallengods.skills.classsystem.ClassType;
import com.fallengods.skills.skill.effect.StatType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerSkillDataImpl implements PlayerSkillData {
    private ClassType playerClass = ClassType.NONE;
    private int skillPoints = 0;
    private final Set<String> unlockedSkills = new HashSet<>();
    private final Map<String, Long> cooldowns = new HashMap<>();
    private final Map<Integer, String> skillBindings = new HashMap<>();
    private final Map<StatType, Double> accumulatedBonuses = new EnumMap<>(StatType.class);

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
    public Set<String> getUnlockedSkillsSet() {
        return unlockedSkills;
    }

    @Override
    public Map<String, Long> getCooldowns() {
        return cooldowns;
    }

    @Override
    public long getCooldownEnd(String skillId) {
        return cooldowns.getOrDefault(skillId, 0L);
    }

    @Override
    public void setCooldownEnd(String skillId, long timestamp) {
        cooldowns.put(skillId, timestamp);
    }

    @Override
    public Map<Integer, String> getSkillBindings() {
        return skillBindings;
    }

    @Override
    public String getBindingForSlot(int slot) {
        return skillBindings.get(slot);
    }

    @Override
    public void setBinding(int slot, String skillId) {
        if (skillId == null || skillId.isEmpty()) {
            skillBindings.remove(slot);
        } else {
            skillBindings.put(slot, skillId);
        }
    }

    @Override
    public int getSlotForSkill(String skillId) {
        for (Map.Entry<Integer, String> entry : skillBindings.entrySet()) {
            if (entry.getValue().equals(skillId))
                return entry.getKey();
        }
        return -1;
    }

    @Override
    public Map<StatType, Double> getAccumulatedBonuses() {
        return accumulatedBonuses;
    }

    @Override
    public double getBonus(StatType type) {
        return accumulatedBonuses.getOrDefault(type, 0.0);
    }

    @Override
    public void setAccumulatedBonuses(Map<StatType, Double> bonuses) {
        accumulatedBonuses.clear();
        accumulatedBonuses.putAll(bonuses);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("PlayerClass", playerClass.name());
        nbt.putInt("SkillPoints", skillPoints);

        ListTag skillList = new ListTag();
        for (String skill : unlockedSkills)
            skillList.add(StringTag.valueOf(skill));
        nbt.put("UnlockedSkills", skillList);

        CompoundTag cdTag = new CompoundTag();
        for (Map.Entry<String, Long> entry : cooldowns.entrySet()) {
            cdTag.putLong(entry.getKey(), entry.getValue());
        }
        nbt.put("Cooldowns", cdTag);

        CompoundTag bindTag = new CompoundTag();
        for (Map.Entry<Integer, String> entry : skillBindings.entrySet()) {
            bindTag.putString(String.valueOf(entry.getKey()), entry.getValue());
        }
        nbt.put("SkillBindings", bindTag);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerClass = ClassType.valueOf(nbt.getString("PlayerClass"));
        skillPoints = nbt.getInt("SkillPoints");

        unlockedSkills.clear();
        ListTag skillList = nbt.getList("UnlockedSkills", 8);
        for (int i = 0; i < skillList.size(); i++)
            unlockedSkills.add(skillList.getString(i));

        cooldowns.clear();
        CompoundTag cdTag = nbt.getCompound("Cooldowns");
        for (String key : cdTag.getAllKeys())
            cooldowns.put(key, cdTag.getLong(key));

        skillBindings.clear();
        CompoundTag bindTag = nbt.getCompound("SkillBindings");
        for (String key : bindTag.getAllKeys()) {
            try {
                skillBindings.put(Integer.parseInt(key), bindTag.getString(key));
            } catch (NumberFormatException ignored) {
            }
        }

        accumulatedBonuses.clear();
    }
}