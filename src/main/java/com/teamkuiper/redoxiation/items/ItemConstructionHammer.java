package com.teamkuiper.redoxiation.items;

import com.teamkuiper.redoxiation.blocks.IHammerable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class ItemConstructionHammer extends Item {
	
	public static final String NAME = "construction_hammer";

	public ItemConstructionHammer() {
		super(RedoxiationItems.ITEM_BASIC_PROPERTISE);
	}

	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockState state = world.getBlockState(context.getPos());
		Block block = state.getBlock();
		if(block instanceof IHammerable) {
			((IHammerable) block).onHammered(state, world, context.getPos(), context.getPlayer(), context.getHand());
		}
		return ActionResultType.PASS;
	}
}
