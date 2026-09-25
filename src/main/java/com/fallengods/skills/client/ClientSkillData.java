package com.fallengods.skills.client;

import com.fallengods.skills.capability.PlayerSkillDataImpl;
import net.minecraft.nbt.CompoundTag;

public class ClientSkillData {
    private static final PlayerSkillDataImpl data = new PlayerSkillDataImpl();

    private static Runnable changeListener = null;

    public static void updateFromServer(CompoundTag nbt) {
        data.deserializeNBT(nbt);
        if (changeListener != null) {
            changeListener.run();
        }
    }

    public static void clear() {
        data.setPlayerClass(com.fallengods.skills.classsystem.ClassType.NONE);
        data.setSkillPoints(0);
        data.getUnlockedSkillsSet().clear();
        data.getAccumulatedBonuses().clear();
        data.getSkillBindings().clear();
        data.getCooldowns().clear();
        if (changeListener != null) {
            changeListener.run();
        }
    }

    public static void setChangeListener(Runnable listener) {
        changeListener = listener;
    }

    public static void clearChangeListener() {
        changeListener = null;
    }

    public static PlayerSkillDataImpl get() {
        return data;
    }
}