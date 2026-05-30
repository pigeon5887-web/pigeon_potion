package com.pigeon.potion.recipe;

import com.pigeon.potion.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

public class WarriorRecipes {

    public static void register() {
        // 配方1：卓越药水 + 兔子脚 = 战神药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.EXCELLENT_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.RABBIT_FOOT;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.WARRIOR_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 配方2：战神药水 + 荧石粉 = 二级战神药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.WARRIOR_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.GLOWSTONE_DUST;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.STRONG_WARRIOR_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 配方3：战神药水 + 红石 = 延长版战神药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.WARRIOR_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.REDSTONE;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.LONG_WARRIOR_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });
    }
}