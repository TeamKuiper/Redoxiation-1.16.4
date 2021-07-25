package com.teamkuiper.redoxiation.blocks.tileentities;

import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;

import net.minecraft.tileentity.TileEntity;

public class TileBase extends TileEntity {

	public TileBase(String name) {
		super(RedoxiationBlocks.TILE_ENTITY_TYPES.get(name).get());
	}

}