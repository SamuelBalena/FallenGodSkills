package com.fallengods.skills.client;

import com.fallengods.skills.capability.PlayerSkillDataImpl;
import net.minecraft.nbt.CompoundTag;

public class ClientSkillData {
    private static final PlayerSkillDataImpl data = new PlayerSkillDataImpl();

    /** Callback que a tela registra pra saber quando os dados mudaram. */
    private static Runnable changeListener = null;

    public static void updateFromServer(CompoundTag nbt) {
        data.deserializeNBT(nbt);
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