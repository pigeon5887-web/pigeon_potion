package com.pigeon.potion.item;

import com.pigeon.potion.PigeonPotionMod;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PigeonPotionMod.MOD_ID);

    public static final RegistryObject<Item> MEDICINE_POUCH = ITEMS.register("medicine_pouch",
            () -> new MedicinePouchItem(new Item.Properties().stacksTo(1)));

    @Mod.EventBusSubscriber(modid = PigeonPotionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CreativeTabHandler {
        @SubscribeEvent
        public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(MEDICINE_POUCH.get());
            }
        }
    }
}