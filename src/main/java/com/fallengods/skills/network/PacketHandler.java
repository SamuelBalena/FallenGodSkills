package com.fallengods.skills.network;

import com.fallengods.skills.FallenGodsSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
        private static final String PROTOCOL_VERSION = "1";

        public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
                        ResourceLocation.fromNamespaceAndPath(FallenGodsSkills.MODID, "main"),
                        () -> PROTOCOL_VERSION,
                        PROTOCOL_VERSION::equals,
                        PROTOCOL_VERSION::equals);

        private static int id = 0;

        public static void register() {
                INSTANCE.registerMessage(id++, PacketSelectClass.class,
                                PacketSelectClass::encode, PacketSelectClass::decode, PacketSelectClass::handle);
                INSTANCE.registerMessage(id++, PacketSyncSkillData.class,
                                PacketSyncSkillData::encode, PacketSyncSkillData::decode, PacketSyncSkillData::handle);
                INSTANCE.registerMessage(id++, PacketOpenClassScreen.class,
                                PacketOpenClassScreen::encode, PacketOpenClassScreen::decode,
                                PacketOpenClassScreen::handle);
                INSTANCE.registerMessage(id++, PacketUnlockSkill.class,
                                PacketUnlockSkill::encode, PacketUnlockSkill::decode, PacketUnlockSkill::handle);
                INSTANCE.registerMessage(id++, PacketActivateSkill.class,
                                PacketActivateSkill::encode, PacketActivateSkill::decode, PacketActivateSkill::handle);
                INSTANCE.registerMessage(id++, PacketBindSkill.class,
                                PacketBindSkill::encode, PacketBindSkill::decode, PacketBindSkill::handle);
        }
}