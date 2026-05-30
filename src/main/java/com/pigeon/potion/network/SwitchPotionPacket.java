package com.pigeon.potion.network;

import com.pigeon.potion.item.MedicinePouchItem;
import com.pigeon.potion.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwitchPotionPacket {

    private final int direction; // 0=上一个, 1=下一个

    public SwitchPotionPacket(int direction) {
        this.direction = direction;
    }

    public static void encode(SwitchPotionPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.direction);
    }

    public static SwitchPotionPacket decode(FriendlyByteBuf buf) {
        return new SwitchPotionPacket(buf.readInt());
    }

    public static void handle(SwitchPotionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            ItemStack pouch = ItemStack.EMPTY;
            if (player.getMainHandItem().getItem() == ModItems.MEDICINE_POUCH.get()) {
                pouch = player.getMainHandItem();
            } else if (player.getOffhandItem().getItem() == ModItems.MEDICINE_POUCH.get()) {
                pouch = player.getOffhandItem();
            }

            if (!pouch.isEmpty()) {
                if (msg.direction == 0) {
                    MedicinePouchItem.switchPreviousServer(player, pouch);
                } else {
                    MedicinePouchItem.switchNextServer(player, pouch);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}