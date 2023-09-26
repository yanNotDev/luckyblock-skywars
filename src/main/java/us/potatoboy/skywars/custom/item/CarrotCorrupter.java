package us.potatoboy.skywars.custom.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarrotCorrupter extends Item {

//  private final List<Integer> slotsWithItems = new ArrayList<>();

    public CarrotCorrupter(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof PlayerEntity player) {
            PlayerInventory inventory = player.getInventory();
//            slotsWithItems.clear();
            final List<Integer> slotsWithItems = new ArrayList<>();

            for (int i = 0; i < 9; i++ ) {
                ItemStack currentSlot = inventory.getStack(i);
                if (!(currentSlot.isEmpty()) && currentSlot.getItem() != Items.CARROT) {
                    slotsWithItems.add(i);
                }
            }
            int random = getRandom(slotsWithItems);

            int randomNum = (int) (Math.random() * 4 + 1);
            // 3/4 chance to corrupt
            if (!(randomNum == 1)) {
            inventory.setStack(random, new ItemStack(Items.CARROT));
            }
        }

        return false;
    }


    private int getRandom(List<Integer> list) {
        int rnd = new Random().nextInt(list.size());
        return list.get(rnd);
    }
}
