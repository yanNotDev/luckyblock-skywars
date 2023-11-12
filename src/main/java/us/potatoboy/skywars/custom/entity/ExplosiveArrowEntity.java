package us.potatoboy.skywars.custom.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import us.potatoboy.skywars.custom.SWItems;

public class ExplosiveArrowEntity extends PersistentProjectileEntity {

    public ExplosiveArrowEntity(EntityType<? extends ExplosiveArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public ExplosiveArrowEntity(World world, LivingEntity owner) {
        super(SWItems.EXPLOSIVE_ARROW_ENTITY, owner, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient && !this.inGround) {
            this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + .5f, this.getZ(), 0.0, 0.0, 0.0);
        }

    }

    @Override
    public ItemStack asItemStack() {
        return new ItemStack(Items.ARROW);
    }


    @Override
    public void onBlockHit(BlockHitResult hitResult) {
        super.onBlockHit(hitResult);
        Vec3d targetPos = hitResult.getPos();
        explode(targetPos);
    }
    @Override
    public void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);
        Vec3d targetPos = hitResult.getPos();
        explode(targetPos);
    }

    private void explode(Vec3d targetPos) {
        if (this.isSubmergedInWater()) return;
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, targetPos.x, targetPos.y, targetPos.z, 2f, World.ExplosionSourceType.NONE);
        }
        this.remove(RemovalReason.DISCARDED);
    }

}