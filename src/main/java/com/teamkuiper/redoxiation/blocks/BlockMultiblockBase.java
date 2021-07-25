package com.teamkuiper.redoxiation.blocks;

import com.teamkuiper.redoxiation.blocks.tileentities.TileMultiblockBase;
import com.teamkuiper.redoxiation.items.ItemConstructionHammer;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockMultiblockBase extends Block {

	String name;

	public BlockMultiblockBase(String name, Properties properties) {
		super(properties);
		this.name = name;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return RedoxiationBlocks.TILE_ENTITY_TYPES.get(name).get().create();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit1) {
		if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
			player.sendMessage(new StringTextComponent("ACTIVATED"), null);
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof TileMultiblockBase) {
				TileMultiblockBase tileMulti = (TileMultiblockBase) tile;
				if (tileMulti.hasRoot()) {
					// TODO open gui
					player.sendMessage(new StringTextComponent("OPEN GUI"), null);
					return ActionResultType.PASS;
				} else {
					player.sendMessage(player.inventory.getCurrentItem().getItem().getName(), null);
					if (player.inventory.getCurrentItem().getItem() == RedoxiationItems.ITEMS.get(ItemConstructionHammer.NAME)
							.get()) {
						tileMulti.updateStructure();
					}
					return ActionResultType.PASS;
				}

			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		
		if (!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof TileMultiblockBase) {
				TileMultiblockBase tileMulti = (TileMultiblockBase) tile;
				if(tileMulti.hasRoot()) {
					tileMulti.resetStructure();
					tileMulti.dropItems();
					player.sendMessage(new StringTextComponent("ITEMS DROPPED"), null);
				}
			}
		}
	}

}
