package com.pigeon.potion.client;

import com.pigeon.potion.PigeonPotionMod;
import com.pigeon.potion.item.ModItems;
import com.pigeon.potion.network.ModMessages;
import com.pigeon.potion.network.SwitchPotionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PigeonPotionMod.MOD_ID, value = Dist.CLIENT)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        // 检查是否按住 Alt 键 - 使用 Screen.hasAltDown()
        boolean isAltDown = net.minecraft.client.gui.screens.Screen.hasAltDown();

        if (!isAltDown) return;

        // 检查是否手持药囊
        ItemStack pouch = ItemStack.EMPTY;
        if (player.getMainHandItem().getItem() == ModItems.MEDICINE_POUCH.get()) {
            pouch = player.getMainHandItem();
        } else if (player.getOffhandItem().getItem() == ModItems.MEDICINE_POUCH.get()) {
            pouch = player.getOffhandItem();
        }

        if (pouch.isEmpty()) return;

        // 获取滚动方向
        double scrollDelta = event.getScrollDelta();

        if (scrollDelta > 0) {
            // 向下滚动 -> 下一个药水
            ModMessages.INSTANCE.sendToServer(new SwitchPotionPacket(0));
            event.setCanceled(true);
        } else if (scrollDelta < 0) {
            // 向上滚动 -> 上一个药水
            ModMessages.INSTANCE.sendToServer(new SwitchPotionPacket(1));
            event.setCanceled(true);
        }
    }
}