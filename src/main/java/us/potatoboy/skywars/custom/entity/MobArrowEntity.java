package us.potatoboy.skywars.custom.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import us.potatoboy.skywars.SkyWars;
import us.potatoboy.skywars.custom.SWItems;

public class MobArrowEntity extends PersistentProjectileEntity {

    public MobArrowEntity(EntityType<? extends MobArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public MobArrowEntity(World world, LivingEntity owner) {
        super(SWItems.MOB_ARROW_ENTITY, owner, world);
    }

    @Override
    public ItemStack asItemStack() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    public void onBlockHit(BlockHitResult hitResult) {
        super.onBlockHit(hitResult);
        Vec3d targetPos = hitResult.getPos();
        spawnMob(targetPos);
    }
    @Override
    public void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);
        Vec3d targetPos = hitResult.getPos();
        spawnMob(targetPos);
    }

    private void spawnMob(Vec3d targetPos) {
        World world = this.getWorld();

        if (!world.isClient) {
            Entity mob;
            Entity shooter = this.getOwner();
            if (Math.random() < 0.5) {
                mob = new CustomZombie(EntityType.ZOMBIE, world);
            } else {
                mob = new CustomSkeleton(EntityType.SKELETON, world);
            }

            Scoreboard scoreboard = world.getScoreboard();
            Team team = scoreboard.addTeam(shooter.getEntityName());
            if (team.isFriendlyFireAllowed()) {
                team.setFriendlyFireAllowed(false);
            }

            if (!shooter.isTeamPlayer(team)) {
                scoreboard.addPlayerToTeam(shooter.getEntityName(), team);
            }

            mob.updatePosition(targetPos.x, targetPos.y, targetPos.z);
            scoreboard.addPlayerToTeam(mob.getEntityName(), team);
            world.spawnEntity(mob);
        }
    }

}