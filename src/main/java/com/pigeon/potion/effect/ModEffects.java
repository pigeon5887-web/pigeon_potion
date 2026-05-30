package com.pigeon.potion.effect;

import com.pigeon.potion.PigeonPotionMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, PigeonPotionMod.MOD_ID);

    public static final RegistryObject<MobEffect> THORNS = EFFECTS.register("thorns", ThornsEffect::new);
    public static final RegistryObject<MobEffect> ARMOR_BOOST = EFFECTS.register("armor_boost", ArmorBoostEffect::new);
    public static final RegistryObject<MobEffect> ARMOR_BREAK = EFFECTS.register("armor_break", ArmorBreakEffect::new);
    public static final RegistryObject<MobEffect> EXCELLENT = EFFECTS.register("excellent", ExcellentEffect::new);
    public static final RegistryObject<MobEffect> WARRIOR = EFFECTS.register("warrior", WarriorEffect::new);
    public static final RegistryObject<MobEffect> BERSERK = EFFECTS.register("berserk", BerserkEffect::new);
    public static final RegistryObject<MobEffect> DIVINE = EFFECTS.register("divine", DivineEffect::new);
    public static final RegistryObject<MobEffect> DODGE = EFFECTS.register("dodge", DodgeEffect::new);
}