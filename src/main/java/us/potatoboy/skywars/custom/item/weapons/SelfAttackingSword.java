package us.potatoboy.skywars.custom.item.weapons;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class SelfAttackingSword extends SwordItem {

    public SelfAttackingSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

}
