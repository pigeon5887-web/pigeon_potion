package com.pigeon.potion.effect;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber
public class BerserkEffect extends MobEffect {

    private static final UUID DAMAGE_UUID = UUID.fromString("a1b2c3d4-e5f6-4789-abcd-123456789abc");
    private static final UUID SPEED_UUID = UUID.fromString("b2c3d4e5-f6a7-4789-bcde-123456789abd");

    // 存储玩家在狂暴期间受到的伤害总和
    private static final ConcurrentHashMap<UUID, Float> damageStorage = new ConcurrentHashMap<>();
    // 存储玩家的狂暴等级
    private static final ConcurrentHashMap<UUID, Integer> levelStorage = new ConcurrentHashMap<>();
    // 标记正在结算的玩家，避免免疫逻辑拦截结算伤害
    private static final ConcurrentHashMap<UUID, Boolean> settlingStorage = new ConcurrentHashMap<>();

    public BerserkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF3300);
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(ModEffects.BERSERK.get())) {
            int amplifier = entity.getEffect(ModEffects.BERSERK.get()).getAmplifier();
            int level = amplifier + 1;
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

        if (entity.hasEffect(ModEffects.BERSERK.get())) {
            int amplifier = entity.getEffect(ModEffects.BERSERK.get()).getAmplifier();
            int level = amplifier + 1;

            // 存储等级（用于伤害结算）
            levelStorage.put(entity.getUUID(), amplifier);

            // 攻击伤害加成
            double damageBonus = 3.0 * level;
            AttributeModifier damageMod = new AttributeModifier(DAMAGE_UUID, "berserk_damage", damageBonus, AttributeModifier.Operation.ADDITION);
            var damageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (damageAttr != null && damageAttr.getModifier(DAMAGE_UUID) == null) {
                damageAttr.addTransientModifier(damageMod);
            }

            // 移动速度加成
            double speedBonus = 0.2 * level;
            AttributeModifier speedMod = new AttributeModifier(SPEED_UUID, "berserk_speed", speedBonus, AttributeModifier.Operation.MULTIPLY_TOTAL);
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

    // ==================== 伤害免疫和记录 ====================
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        UUID id = entity.getUUID();

        // 如果是结算伤害，不取消
        if (settlingStorage.containsKey(id)) {
            return;
        }

        if (entity.hasEffect(ModEffects.BERSERK.get())) {
            // 记录伤害
            float currentDamage = damageStorage.getOrDefault(id, 0f);
            damageStorage.put(id, currentDamage + event.getAmount());

            // 免疫伤害
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        var effect = event.getEffectInstance();
        if (effect != null && effect.getEffect() == ModEffects.BERSERK.get()) {
            LivingEntity entity = event.getEntity();
            UUID id = entity.getUUID();

            Float totalDamage = damageStorage.remove(id);
            Integer amplifier = levelStorage.remove(id);

            if (totalDamage != null && totalDamage > 0) {
                int level = (amplifier != null ? amplifier : 0) + 1;
                float percent = (level == 1) ? 0.5f : 0.25f;
                float finalDamage = totalDamage * percent;

                // 格式化伤害值（保留1位小数）
                String totalDamageStr = String.format("%.1f", totalDamage);
                String finalDamageStr = String.format("%.1f", finalDamage);

                if (finalDamage > 0) {
                    // 保底机制：不致死
                    float currentHealth = entity.getHealth();
                    float newHealth = currentHealth - finalDamage;

                    if (newHealth <= 0) {
                        finalDamage = currentHealth - 1;
                        finalDamageStr = String.format("%.1f", finalDamage);
                    }

                    if (finalDamage > 0) {
                        settlingStorage.put(id, true);
                        var damageSource = createMagicDamage(entity);
                        entity.hurt(damageSource, finalDamage);
                        settlingStorage.remove(id);

                        if (entity instanceof Player player) {
                            player.displayClientMessage(
                                    Component.translatable("message.pigeon_potion.berserk_end_damage", totalDamage, finalDamage),
                                    true
                            );
                        }
                    } else if (entity instanceof Player player) {
                        player.displayClientMessage(
                                Component.translatable("message.pigeon_potion.berserk_end_survive", totalDamage),
                                true
                        );
                    }
                } else if (entity instanceof Player player) {
                    player.displayClientMessage(
                            Component.translatable("message.pigeon_potion.berserk_end_zero", totalDamage),
                            true
                    );
                }
            } else if (entity instanceof Player player) {
                player.displayClientMessage(
                        Component.translatable("message.pigeon_potion.berserk_end_no_damage"),
                        true
                );
            }
        }
    }

    private static DamageSource createMagicDamage(LivingEntity entity) {
        var level = entity.level();
        var damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        var magicDamage = damageTypes.getHolderOrThrow(DamageTypes.MAGIC);
        return new DamageSource(magicDamage);
    }
}