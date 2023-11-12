package us.potatoboy.skywars.custom;

import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import us.potatoboy.skywars.SkyWars;
import us.potatoboy.skywars.custom.block.LaunchPadBlock;
import us.potatoboy.skywars.custom.block.LaunchPadBlockEntity;
import us.potatoboy.skywars.custom.block.LuckyBlock;
import us.potatoboy.skywars.custom.entity.ExplosiveArrowEntity;
import us.potatoboy.skywars.custom.entity.MobArrowEntity;
import us.potatoboy.skywars.custom.item.armor.Exodus;
import us.potatoboy.skywars.custom.item.armor.ModArmorMaterials;
import us.potatoboy.skywars.custom.item.armor.UpgradeBook;
import us.potatoboy.skywars.custom.item.weapons.*;


public class SWItems {
    public static final Block GOLD_LAUNCH_PAD = new LaunchPadBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).strength(100).noCollision(), Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
    public static final Block IRON_LAUNCH_PAD = new LaunchPadBlock(AbstractBlock.Settings.copy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).strength(100).noCollision(), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
    public static final BlockEntityType<LaunchPadBlockEntity> LAUNCH_PAD_ENTITY = FabricBlockEntityTypeBuilder.create(LaunchPadBlockEntity::new, GOLD_LAUNCH_PAD, IRON_LAUNCH_PAD).build(null);

    public static final Block RED_LUCKY_BLOCK = new LuckyBlock(FabricBlockSettings.copyOf(Blocks.RED_STAINED_GLASS).resistance(1200.0f));
    public static final Item ANDURIL = new Anduril();
    public static final Item AXE_OF_PERUN = new AxeOfPerun(ToolMaterials.DIAMOND, 2, -3.0f, new FabricItemSettings());
    public static final Item SWORD_OF_JUSTICE = new SwordOfJustice(ToolMaterials.IRON, 3, -2.4f, new FabricItemSettings());
    public static final Item CARROT_CORRUPTER = new CarrotCorrupter(new FabricItemSettings().maxCount(1));
    public static final Item ASPECT_OF_THE_END = new AspectOfTheEnd(ToolMaterials.DIAMOND, 3, -2.4f, new FabricItemSettings());
    public static final Item SELF_ATTACKING_SWORD = new SelfAttackingSword(ToolMaterials.IRON, 3, -2.4f, new FabricItemSettings());
    public static final Item EXPLOSIVE_BOW = new ExplosiveBow(new FabricItemSettings().maxDamage(10));
    public static final Item MOB_BOW = new MobBow(new FabricItemSettings().maxDamage(20));
    public static final Item UPGRADE_BOOK = new UpgradeBook(new FabricItemSettings());
    public static final Item EXODUS = new Exodus(ModArmorMaterials.EXODUS, ArmorItem.Type.HELMET, new FabricItemSettings());
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
        registerItem("aspect_of_the_end", ASPECT_OF_THE_END);
        registerItem("self_attacking_sword", SELF_ATTACKING_SWORD);
        registerItem("explosive_bow", EXPLOSIVE_BOW);
        registerItem("mob_bow", MOB_BOW);

        registerItem("upgrade_book", UPGRADE_BOOK);
        registerItem("exodus", EXODUS);
    }

    public static final EntityType<ExplosiveArrowEntity> EXPLOSIVE_ARROW_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            SkyWars.identifier("explosive_arrow"),
            FabricEntityTypeBuilder.<ExplosiveArrowEntity>create(SpawnGroup.MISC, ExplosiveArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());

    public static final EntityType<MobArrowEntity> MOB_ARROW_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            SkyWars.identifier("mob_arrow"),
            FabricEntityTypeBuilder.<MobArrowEntity>create(SpawnGroup.MISC, MobArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());

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
