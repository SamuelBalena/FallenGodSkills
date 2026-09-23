package com.fallengods.skills.client;

import com.fallengods.skills.capability.PlayerSkillDataImpl;
import net.minecraft.nbt.CompoundTag;

public class ClientSkillData {
    private static final PlayerSkillDataImpl data = new PlayerSkillDataImpl();

    public static void updateFromServer(CompoundTag nbt) {
        data.deserializeNBT(nbt);
    }

    public static PlayerSkillDataImpl get() {
        return data;
    }
}