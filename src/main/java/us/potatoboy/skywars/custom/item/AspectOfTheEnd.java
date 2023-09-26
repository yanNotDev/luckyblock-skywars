package us.potatoboy.skywars.custom.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.util.math.Direction.DOWN;

public class AspectOfTheEnd extends SwordItem {

    protected final Random random = Random.create();
    public String TELEPORTS_REMAINING_TAG = "teleports_remaining";
    public String FIRST_RUN_TAG = "first_run";


    public AspectOfTheEnd(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        super.getDefaultStack().getOrCreateNbt().putInt(TELEPORTS_REMAINING_TAG, 10);
    }

    private void handleTeleport(LivingEntity user) {
        // Get the player's eye position and look vector
        Vec3d eyePos = user.getEyePos();
        Vec3d lookVec = user.getRotationVector();

        // Calculate the end position of the ray trace
        long teleportDistance = 8;
        Vec3d teleportPos = eyePos.add(lookVec.multiply(teleportDistance)).add(0, -1 * user.getStandingEyeHeight(), 0);

        // Get the world and perform the ray trace
        World world = user.getWorld();
        BlockHitResult result = world.raycast(new RaycastContext(eyePos, teleportPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, user));

        Vec3d finalTeleportPos = teleportPos;

        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = result.getBlockPos();
            Direction face = result.getSide();
            BlockPos adjustedPos = hitPos.offset(face).offset(DOWN);
            finalTeleportPos = new Vec3d(adjustedPos.getX() + 0.5, adjustedPos.getY() + 1.0, adjustedPos.getZ() + 0.5);
        }
        finalTeleportPos = adjustTeleportPosition(finalTeleportPos, user, world);
        // Teleport the player
        user.teleport(finalTeleportPos.x, finalTeleportPos.y, finalTeleportPos.z);
        // Check if the player is in a 1-block tall space and make them crawl
        if (isPlayerInOneBlockSpace(user.getLeashPos(1.0f), world)) {
            user.teleport(user.getX(), user.getY() + 1, user.getZ());
            user.setPose(EntityPose.SWIMMING);
        }
    }

    private boolean isPlayerInOneBlockSpace(Vec3d teleportPos, World world) {
        int x = (int) teleportPos.getX();
        int y = (int) teleportPos.getY();
        int z = (int) teleportPos.getZ();
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockPos abovePos = blockPos.up().up();
        BlockPos belowPos = blockPos.down();

        boolean aboveSolid = world.getBlockState(abovePos).isOpaque();
        boolean belowSolid = world.getBlockState(belowPos).isOpaque();

        return aboveSolid && belowSolid;
    }

    public Vec3d adjustTeleportPosition(Vec3d teleportPos, LivingEntity user, World world) {
        for (int i = 1; i <= 2; i++) {
            teleportPos = raiseFeet(teleportPos, world);
        }
        for (int i = 1; i <= 2; i++) {
            teleportPos = lowerHead(teleportPos, user, world);
        }
        return teleportPos;
    }

    public Vec3d lowerHead(Vec3d teleportPos, LivingEntity user, World world) {
        BlockPos headPos = new BlockPos((int) teleportPos.getX(), (int) teleportPos.getY(), (int) teleportPos.getZ());
        if (world.getBlockState(headPos).isOpaque()) {
            double headOverlap = teleportPos.y + user.getHeight() - headPos.getY();
            teleportPos = teleportPos.add(0, -headOverlap, 0);
        }
        return teleportPos;
    }

    public Vec3d raiseFeet(Vec3d teleportPos, World world) {
        BlockPos feetPos = new BlockPos((int) teleportPos.getX(), (int) teleportPos.getY(), (int) teleportPos.getZ());
        if (world.getBlockState(feetPos).isOpaque()) {
            // If the feet are inside a block, adjust the teleport position up by the difference between the block top and the player's feet
            double blockY = feetPos.getY() + world.getBlockState(feetPos).getOutlineShape((BlockView) world, feetPos).getMax(Direction.Axis.Y);
            double feetOverlap = blockY - teleportPos.getY();
            teleportPos = teleportPos.add(0, feetOverlap, 0);
        }
        return teleportPos;
    }

    public void soundAndParticles(World world, LivingEntity user) {
        int x = (int) user.getX();
        int y = (int) user.getY();
        int z = (int) user.getZ();
        BlockPos destPos = new BlockPos(x, y, z);
        world.playSound(null, destPos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0F);
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.PORTAL, x, y, z, 25, 0.5, 0.5, 0.5, 0.0);
        }
    }

    public int getTeleportsRemaining(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(TELEPORTS_REMAINING_TAG);
    }
    public boolean isFirstRun(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean(FIRST_RUN_TAG);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!player.getWorld().isClient) {

            if (!stack.getNbt().contains(FIRST_RUN_TAG)) {
                stack.getOrCreateNbt().putBoolean(FIRST_RUN_TAG, true);
            }

            int maxTeleports = 10;

            if ((getTeleportsRemaining(stack) != maxTeleports) && isFirstRun(stack)) {
                stack.getOrCreateNbt().putInt(TELEPORTS_REMAINING_TAG, maxTeleports);
                stack.getOrCreateNbt().putBoolean(FIRST_RUN_TAG, false);
            }

            if (getTeleportsRemaining(stack) == 0) {
                return TypedActionResult.fail(stack);
            }

            // Teleport player
            handleTeleport(player);
            // Remove fall damage
            player.fallDistance = 0;

            // Play sound and spawn particles
            soundAndParticles(world, player);

            stack.getOrCreateNbt().putInt(TELEPORTS_REMAINING_TAG, stack.getOrCreateNbt().getInt(TELEPORTS_REMAINING_TAG) -  1);
            }
                return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (getTeleportsRemaining(stack) != 0 && !isFirstRun(stack)) {
            tooltip.add(Text.translatable("tooltip.skywars.aspect_of_the_end", getTeleportsRemaining(stack)));
        }
        super.appendTooltip(stack, world, tooltip, context);
        }
}