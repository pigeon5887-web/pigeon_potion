package com.pigeon.potion.effect;

import com.pigeon.potion.PigeonPotionMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FriendlyEffect extends MobEffect {

    private static final double ATTRACT_RANGE = 10.0D;
    private static final double STOP_DISTANCE_SQR = 2.0D * 2.0D;
    private static final int CHECK_INTERVAL = 10;

    private static final ResourceLocation FOLLOW_ME_ADVANCEMENT =
            new ResourceLocation(PigeonPotionMod.MOD_ID, "follow_me");

    public FriendlyEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF69B4);
    }

    /**
     * 拥有亲近效果的实体不会被生物作为攻击目标。
     */
    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity target = event.getNewTarget();

        if (target == null) {
            return;
        }

        if (!target.hasEffect(ModEffects.FRIENDLY.get())) {
            return;
        }

        event.setCanceled(true);

        if (event.getEntity() instanceof Mob mob && mob.getTarget() == target) {
            mob.setTarget(null);
        }
    }

    /**
     * 拥有亲近效果的实体会吸引半径 10 格内的友好生物靠近。
     *
     * 如果吸引中心是玩家，并且至少有一只友好生物被吸引，
     * 就发放“来吧，跟我走”成就。
     */
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity attractor = event.getEntity();

        if (attractor.level().isClientSide()) {
            return;
        }

        if (!attractor.hasEffect(ModEffects.FRIENDLY.get())) {
            return;
        }

        if (!(attractor.level() instanceof ServerLevel level)) {
            return;
        }

        if (attractor.tickCount % CHECK_INTERVAL != 0) {
            return;
        }

        boolean attractedAnyMob = false;

        for (PathfinderMob mob : level.getEntitiesOfClass(
                PathfinderMob.class,
                attractor.getBoundingBox().inflate(ATTRACT_RANGE),
                mob -> mob != attractor && mob.isAlive()
        )) {
            if (!isFriendlyMob(mob)) {
                continue;
            }

            if (mob.distanceToSqr(attractor) <= STOP_DISTANCE_SQR) {
                mob.getNavigation().stop();
                attractedAnyMob = true;
                continue;
            }

            boolean moved = mob.getNavigation().moveTo(attractor, 1.2D);

            if (moved) {
                attractedAnyMob = true;
            }
        }

        if (attractedAnyMob) {
            awardFollowMeAdvancement(attractor);
        }
    }

    private static boolean isFriendlyMob(PathfinderMob mob) {
        return !(mob instanceof Enemy)
                && !(mob instanceof NeutralMob);
    }

    private static void awardFollowMeAdvancement(LivingEntity attractor) {
        if (!(attractor instanceof ServerPlayer player)) {
            return;
        }

        Advancement advancement =
                player.server.getAdvancements().getAdvancement(FOLLOW_ME_ADVANCEMENT);

        if (advancement == null) {
            return;
        }

        AdvancementProgress progress =
                player.getAdvancements().getOrStartProgress(advancement);

        if (progress.isDone()) {
            return;
        }

        player.getAdvancements().award(advancement, "followed");
    }
}
