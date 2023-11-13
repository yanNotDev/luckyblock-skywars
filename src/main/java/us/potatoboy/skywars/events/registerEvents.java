package us.potatoboy.skywars.events;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
    import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import us.potatoboy.skywars.custom.SWItems;
import us.potatoboy.skywars.custom.entity.CustomSkeleton;
import us.potatoboy.skywars.custom.entity.CustomZombie;
import us.potatoboy.skywars.custom.item.weapons.SelfAttackingSword;

public class registerEvents {

    public static void registerEvent() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

            if ((entity instanceof CustomZombie || entity instanceof CustomSkeleton) && entity.isTeammate(player))  {
                return ActionResult.FAIL;
            }

            if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(SWItems.EXODUS)) {
                if (entity instanceof PlayerEntity) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1, true, false, true));
                }
            }

            if (player.getStackInHand(hand).getItem() instanceof SelfAttackingSword) {
                player.damage(entity.getDamageSources().playerAttack(player), 6.0F);
                return ActionResult.SUCCESS;
            }
                return ActionResult.PASS;

        }

        );

    }
}