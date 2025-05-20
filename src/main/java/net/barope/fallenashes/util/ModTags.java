package net.barope.fallenashes.util;

import net.barope.fallenashes.FallenAshes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> METAL_BLOCKS = createTag("metal_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(FallenAshes.MOD_ID, name));
        }

    }
    public static class Items{
        public static final TagKey<Item> METAL_ITEMS = createTag("metal_items");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(FallenAshes.MOD_ID, name));
        }
    }
}
