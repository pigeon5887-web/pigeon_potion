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
public class ArmorBoostEffect extends MobEffect {

    private static final UUID ARMOR_BOOST_UUID = UUID.fromString("c1d2e3f4-a5b6-4789-acde-f123456789ab");

    public ArmorBoostEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x3366CC);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        // 检查是否有构甲效果
        if (entity.hasEffect(ModEffects.ARMOR_BOOST.get())) {
            int amplifier = entity.getEffect(ModEffects.ARMOR_BOOST.get()).getAmplifier();
            double armorBonus = 4.0 * (amplifier + 1); // 一级 +4，二级 +8

            AttributeInstance armorAttr = entity.getAttribute(Attributes.ARMOR);
            if (armorAttr != null) {
                // 检查是否已经添加过修饰器
                if (armorAttr.getModifier(ARMOR_BOOST_UUID) == null) {
                    AttributeModifier modifier = new AttributeModifier(
                            ARMOR_BOOST_UUID,
                            "armor_boost_bonus",
                            armorBonus,
                            AttributeModifier.Operation.ADDITION
                    );
                    armorAttr.addTransientModifier(modifier);
                }
            }
        } else {
            // 移除修饰器
            AttributeInstance armorAttr = entity.getAttribute(Attributes.ARMOR);
            if (armorAttr != null && armorAttr.getModifier(ARMOR_BOOST_UUID) != null) {
                armorAttr.removeModifier(ARMOR_BOOST_UUID);
            }
        }
    }
}