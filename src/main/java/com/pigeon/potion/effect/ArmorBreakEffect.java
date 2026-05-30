package com.pigeon.potion.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ArmorBreakEffect extends MobEffect {

    private static final UUID UUID_ARMOR_BREAK = UUID.fromString("22345678-2234-2234-2234-223456789abc");

    public ArmorBreakEffect() {
        super(MobEffectCategory.HARMFUL, 0xCC3333); // 红色
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
        if (armor == null) return;

        if (entity.hasEffect(ModEffects.ARMOR_BREAK.get())) {
            int level = entity.getEffect(ModEffects.ARMOR_BREAK.get()).getAmplifier() + 1;
            double penalty = -4.0 * level; // 一级 -4，二级 -8

            if (armor.getModifier(UUID_ARMOR_BREAK) == null) {
                armor.addPermanentModifier(new AttributeModifier(UUID_ARMOR_BREAK, "armor_break", penalty, AttributeModifier.Operation.ADDITION));
            }
        } else {
            armor.removeModifier(UUID_ARMOR_BREAK);
        }
    }
}