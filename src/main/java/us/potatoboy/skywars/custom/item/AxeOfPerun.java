package us.potatoboy.skywars.custom.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class AxeOfPerun extends AxeItem {
    public AxeOfPerun(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            ItemCooldownManager cooldown = ((PlayerEntity) attacker).getItemCooldownManager();
            if (!(cooldown.isCoolingDown(this))) {
                World world = target.getWorld();
                DamageSources source = world.getDamageSources();

                LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightningEntity.setCosmetic(true);
                lightningEntity.setPosition(target.getPos());

                world.spawnEntity(lightningEntity);
                target.damage(source.lightningBolt(), 5.0f);
                cooldown.set(this, 100);
            }
        }
        return false;
    }

}
