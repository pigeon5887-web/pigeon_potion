package com.pigeon.potion.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DodgeEffect extends MobEffect {

    public DodgeEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x1A7A6F); // 墨绿色
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModEffects.DODGE.get())) {
            int amplifier = entity.getEffect(ModEffects.DODGE.get()).getAmplifier();
            float dodgeChance = 0.2f * (amplifier + 1); // 一级10%，二级20%

            if (entity.getRandom().nextFloat() < dodgeChance) {
                event.setCanceled(true);

                // 播放末影人传送音效
                if (!entity.level().isClientSide()) {
                    entity.level().playSound(
                            null,
                            entity.getX(), entity.getY(), entity.getZ(),
                            SoundEvents.ENDERMAN_TELEPORT,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }

                // 生成末影粒子效果
                if (entity.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            ParticleTypes.PORTAL,           // 末影传送门粒子
                            entity.getX(),
                            entity.getY() + entity.getBbHeight() / 2, // 身体中部
                            entity.getZ(),
                            20,                              // 粒子数量
                            0.3,                             // X偏移
                            0.5,                             // Y偏移
                            0.3,                             // Z偏移
                            0.1                              // 速度
                    );
                }

                // 提示消息
                if (entity instanceof Player player) {
                    player.displayClientMessage(Component.translatable("message.pigeon_potion.dodge"), true);
                }
            }
        }
    }
}