package us.potatoboy.skywars.custom.item.armor;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Map;

public class UpgradeBook extends Item {
    public UpgradeBook(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getArmorItems().forEach(itemStack -> {
            int protection_level = EnchantmentHelper.getLevel(Enchantments.PROTECTION, itemStack);

            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);
            enchantments.remove(Enchantments.PROTECTION);
            EnchantmentHelper.set(enchantments, itemStack);

            itemStack.addEnchantment(Enchantments.PROTECTION, protection_level + 2);

        });
        user.playSound(SoundEvents.BLOCK_ANVIL_USE, 1, 1);
        user.getStackInHand(hand).decrement(1);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
