package com.fallengods.skills.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOpenClassScreen {
    public PacketOpenClassScreen() {
    }

    public static void encode(PacketOpenClassScreen msg, FriendlyByteBuf buf) {
    }

    public static PacketOpenClassScreen decode(FriendlyByteBuf buf) {
        return new PacketOpenClassScreen();
    }

    public static void handle(PacketOpenClassScreen msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> com.fallengods.skills.client.ClientEvents.openClassScreen()));
        ctx.get().setPacketHandled(true);
    }
}