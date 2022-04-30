package com.teamkuiper.redoxiation;

import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class RedoxiationItemGroups {

	public static final ItemGroup BLOCK_GROUP = new RedoxiationItemGroups.RDBlockGroup();
	public static final ItemGroup ITEM_GROUP = new RedoxiationItemGroups.RDItemGroup();

	public static class RDBlockGroup extends ItemGroup {
		public RDBlockGroup() {
			super("redoxiation_blocks");
		}

		@Override
		public ItemStack makeIcon() {
			return RedoxiationBlocks.BLOCKS_BI.get("bauxite").get().getDefaultInstance();
		}
	}

	public static class RDItemGroup extends ItemGroup {
		public RDItemGroup() {
			super("redoxiation_items");
		}

		@Override
		public ItemStack makeIcon() {
			return RedoxiationItems.ITEMS.get("raw_bauxite").get().getDefaultInstance();
		}
	}

}
