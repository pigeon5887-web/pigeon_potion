package com.pigeon.potion.recipe;

import com.pigeon.potion.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

public class ExcellentRecipes {

    public static void register() {
        // 配方1：力量药水 + 糖 = 卓越药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == Potions.STRENGTH;  // 力量药水
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.SUGAR;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.EXCELLENT_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 配方2：卓越药水 + 荧石粉 = 强化版卓越药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.EXCELLENT_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.GLOWSTONE_DUST;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.STRONG_EXCELLENT_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 配方3：卓越药水 + 红石 = 延长版卓越药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.EXCELLENT_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.REDSTONE;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.LONG_EXCELLENT_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });
    }
}