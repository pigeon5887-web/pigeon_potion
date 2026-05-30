package com.pigeon.potion.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ExcellentEffect extends MobEffect {

    private static final UUID DAMAGE_UUID = UUID.fromString("d1e2f3a4-b5c6-4789-acde-f123456789ab");
    private static final UUID SPEED_UUID = UUID.fromString("e2f3a4b5-c6d7-4789-bcde-f123456789ac");

    public ExcellentEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFD700); // 金色
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModEffects.EXCELLENT.get())) {
            int amplifier = entity.getEffect(ModEffects.EXCELLENT.get()).getAmplifier();
            int level = amplifier + 1;

            // 增加攻击伤害：一级 +3，二级 +6
            double damageBonus = 3.0 * level;
            AttributeModifier damageMod = new AttributeModifier(DAMAGE_UUID, "excellent_damage", damageBonus, AttributeModifier.Operation.ADDITION);
            if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                var damageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
                if (damageAttr != null && damageAttr.getModifier(DAMAGE_UUID) == null) {
                    damageAttr.addTransientModifier(damageMod);
                }
            }

            // 增加移动速度：一级 +20%，二级 +40%
            double speedBonus = 0.2 * level;
            AttributeModifier speedMod = new AttributeModifier(SPEED_UUID, "excellent_speed", speedBonus, AttributeModifier.Operation.MULTIPLY_TOTAL);
            if (entity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speedAttr != null && speedAttr.getModifier(SPEED_UUID) == null) {
                    speedAttr.addTransientModifier(speedMod);
                }
            }
        } else {
            // 移除属性修饰器
            if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                var damageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
                if (damageAttr != null) damageAttr.removeModifier(DAMAGE_UUID);
            }
            if (entity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speedAttr != null) speedAttr.removeModifier(SPEED_UUID);
            }
        }
    }
}