package com.teamkuiper.redoxiation.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockCog extends Block {
	
	String name;
	
	public BlockCog(String name, Properties properties) {
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
}
