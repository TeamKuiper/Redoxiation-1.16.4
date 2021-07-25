package com.teamkuiper.redoxiation.blocks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.teamkuiper.redoxiation.blocks.tileentities.TileMultiblockBase;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class MultiblockData {

	Map<Vector3i, Block> structureMap = new HashMap<>();
	
	public boolean addBlock(Vector3i relativePos, Block block) {
		if(structureMap.containsKey(relativePos))
			return false;
		structureMap.put(relativePos, block);
		return true;
	}

	public boolean isStructured(World world, BlockPos rootPos) {
		Iterator<Vector3i> iter = structureMap.keySet().iterator();
		
		while(iter.hasNext()) {
			Vector3i key = iter.next();
			Block value = structureMap.get(key);
			System.out.println(rootPos.add(key) + ": " + world.getBlockState(rootPos.add(key)).getBlock());
			
			if(world.getBlockState(rootPos.add(key)).getBlock() != value) {
				return false;
			}
		}
		return true;
	}
	
	public boolean notifyRoot(World world, BlockPos rootPos, TileMultiblockBase root) {
		Iterator<Vector3i> iter = structureMap.keySet().iterator();
		
		while(iter.hasNext()) {
			Vector3i key = iter.next();
			
			TileEntity tile = world.getTileEntity(rootPos.add(key));
			if(tile instanceof TileMultiblockBase) {
				((TileMultiblockBase) tile).updateRoot(root);
			}
		}
		return true;
	}
	
}
