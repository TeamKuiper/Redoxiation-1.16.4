package com.teamkuiper.redoxiation.blocks.tileentities;

import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileCog extends TileEntity implements ITickableTileEntity {
	
    public TileCog(String name) {
		super(RedoxiationBlocks.TILE_ENTITY_TYPES.get(name).get());
	}

	public boolean[] side = new boolean[6];
	
	@Override
	public void tick() {
		//this.level.setBlock(this.worldPosition.below(), Blocks.AIR.defaultBlockState(), 0)
		this.world.setBlockState(this.pos.down(), Blocks.AIR.getDefaultState());
		System.out.println("test");
	}

}
