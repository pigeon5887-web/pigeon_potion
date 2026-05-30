package com.pigeon.potion.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DivineEffect extends MobEffect {

    public DivineEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFD700); // 金色
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModEffects.DIVINE.get())) {
            float damage = event.getAmount();
            float newHealth = entity.getHealth() - damage;

            // 如果这次伤害会致死
            if (newHealth <= 0) {
                // 取消死亡伤害
                event.setCanceled(true);

                // 恢复 V（5秒，每秒恢复 2.5 颗心，总共约 12.5 颗心）
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 4, false, false));

                // 抗性提升 II（30秒）
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1, false, false));

                // 伤害吸收 III（30秒）
                entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 2, false, false));

                // 播放不死图腾音效
                entity.level().playSound(
                        null,
                        entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.TOTEM_USE,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );

                // 播放末地传送门音效（神圣感）
                entity.level().playSound(
                        null,
                        entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.END_PORTAL_SPAWN,
                        SoundSource.PLAYERS,
                        0.5F,
                        1.5F
                );

                // 生成不死图腾粒子效果
                if (entity.level() instanceof ServerLevel serverLevel) {
                    // 不死图腾的绿色粒子环
                    serverLevel.sendParticles(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            entity.getX(),
                            entity.getY() + entity.getBbHeight() / 2,
                            entity.getZ(),
                            50,         // 粒子数量
                            0.5,        // X偏移
                            1.0,        // Y偏移
                            0.5,        // Z偏移
                            0.5         // 速度
                    );

                    // 金色粒子增强神圣感
                    serverLevel.sendParticles(
                            ParticleTypes.ENCHANTED_HIT,
                            entity.getX(),
                            entity.getY() + entity.getBbHeight() / 2,
                            entity.getZ(),
                            30,
                            0.5,
                            1.0,
                            0.5,
                            0.3
                    );
                }

                // 发送提示消息
                if (entity instanceof Player player) {
                    player.displayClientMessage(Component.translatable("message.pigeon_potion.divine_protection"), true);
                }
            }
        }
    }
}