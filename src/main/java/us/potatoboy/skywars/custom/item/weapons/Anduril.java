package us.potatoboy.skywars.custom.item.weapons;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Anduril extends SwordItem {
    public Anduril() {
        super(ToolMaterials.IRON, 3, -2.4f, new FabricItemSettings());
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player && selected) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 5, 0, true, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 5, 0, true, false, true));
        }
    }

}
