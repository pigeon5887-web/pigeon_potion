package com.pigeon.potion.potion;

import com.pigeon.potion.PigeonPotionMod;
import com.pigeon.potion.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, PigeonPotionMod.MOD_ID);

    // ========== 急迫药水 ==========
    public static final RegistryObject<Potion> HASTE_POTION = POTIONS.register("haste_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 0)));
    public static final RegistryObject<Potion> LONG_HASTE_POTION = POTIONS.register("long_haste_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 9600, 0)));
    public static final RegistryObject<Potion> STRONG_HASTE_POTION = POTIONS.register("strong_haste_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 1)));

    // ========== 疲劳药水 ==========
    public static final RegistryObject<Potion> FATIGUE_POTION = POTIONS.register("fatigue_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 3600, 0)));
    public static final RegistryObject<Potion> LONG_FATIGUE_POTION = POTIONS.register("long_fatigue_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 9600, 0)));
    public static final RegistryObject<Potion> STRONG_FATIGUE_POTION = POTIONS.register("strong_fatigue_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1800, 1)));

    // ========== 反伤药水 ==========
    public static final RegistryObject<Potion> THORNS_POTION = POTIONS.register("thorns_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.THORNS.get(), 3600, 0)));
    public static final RegistryObject<Potion> LONG_THORNS_POTION = POTIONS.register("long_thorns_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.THORNS.get(), 9600, 0)));
    public static final RegistryObject<Potion> STRONG_THORNS_POTION = POTIONS.register("strong_thorns_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.THORNS.get(), 1800, 1)));

    // ========== 构甲药水 ==========
    public static final RegistryObject<Potion> ARMOR_BOOST_POTION = POTIONS.register("armor_boost_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ARMOR_BOOST.get(), 3600, 0)));
    public static final RegistryObject<Potion> LONG_ARMOR_BOOST_POTION = POTIONS.register("long_armor_boost_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ARMOR_BOOST.get(), 9600, 0)));
    public static final RegistryObject<Potion> STRONG_ARMOR_BOOST_POTION = POTIONS.register("strong_armor_boost_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ARMOR_BOOST.get(), 1800, 1)));

    // ========== 破甲药水 ==========
    public static final RegistryObject<Potion> ARMOR_BREAK_POTION = POTIONS.register("armor_break_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ARMOR_BREAK.get(), 3600, 0)));
    public static final RegistryObject<Potion> LONG_ARMOR_BREAK_POTION = POTIONS.register("long_armor_break_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ARMOR_BREAK.get(), 9600, 0)));
    public static final RegistryObject<Potion> STRONG_ARMOR_BREAK_POTION = POTIONS.register("strong_armor_break_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ARMOR_BREAK.get(), 1800, 1)));

    //========== 卓越药水 ==========
    public static final RegistryObject<Potion> EXCELLENT_POTION = POTIONS.register("excellent_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.EXCELLENT.get(), 3600, 0)));

    public static final RegistryObject<Potion> LONG_EXCELLENT_POTION = POTIONS.register("long_excellent_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.EXCELLENT.get(), 9600, 0)));

    public static final RegistryObject<Potion> STRONG_EXCELLENT_POTION = POTIONS.register("strong_excellent_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.EXCELLENT.get(), 1800, 1)));

    // ========== 战神药水 ==========
    public static final RegistryObject<Potion> WARRIOR_POTION = POTIONS.register("warrior_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.WARRIOR.get(), 3600, 0)));

    public static final RegistryObject<Potion> LONG_WARRIOR_POTION = POTIONS.register("long_warrior_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.WARRIOR.get(), 9600, 0)));

    public static final RegistryObject<Potion> STRONG_WARRIOR_POTION = POTIONS.register("strong_warrior_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.WARRIOR.get(), 1800, 1)));

    // ========== 狂暴药水 ==========
    public static final RegistryObject<Potion> BERSERK_POTION = POTIONS.register("berserk_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BERSERK.get(), 1200, 0)));

    public static final RegistryObject<Potion> STRONG_BERSERK_POTION = POTIONS.register("strong_berserk_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BERSERK.get(), 600, 1)));

    // ========== 神圣药水 ==========
    public static final RegistryObject<Potion> DIVINE_POTION = POTIONS.register("divine_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DIVINE.get(), 2400, 0)));
    public static final RegistryObject<Potion> LONG_DIVINE_POTION = POTIONS.register("long_divine_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DIVINE.get(), 4800, 0)));

    // ========== 凋零药水 ==========
    public static final RegistryObject<Potion> WITHER_POTION = POTIONS.register("wither_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.WITHER, 800, 0)));
    public static final RegistryObject<Potion> LONG_WITHER_POTION = POTIONS.register("long_wither_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.WITHER, 1800, 0)));
    public static final RegistryObject<Potion> STRONG_WITHER_POTION = POTIONS.register("strong_wither_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.WITHER, 400, 1)));

    // ========== 幸运药水 ==========
    public static final RegistryObject<Potion> LUCK_POTION = POTIONS.register("luck_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 3600, 0)));

    public static final RegistryObject<Potion> LONG_LUCK_POTION = POTIONS.register("long_luck_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 9600, 0)));

    // ========== 霉运药水 ==========
    public static final RegistryObject<Potion> BAD_LUCK_POTION = POTIONS.register("bad_luck_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 3600, 0)));

    public static final RegistryObject<Potion> LONG_BAD_LUCK_POTION = POTIONS.register("long_bad_luck_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 9600, 0)));

    // ========== 反胃药水 ==========
    public static final RegistryObject<Potion> NAUSEA_POTION = POTIONS.register("nausea_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.CONFUSION, 600, 0)));  // 30秒 = 600 tick

    public static final RegistryObject<Potion> LONG_NAUSEA_POTION = POTIONS.register("long_nausea_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.CONFUSION, 1800, 0))); // 1分30秒 = 1800 tick

    public static final RegistryObject<Potion> STRONG_NAUSEA_POTION = POTIONS.register("strong_nausea_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.CONFUSION, 300, 1)));   // 15秒 = 300 tick，二级

    // ========== 闪避药水 ==========
    public static final RegistryObject<Potion> DODGE_POTION = POTIONS.register("dodge_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DODGE.get(), 3600, 0)));
    public static final RegistryObject<Potion> STRONG_DODGE_POTION = POTIONS.register("strong_dodge_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DODGE.get(), 1800, 1)));

    // ========== 抗性药水 ==========
    public static final RegistryObject<Potion> RESISTANCE_POTION = POTIONS.register("resistance_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 2)));
    public static final RegistryObject<Potion> RESISTANCE_POTION_PLUS_1 = POTIONS.register("resistance_potion_plus_1",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 500, 2)));
    public static final RegistryObject<Potion> RESISTANCE_POTION_PLUS_2 = POTIONS.register("resistance_potion_plus_2",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 620, 2)));
    public static final RegistryObject<Potion> RESISTANCE_POTION_PLUS_3 = POTIONS.register("resistance_potion_plus_3",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 760, 2)));
    public static final RegistryObject<Potion> LONG_RESISTANCE_POTION = POTIONS.register("long_resistance_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 2)));
    public static final RegistryObject<Potion> LONG_RESISTANCE_POTION_PLUS_1 = POTIONS.register("long_resistance_potion_plus_1",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 920, 2)));
    public static final RegistryObject<Potion> LONG_RESISTANCE_POTION_PLUS_2 = POTIONS.register("long_resistance_potion_plus_2",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1040, 2)));
    public static final RegistryObject<Potion> STRONG_RESISTANCE_POTION = POTIONS.register("strong_resistance_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 3)));
    public static final RegistryObject<Potion> STRONG_RESISTANCE_POTION_PLUS_1 = POTIONS.register("strong_resistance_potion_plus_1",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 520, 3)));

    // ========== 亲近药水 ==========
    public static final RegistryObject<Potion> FRIENDLY_POTION = POTIONS.register("friendly_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.FRIENDLY.get(), 1200, 0)));

    // ========== 驱逐药水 ==========
    public static final RegistryObject<Potion> REPEL_POTION = POTIONS.register("repel_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.REPEL.get(), 1200, 0)));
}