package us.potatoboy.skywars.custom;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static us.potatoboy.skywars.custom.SWItems.*;


public class ModItemGroups {

    private static final ItemGroup LUCKY_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(RED_LUCKY_BLOCK))
            .displayName(Text.translatable("itemGroup.skywars.lucky_group"))
            .entries(((displayContext, entries) -> {
                entries.add(RED_LUCKY_BLOCK);
                entries.add(ANDURIL);
                entries.add(AXE_OF_PERUN);
                entries.add(SWORD_OF_JUSTICE);
                entries.add(CARROT_CORRUPTER);
            }))
            .build();
    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, new Identifier("skywars", "lucky_group"), LUCKY_GROUP);
    }
}
