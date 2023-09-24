package us.potatoboy.skywars.events;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import us.potatoboy.skywars.custom.item.AxeOfPerun;

public class registerEvents {

    public static void registerEvent() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.getStackInHand(hand).getItem() instanceof AxeOfPerun) {
                if (entity instanceof ServerPlayerEntity player1) {
                    player1.heal(-10.0f);
                }
            }

            return ActionResult.SUCCESS;
        });
    }
}
