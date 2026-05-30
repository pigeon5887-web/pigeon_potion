package com.pigeon.potion.recipe;

import com.pigeon.potion.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

public class DivineRecipes {

    public static void register() {
        // 配方1：粗制药水 + 不死图腾 = 神圣药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == Potions.AWKWARD;
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.TOTEM_OF_UNDYING;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.DIVINE_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });

        // 配方2：神圣药水 + 红石 = 延长版神圣药水
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == ModPotions.DIVINE_POTION.get();
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.REDSTONE;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (isInput(input) && isIngredient(ingredient)) {
                    return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.LONG_DIVINE_POTION.get());
                }
                return ItemStack.EMPTY;
            }
        });
    }
}