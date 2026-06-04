package com.pigeon.potion.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber
public class RepelEffect extends MobEffect {

    private static final double RANGE = 16.0D;
    private static final int CHECK_INTERVAL = 10;

    public RepelEffect() {
        super(MobEffectCategory.HARMFUL, 0x8B0000);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        if (!(entity.level() instanceof ServerLevel level)) {
            return;
        }

        if (!(entity instanceof Mob mob)) {
            return;
        }

        if (mob.tickCount % CHECK_INTERVAL != 0) {
            return;
        }

        LivingEntity repelTarget = findNearestRepelTarget(level, mob);

        if (repelTarget == null) {
            return;
        }

        if (isAggressiveMob(mob)) {
            if (mob.canAttack(repelTarget) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(repelTarget)) {
                mob.setTarget(repelTarget);
            }
            return;
        }

        if (isFriendlyMob(mob) && mob instanceof PathfinderMob pathfinderMob) {
            runAwayFrom(pathfinderMob, repelTarget);
        }
    }

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity newTarget = event.getNewTarget();

        if (newTarget == null) {
            return;
        }

        if (!newTarget.hasEffect(ModEffects.REPEL.get())) {
            return;
        }

        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }

        if (isFriendlyMob(mob)) {
            event.setCanceled(true);
            mob.setTarget(null);
        }
    }

    private static LivingEntity findNearestRepelTarget(ServerLevel level, Mob mob) {
        AABB box = mob.getBoundingBox().inflate(RANGE);

        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                box,
                target -> target != mob
                        && target.isAlive()
                        && target.hasEffect(ModEffects.REPEL.get())
                        && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)
        );

        return targets.stream()
                .min(Comparator.comparingDouble(mob::distanceToSqr))
                .orElse(null);
    }

    private static boolean isAggressiveMob(Mob mob) {
        return mob instanceof Enemy || mob instanceof NeutralMob;
    }

    private static boolean isFriendlyMob(Mob mob) {
        if (mob instanceof Enemy || mob instanceof NeutralMob) {
            return false;
        }

        if (mob instanceof TamableAnimal tamableAnimal && tamableAnimal.isTame()) {
            return true;
        }

        return mob instanceof Animal;
    }

    private static void runAwayFrom(PathfinderMob mob, LivingEntity avoidTarget) {
        Vec3 direction = mob.position().subtract(avoidTarget.position());

        if (direction.lengthSqr() < 0.0001D) {
            direction = new Vec3(
                    mob.getRandom().nextDouble() - 0.5D,
                    0.0D,
                    mob.getRandom().nextDouble() - 0.5D
            );
        }

        direction = direction.normalize();

        for (int i = 0; i < 8; i++) {
            double distance = 8.0D + mob.getRandom().nextDouble() * 8.0D;
            double sideOffset = (mob.getRandom().nextDouble() - 0.5D) * 6.0D;

            Vec3 side = new Vec3(-direction.z, 0.0D, direction.x);
            Vec3 targetPos = mob.position()
                    .add(direction.scale(distance))
                    .add(side.scale(sideOffset));

            BlockPos pos = BlockPos.containing(targetPos.x, mob.getY(), targetPos.z);
            Path path = mob.getNavigation().createPath(pos, 0);

            if (path != null) {
                mob.getNavigation().moveTo(path, 1.25D);
                return;
            }
        }
    }
}
