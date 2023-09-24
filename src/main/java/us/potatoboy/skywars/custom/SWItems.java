package us.potatoboy.skywars.custom;

import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import us.potatoboy.skywars.SkyWars;
import us.potatoboy.skywars.custom.block.LaunchPadBlock;
import us.potatoboy.skywars.custom.block.LaunchPadBlockEntity;
import us.potatoboy.skywars.custom.block.LuckyBlock;
import us.potatoboy.skywars.custom.item.Anduril;
import us.potatoboy.skywars.custom.item.AxeOfPerun;
import us.potatoboy.skywars.custom.item.CarrotCorrupter;
import us.potatoboy.skywars.custom.item.SwordOfJustice;

public class SWItems {
    public static final Block GOLD_LAUNCH_PAD = new LaunchPadBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).strength(100).noCollision(), Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
    public static final Block IRON_LAUNCH_PAD = new LaunchPadBlock(AbstractBlock.Settings.copy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).strength(100).noCollision(), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
    public static final BlockEntityType<LaunchPadBlockEntity> LAUNCH_PAD_ENTITY = FabricBlockEntityTypeBuilder.create(LaunchPadBlockEntity::new, GOLD_LAUNCH_PAD, IRON_LAUNCH_PAD).build(null);

    public static final Block RED_LUCKY_BLOCK = new LuckyBlock(FabricBlockSettings.copyOf(Blocks.RED_STAINED_GLASS).resistance(1200.0f));
    public static final Item ANDURIL = new Anduril(ToolMaterials.IRON, 3, -2.4f, new FabricItemSettings());
    public static final Item AXE_OF_PERUN = new AxeOfPerun(ToolMaterials.DIAMOND, 2, -3.0f, new FabricItemSettings());
    public static final Item SWORD_OF_JUSTICE = new SwordOfJustice(ToolMaterials.IRON, 3, -2.4f, new FabricItemSettings());
    public static final Item CARROT_CORRUPTER = new CarrotCorrupter(new FabricItemSettings().maxCount(1));
    public static void register() {
        register("gold_launch_pad", GOLD_LAUNCH_PAD);
        register("iron_launch_pad", IRON_LAUNCH_PAD);
        registerBlockEntity("launch_pad", LAUNCH_PAD_ENTITY);

        register("red_lucky_block", RED_LUCKY_BLOCK);
        registerBlockItem("red_lucky_block", RED_LUCKY_BLOCK);

        registerItem("anduril", ANDURIL);
        registerItem("axe_of_perun", AXE_OF_PERUN);
        registerItem("sword_of_justice", SWORD_OF_JUSTICE);
        registerItem("carrot_corrupter", CARROT_CORRUPTER);
    }

    private static <T extends Block> T register(String id, T block) {
        return Registry.register(Registries.BLOCK, SkyWars.identifier(id), block);
    }

    private static <T extends Block> BlockItem registerBlockItem(String id, T block) {
        return Registry.register(Registries.ITEM, SkyWars.identifier(id), new BlockItem(block, new FabricItemSettings()));
    }

    private static <T extends Item> Item registerItem(String id, T item) {
        return Registry.register(Registries.ITEM, SkyWars.identifier(id), item);
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, BlockEntityType<T> type) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, SkyWars.identifier(id), type);
        PolymerBlockUtils.registerBlockEntity(LAUNCH_PAD_ENTITY);
        return type;
    }
}
