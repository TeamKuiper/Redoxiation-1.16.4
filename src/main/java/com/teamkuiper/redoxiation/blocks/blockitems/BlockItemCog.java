package com.teamkuiper.redoxiation.blocks.blockitems;

import com.teamkuiper.redoxiation.blocks.BlockCog;
import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;
import com.teamkuiper.redoxiation.blocks.tileentities.TileCog;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemCog extends BlockItem {

	String name;

	public BlockItemCog(String name) {
		super(RedoxiationBlocks.BLOCKS.get(name).get(), RedoxiationItems.BLOCKITEM_BASIC_PROPERTISE);
		this.name = name;
	}
	
	@Override
	protected BlockState getStateForPlacement(BlockItemUseContext context) {
		return RedoxiationBlocks.BLOCKS.get(name).get().getDefaultState().with(BlockCog.FIRST_SIDE, context.getFace().getOpposite());
	}
	

	@Override
	public ActionResultType tryPlace(BlockItemUseContext context) {
		World world = context.getWorld();
        BlockPos blockpos = context.getPos();
		BlockState blockState = world.getBlockState(blockpos);
		TileEntity tile = world.getTileEntity(blockpos);
		int side = context.getFace().getOpposite().getIndex();
		if (world.getBlockState(blockpos.offset(Direction.byIndex(side))).isSolid()) {
			if (blockState.getBlock() == RedoxiationBlocks.BLOCKS.get(name).get()) {
				if (tile instanceof TileCog) {
					TileCog tileCog = (TileCog) tile;
					PlayerEntity playerentity = context.getPlayer();
					ItemStack itemstack = context.getItem();
					if (tileCog.placeSide(side)) {
						tileCog.markDirty();

						SoundType soundtype = blockState.getSoundType(world, blockpos, context.getPlayer());
						world.playSound(playerentity, blockpos,
								this.getPlaceSound(blockState, world, blockpos, context.getPlayer()),
								SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F,
								soundtype.getPitch() * 0.8F);
						if (playerentity == null || !playerentity.abilities.isCreativeMode) {
							itemstack.shrink(1);
						}

						return ActionResultType.func_233537_a_(world.isRemote);
					} else {
						return ActionResultType.FAIL;
					}
				}
			}
		} else {
			return ActionResultType.FAIL;
		}

		return super.tryPlace(context);
	}

}
