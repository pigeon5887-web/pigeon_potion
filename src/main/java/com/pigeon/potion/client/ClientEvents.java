package com.pigeon.potion.client;

import com.pigeon.potion.PigeonPotionMod;
import com.pigeon.potion.gui.MedicinePouchScreen;
import com.pigeon.potion.gui.ModMenuTypes;
import com.pigeon.potion.item.MedicinePouchItem;
import com.pigeon.potion.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PigeonPotionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    private static int sneakTickCount = 0;
    private static boolean wasSneaking = false;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.MEDICINE_POUCH.get(), MedicinePouchScreen::new);
        });
    }

    @Mod.EventBusSubscriber(modid = PigeonPotionMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientTickHandler {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;

            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            Player player = mc.player;

            ItemStack pouch = ItemStack.EMPTY;
            if (player.getMainHandItem().getItem() == ModItems.MEDICINE_POUCH.get()) {
                pouch = player.getMainHandItem();
            } else if (player.getOffhandItem().getItem() == ModItems.MEDICINE_POUCH.get()) {
                pouch = player.getOffhandItem();
            }

            if (pouch.isEmpty()) return;

            boolean isSneaking = player.isShiftKeyDown();

            if (isSneaking && !wasSneaking) {
                MedicinePouchItem.displayCurrentSelected(player, pouch);
                sneakTickCount = 0;
            } else if (isSneaking && wasSneaking) {
                sneakTickCount++;
                if (sneakTickCount >= 20) {
                    MedicinePouchItem.displayCurrentSelected(player, pouch);
                    sneakTickCount = 0;
                }
            } else {
                sneakTickCount = 0;
            }

            wasSneaking = isSneaking;
        }
    }
}