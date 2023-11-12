package us.potatoboy.skywars.custom.item.weapons;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class SwordOfJustice extends SwordItem {
    public SwordOfJustice(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            ItemCooldownManager cooldown = ((PlayerEntity) attacker).getItemCooldownManager();
            if (!(cooldown.isCoolingDown(this))) {
                World world = target.getWorld();

                LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightningEntity.setCosmetic(true);
                lightningEntity.setPosition(target.getPos());

                world.spawnEntity(lightningEntity);
                attacker.heal(2.0f);
                cooldown.set(this, 100);
            }
        }
        return false;
    }

}
