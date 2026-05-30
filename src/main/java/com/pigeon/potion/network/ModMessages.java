package com.pigeon.potion.network;

import com.pigeon.potion.PigeonPotionMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1.0";
    private static int id = 0;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(PigeonPotionMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(id++, SwitchPotionPacket.class,
                SwitchPotionPacket::encode,
                SwitchPotionPacket::decode,
                SwitchPotionPacket::handle);
    }
}