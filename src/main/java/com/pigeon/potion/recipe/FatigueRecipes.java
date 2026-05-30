package com.pigeon.potion.recipe;

import com.pigeon.potion.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

public class FatigueRecipes {

    public static void register() {
        // 急迫药水 + 发酵蛛眼 = 疲劳药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.HASTE_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.FERMENTED_SPIDER_EYE;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.FATIGUE_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 疲劳药水 + 红石 = 延长版
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.FATIGUE_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.REDSTONE;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.LONG_FATIGUE_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 疲劳药水 + 荧石粉 = 强化版
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.FATIGUE_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.GLOWSTONE_DUST;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.STRONG_FATIGUE_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });
    }
}