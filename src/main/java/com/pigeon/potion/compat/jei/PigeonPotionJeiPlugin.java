package com.pigeon.potion.compat.jei;

import com.pigeon.potion.PigeonPotionMod;
import com.pigeon.potion.potion.ModPotions;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.IRecipeRegistration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import java.util.List;

@JeiPlugin
public class PigeonPotionJeiPlugin implements IModPlugin {

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(PigeonPotionMod.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // 急迫药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.GOLD_INGOT, ModPotions.HASTE_POTION.get());
        addBrewingRecipe(registration, ModPotions.HASTE_POTION.get(), Items.REDSTONE, ModPotions.LONG_HASTE_POTION.get());
        addBrewingRecipe(registration, ModPotions.HASTE_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_HASTE_POTION.get());

        // 疲劳药水
        addBrewingRecipe(registration, ModPotions.HASTE_POTION.get(), Items.FERMENTED_SPIDER_EYE, ModPotions.FATIGUE_POTION.get());
        addBrewingRecipe(registration, ModPotions.FATIGUE_POTION.get(), Items.REDSTONE, ModPotions.LONG_FATIGUE_POTION.get());
        addBrewingRecipe(registration, ModPotions.FATIGUE_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_FATIGUE_POTION.get());

        // 反伤药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.CACTUS, ModPotions.THORNS_POTION.get());
        addBrewingRecipe(registration, ModPotions.THORNS_POTION.get(), Items.REDSTONE, ModPotions.LONG_THORNS_POTION.get());
        addBrewingRecipe(registration, ModPotions.THORNS_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_THORNS_POTION.get());

        // 构甲药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.IRON_INGOT, ModPotions.ARMOR_BOOST_POTION.get());
        addBrewingRecipe(registration, ModPotions.ARMOR_BOOST_POTION.get(), Items.REDSTONE, ModPotions.LONG_ARMOR_BOOST_POTION.get());
        addBrewingRecipe(registration, ModPotions.ARMOR_BOOST_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_ARMOR_BOOST_POTION.get());

        // 破甲药水
        addBrewingRecipe(registration, ModPotions.ARMOR_BOOST_POTION.get(), Items.FERMENTED_SPIDER_EYE, ModPotions.ARMOR_BREAK_POTION.get());
        addBrewingRecipe(registration, ModPotions.ARMOR_BREAK_POTION.get(), Items.REDSTONE, ModPotions.LONG_ARMOR_BREAK_POTION.get());
        addBrewingRecipe(registration, ModPotions.ARMOR_BREAK_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_ARMOR_BREAK_POTION.get());

        // 卓越药水
        addBrewingRecipe(registration, Potions.STRENGTH, Items.SUGAR, ModPotions.EXCELLENT_POTION.get());
        addBrewingRecipe(registration, ModPotions.EXCELLENT_POTION.get(), Items.REDSTONE, ModPotions.LONG_EXCELLENT_POTION.get());
        addBrewingRecipe(registration, ModPotions.EXCELLENT_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_EXCELLENT_POTION.get());

        // 战神药水
        addBrewingRecipe(registration, ModPotions.EXCELLENT_POTION.get(), Items.RABBIT_FOOT, ModPotions.WARRIOR_POTION.get());
        addBrewingRecipe(registration, ModPotions.WARRIOR_POTION.get(), Items.REDSTONE, ModPotions.LONG_WARRIOR_POTION.get());
        addBrewingRecipe(registration, ModPotions.WARRIOR_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_WARRIOR_POTION.get());

        // 狂暴药水
        addBrewingRecipe(registration, ModPotions.WARRIOR_POTION.get(), Items.NETHER_STAR, ModPotions.BERSERK_POTION.get());
        addBrewingRecipe(registration, ModPotions.BERSERK_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_BERSERK_POTION.get());

        // 神圣药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.TOTEM_OF_UNDYING, ModPotions.DIVINE_POTION.get());
        addBrewingRecipe(registration, ModPotions.DIVINE_POTION.get(), Items.REDSTONE, ModPotions.LONG_DIVINE_POTION.get());

        // 凋零药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.WITHER_ROSE, ModPotions.WITHER_POTION.get());
        addBrewingRecipe(registration, ModPotions.WITHER_POTION.get(), Items.REDSTONE, ModPotions.LONG_WITHER_POTION.get());
        addBrewingRecipe(registration, ModPotions.WITHER_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_WITHER_POTION.get());

        // 幸运药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.EMERALD, ModPotions.LUCK_POTION.get());
        addBrewingRecipe(registration, ModPotions.LUCK_POTION.get(), Items.REDSTONE, ModPotions.LONG_LUCK_POTION.get());

        // 霉运药水
        addBrewingRecipe(registration, ModPotions.LUCK_POTION.get(), Items.FERMENTED_SPIDER_EYE, ModPotions.BAD_LUCK_POTION.get());
        addBrewingRecipe(registration, ModPotions.BAD_LUCK_POTION.get(), Items.REDSTONE, ModPotions.LONG_BAD_LUCK_POTION.get());

        // 反胃药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.ROTTEN_FLESH, ModPotions.NAUSEA_POTION.get());
        addBrewingRecipe(registration, ModPotions.NAUSEA_POTION.get(), Items.REDSTONE, ModPotions.LONG_NAUSEA_POTION.get());
        addBrewingRecipe(registration, ModPotions.NAUSEA_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_NAUSEA_POTION.get());

        // 闪避药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.ENDER_PEARL, ModPotions.DODGE_POTION.get());
        addBrewingRecipe(registration, ModPotions.DODGE_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_DODGE_POTION.get());

        // 抗性药水
        addBrewingRecipe(registration, Potions.TURTLE_MASTER, Items.AMETHYST_SHARD, ModPotions.RESISTANCE_POTION.get());
        addBrewingRecipe(registration, Potions.LONG_TURTLE_MASTER, Items.AMETHYST_SHARD, ModPotions.LONG_RESISTANCE_POTION.get());
        addBrewingRecipe(registration, Potions.STRONG_TURTLE_MASTER, Items.AMETHYST_SHARD, ModPotions.STRONG_RESISTANCE_POTION.get());

        //亲近药水
        addBrewingRecipe(registration, Potions.AWKWARD, Items.POPPY, ModPotions.FRIENDLY_POTION.get());

        //驱逐药水
        addBrewingRecipe(registration, ModPotions.FRIENDLY_POTION.get(), Items.FERMENTED_SPIDER_EYE, ModPotions.REPEL_POTION.get());
    }

    private void addBrewingRecipe(
            IRecipeRegistration registration,
            Potion inputPotion,
            Item ingredient,
            Potion outputPotion
    ) {
        addBrewingRecipe(registration, Items.POTION, inputPotion, ingredient, Items.POTION, outputPotion);
        addBrewingRecipe(registration, Items.SPLASH_POTION, inputPotion, ingredient, Items.SPLASH_POTION, outputPotion);
        addBrewingRecipe(registration, Items.LINGERING_POTION, inputPotion, ingredient, Items.LINGERING_POTION, outputPotion);
    }

    private void addBrewingRecipe(
            IRecipeRegistration registration,
            Item inputItem,
            Potion inputPotion,
            Item ingredient,
            Item outputItem,
            Potion outputPotion
    ) {
        ItemStack input = PotionUtils.setPotion(new ItemStack(inputItem), inputPotion);
        ItemStack ingredientStack = new ItemStack(ingredient);
        ItemStack output = PotionUtils.setPotion(new ItemStack(outputItem), outputPotion);

        registration.addRecipes(
                RecipeTypes.BREWING,
                List.of(new IJeiBrewingRecipe() {
                    @Override
                    public List<ItemStack> getPotionInputs() {
                        return List.of(input);
                    }

                    @Override
                    public List<ItemStack> getIngredients() {
                        return List.of(ingredientStack);
                    }

                    @Override
                    public ItemStack getPotionOutput() {
                        return output;
                    }

                    @Override
                    public int getBrewingSteps() {
                        return 1;
                    }
                })
        );
    }
}
