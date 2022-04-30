package com.teamkuiper.redoxiation.blocks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.teamkuiper.redoxiation.blocks.tileentities.TileMultiblockBase;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class MultiblockData {

	Map<Vector3i, Block> structureMap = new HashMap<>();
	
	public boolean addBlock(Vector3i relativePos, Block block) {
		if(structureMap.containsKey(relativePos))
			return false;
		structureMap.put(relativePos, block);
		return true;
	}

	public BlockPos checkStructure(World world, BlockPos checkPos) {
		whole:
		for(Vector3i rootVec : structureMap.keySet()) {
			for(Vector3i relVec : structureMap.keySet()) {
				Vector3i checkVec = new Vector3i(relVec.getX()-rootVec.getX(), relVec.getY()-rootVec.getY(), relVec.getZ()-rootVec.getZ());
				Block block = structureMap.get(relVec);
				
				if(world.getBlockState(checkPos.offset(checkVec)).getBlock() != block) {
					continue whole;
				}
			}
			System.out.println("SUCCEEDED");
			return checkPos.subtract(rootVec);
		}
		return null;
	}
	
	public boolean notifyRoot(World world, BlockPos rootPos, TileMultiblockBase rootTile) {
		for(Vector3i relVec : structureMap.keySet()) {
			BlockPos pos = rootPos.offset(relVec);
			TileEntity tile = world.getBlockEntity(pos);
			System.out.println(pos);
			if(world.getBlockState(pos).getBlock() instanceof BlockMultiblockBase) {
				world.setBlock(pos, world.getBlockState(pos)
						.setValue(BlockMultiblockBase.IS_MULTIBLOCK, rootTile != null)
						.setValue(BlockMultiblockBase.IS_ROOT, relVec.getX() == 0 && relVec.getY() == 0 && relVec.getZ() == 0), Constants.BlockFlags.DEFAULT); //what is this flag
			}
			System.out.println(tile == null ? pos : (tile.getBlockPos() + " " + tile.getClass()));
			if(tile != null && tile instanceof TileMultiblockBase) {
				((TileMultiblockBase) tile).updateRoot(rootTile);
			}
		}
		return true;
	}
	
}
