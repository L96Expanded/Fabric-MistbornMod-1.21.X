package net.barope.fallenashes.abilities;

import net.barope.fallenashes.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Iron_Burn {
    /**
     * Triggers a repulsion effect pushing the player away from every non-air block
     * within a 20-block radius.
     */
    public static void trigger(PlayerEntity player) {
        if (player == null) return;
        World world = player.getEntityWorld();
        Vec3d totalPush = Vec3d.ZERO;
        // Use player's eye height for a more consistent center
        BlockPos center = player.getBlockPos().add(0, (int)player.getStandingEyeHeight(), 0);
        int maxRange = 50;
        double maxStrength = .5;
        TagKey<Block> targetTag = ModTags.Blocks.METAL_BLOCKS;

        BlockPos start = center.add(-maxRange, -maxRange, -maxRange);
        BlockPos end = center.add( maxRange,  maxRange,  maxRange);
        long count = BlockPos.stream(start, end).count();
        System.out.println("[MyMod] block count = " + count);


        // Iterate through a cubic volume around the player
        for (BlockPos bp : BlockPos.iterate(start, end)) {
            // Skip air block
            if (!world.getBlockState(bp).isIn(targetTag)) continue;
            // Compute direction vector from block to player
            Vec3d blockCenter = Vec3d.ofCenter(bp);
            Vec3d dir = player.getPos().subtract(blockCenter);
            double dist = dir.length();

            // Only consider block within range and avoid zero-distance
            if (dist <= 0.01 || dist > maxRange) continue;

            // Linear falloff based on distance
            double strength = maxStrength * (1.0 - (dist / maxRange));
            Vec3d push = dir.normalize().multiply(-strength);

            // Accumulate
            totalPush = totalPush.add(push);
        }
        // Apply the total push to the player
        player.addVelocity(totalPush.x, totalPush.y, totalPush.z);
        player.velocityModified = true;
    }
}