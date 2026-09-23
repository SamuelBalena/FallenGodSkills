package com.fallengods.skills.network;

import com.fallengods.skills.client.ClientSkillData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncSkillData {
    private final CompoundTag data;

    public PacketSyncSkillData(CompoundTag data) {
        this.data = data;
    }

    public static void encode(PacketSyncSkillData msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.data);
    }

    public static PacketSyncSkillData decode(FriendlyByteBuf buf) {
        return new PacketSyncSkillData(buf.readNbt());
    }

    public static void handle(PacketSyncSkillData msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ClientSkillData.updateFromServer(msg.data)));
        ctx.get().setPacketHandled(true);
    }
}