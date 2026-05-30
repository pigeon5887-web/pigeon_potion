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
public class WarriorEffect extends MobEffect {

    private static final UUID DAMAGE_UUID = UUID.fromString("f1a2b3c4-d5e6-4789-abcd-123456789abc");
    private static final UUID SPEED_UUID = UUID.fromString("a2b3c4d5-e6f7-4789-bcde-123456789abd");

    public WarriorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xCC6600);
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();

        // 检查是否有战神效果
        if (entity.hasEffect(ModEffects.WARRIOR.get())) {
            int amplifier = entity.getEffect(ModEffects.WARRIOR.get()).getAmplifier();
            int level = amplifier + 1;

            // 增加跳跃速度：默认 0.42，一级加 0.21，二级加 0.42
            double extraJumpVelocity = 0.21 * level;
            entity.setDeltaMovement(
                    entity.getDeltaMovement().x,
                    entity.getDeltaMovement().y + extraJumpVelocity,
                    entity.getDeltaMovement().z
            );
        }
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModEffects.WARRIOR.get())) {
            int amplifier = entity.getEffect(ModEffects.WARRIOR.get()).getAmplifier();
            int level = amplifier + 1;

            // 攻击伤害加成
            double damageBonus = 3.0 * level;
            AttributeModifier damageMod = new AttributeModifier(DAMAGE_UUID, "warrior_damage", damageBonus, AttributeModifier.Operation.ADDITION);
            var damageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (damageAttr != null && damageAttr.getModifier(DAMAGE_UUID) == null) {
                damageAttr.addTransientModifier(damageMod);
            }

            // 移动速度加成
            double speedBonus = 0.2 * level;
            AttributeModifier speedMod = new AttributeModifier(SPEED_UUID, "warrior_speed", speedBonus, AttributeModifier.Operation.MULTIPLY_TOTAL);
            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null && speedAttr.getModifier(SPEED_UUID) == null) {
                speedAttr.addTransientModifier(speedMod);
            }
        } else {
            // 移除属性修饰器
            var damageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (damageAttr != null) damageAttr.removeModifier(DAMAGE_UUID);

            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.removeModifier(SPEED_UUID);
        }
    }
}