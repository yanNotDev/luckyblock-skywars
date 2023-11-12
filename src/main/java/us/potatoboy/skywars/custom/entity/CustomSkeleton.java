package us.potatoboy.skywars.custom.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class CustomSkeleton extends SkeletonEntity {
    public CustomSkeleton(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected boolean isAffectedByDaylight() {
        return false;
    }
}

