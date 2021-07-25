package com.teamkuiper.redoxiation.blocks.tileentities;

import com.teamkuiper.redoxiation.blocks.BlockBlastFurnace;
import com.teamkuiper.redoxiation.blocks.MultiblockData;
import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.vector.Vector3i;

public class TileBlastFurnace extends TileMultiblockBase {
	
	public static final String NAME = BlockBlastFurnace.NAME;
	
	private static final MultiblockData STRUCT = new MultiblockData();
	
	static {
		Block blastFurnace = RedoxiationBlocks.BLOCKS.get(BlockBlastFurnace.NAME).get();

		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				for(int z = 0; z <= 2; z++) {
					if(x == 0 && y == 0 && z == 1) //empty in the middle
						STRUCT.addBlock(new Vector3i(x, y, z), Blocks.AIR);
					else
						STRUCT.addBlock(new Vector3i(x, y, z), blastFurnace);
				}
			}
		}
	}

	public TileBlastFurnace() {
		super(NAME, STRUCT);
	}

}
