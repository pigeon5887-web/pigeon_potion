package com.pigeon.potion.gui;

import com.pigeon.potion.PigeonPotionMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, PigeonPotionMod.MOD_ID);

    public static final RegistryObject<MenuType<MedicinePouchMenu>> MEDICINE_POUCH =
            MENU_TYPES.register("medicine_pouch",
                    () -> IForgeMenuType.create(MedicinePouchMenu::new));
}