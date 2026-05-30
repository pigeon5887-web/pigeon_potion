package com.pigeon.potion.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ThornsEffect extends MobEffect {

    public ThornsEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xC0C0C0);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        if (target.hasEffect(ModEffects.THORNS.get())) {
            int amplifier = target.getEffect(ModEffects.THORNS.get()).getAmplifier();
            float reflectPercent = 0.25f * (amplifier + 1); // 一级25%，二级50%
            float reflectDamage = event.getAmount() * reflectPercent;

            DamageSource source = event.getSource();
            if (source.getEntity() instanceof LivingEntity attacker) {
                attacker.hurt(source, reflectDamage);
            }
        }
    }
}