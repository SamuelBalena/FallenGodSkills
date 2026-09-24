package com.fallengods.skills.capability;

import com.fallengods.skills.classsystem.ClassType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerSkillDataImpl implements PlayerSkillData {
    private ClassType playerClass = ClassType.NONE;
    private int skillPoints = 0;
    private int lastKnownLevel = -1;
    private final Set<String> unlockedSkills = new HashSet<>();
    private final Map<String, Long> cooldowns = new HashMap<>();

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
    public int getLastKnownLevel() {
        return lastKnownLevel;
    }

    @Override
    public void setLastKnownLevel(int level) {
        this.lastKnownLevel = level;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("PlayerClass", playerClass.name());
        nbt.putInt("SkillPoints", skillPoints);
        nbt.putInt("LastKnownLevel", lastKnownLevel);

        ListTag skillList = new ListTag();
        for (String skill : unlockedSkills)
            skillList.add(StringTag.valueOf(skill));
        nbt.put("UnlockedSkills", skillList);

        CompoundTag cdTag = new CompoundTag();
        for (Map.Entry<String, Long> entry : cooldowns.entrySet()) {
            cdTag.putLong(entry.getKey(), entry.getValue());
        }
        nbt.put("Cooldowns", cdTag);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerClass = ClassType.valueOf(nbt.getString("PlayerClass"));
        skillPoints = nbt.getInt("SkillPoints");
        lastKnownLevel = nbt.contains("LastKnownLevel") ? nbt.getInt("LastKnownLevel") : -1;

        unlockedSkills.clear();
        ListTag skillList = nbt.getList("UnlockedSkills", 8);
        for (int i = 0; i < skillList.size(); i++)
            unlockedSkills.add(skillList.getString(i));

        cooldowns.clear();
        CompoundTag cdTag = nbt.getCompound("Cooldowns");
        for (String key : cdTag.getAllKeys()) {
            cooldowns.put(key, cdTag.getLong(key));
        }
    }
}