package com.pigeon.potion.recipe;

import com.pigeon.potion.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

public class WitherRecipes {

    public static void register() {
        // 配方1：粗制药水 + 凋零玫瑰 = 凋零药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == Potions.AWKWARD;
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.WITHER_ROSE;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.WITHER_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 配方2：凋零药水 + 红石 = 延长版凋零药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.WITHER_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.REDSTONE;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.LONG_WITHER_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 配方3：凋零药水 + 荧石粉 = 强化版凋零药水（二级）
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.WITHER_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.GLOWSTONE_DUST;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.STRONG_WITHER_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });
    }
}