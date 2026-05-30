package com.pigeon.potion;

import com.pigeon.potion.effect.ModEffects;
import com.pigeon.potion.gui.ModMenuTypes;
import com.pigeon.potion.item.ModItems;
import com.pigeon.potion.network.ModMessages;
import com.pigeon.potion.potion.ModPotions;
import com.pigeon.potion.recipe.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PigeonPotionMod.MOD_ID)
public class PigeonPotionMod {
    public static final String MOD_ID = "pigeon_potion";

    public PigeonPotionMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEffects.EFFECTS.register(bus);
        ModPotions.POTIONS.register(bus);
        ModItems.ITEMS.register(bus);
        ModMenuTypes.MENU_TYPES.register(bus);

        bus.addListener(this::commonSetup);

        // 注册网络
        ModMessages.register();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            HasteRecipes.register();
            ThornsRecipes.register();
            ArmorBoostRecipes.register();
            ArmorBreakRecipes.register();
            ExcellentRecipes.register();
            WarriorRecipes.register();
            BerserkRecipes.register();
            DivineRecipes.register();
            WitherRecipes.register();
            FatigueRecipes.register();
            LuckRecipes.register();
            NauseaRecipes.register();
            ResistanceRecipes.register();
            DodgeRecipes.register();
        });
    }
}