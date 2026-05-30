package com.pigeon.potion.recipe;

import com.pigeon.potion.potion.ModPotions;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class ResistanceRecipes {

    public static void register() {
        BrewingRecipeRegistry.addRecipe(create(
                Potions.TURTLE_MASTER,
                new Potion[]{
                        ModPotions.RESISTANCE_POTION.get(),
                        ModPotions.RESISTANCE_POTION_PLUS_1.get(),
                        ModPotions.RESISTANCE_POTION_PLUS_2.get(),
                        ModPotions.RESISTANCE_POTION_PLUS_3.get()
                }
        ));

        BrewingRecipeRegistry.addRecipe(create(
                Potions.LONG_TURTLE_MASTER,
                new Potion[]{
                        ModPotions.LONG_RESISTANCE_POTION.get(),
                        ModPotions.LONG_RESISTANCE_POTION_PLUS_1.get(),
                        ModPotions.LONG_RESISTANCE_POTION_PLUS_2.get()
                }
        ));

        BrewingRecipeRegistry.addRecipe(create(
                Potions.STRONG_TURTLE_MASTER,
                new Potion[]{
                        ModPotions.STRONG_RESISTANCE_POTION.get(),
                        ModPotions.STRONG_RESISTANCE_POTION_PLUS_1.get()
                }
        ));
    }

    private static IBrewingRecipe create(
            Potion basePotion,
            Potion[] resultPool
    ) {
        return new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == basePotion;
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == Items.AMETHYST_SHARD;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (!isInput(input) || !isIngredient(ingredient)) {
                    return ItemStack.EMPTY;
                }

                Potion randomPotion =
                        resultPool[RandomSource.create().nextInt(resultPool.length)];

                return PotionUtils.setPotion(
                        new ItemStack(Items.POTION),
                        randomPotion
                );
            }
        };
    }
}