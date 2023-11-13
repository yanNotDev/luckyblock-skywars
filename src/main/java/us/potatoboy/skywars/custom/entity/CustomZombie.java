package us.potatoboy.skywars.custom.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class CustomZombie extends ZombieEntity {

    public CustomZombie(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected boolean burnsInDaylight() {
        return false;
    }

}