package com.teamkuiper.redoxiation.blocks.tileentities;

import com.teamkuiper.redoxiation.blocks.MultiblockData;
import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class TileMultiblockBase extends TileEntity implements ITickable {
	
	TileMultiblockBase rootTile;
	
	final MultiblockData structure;
	
	public TileMultiblockBase(String name, MultiblockData structure) {
		super(RedoxiationBlocks.TILE_ENTITY_TYPES.get(name).get());
		this.structure = structure;
	}
	
	public boolean hasRoot() {
		return rootTile != null;
	}
	
	//returns root blockpos.
	public BlockPos checkStructure() {
		return structure.checkStructure(world, pos);
	}
	
	public void updateStructure() {
		System.out.println("UPDATE STRUCTURE");
		if(rootTile == null) {
			BlockPos rootPos = checkStructure();
			if(rootPos != null) {
				TileEntity rootTile = world.getTileEntity(rootPos);
				if(rootTile instanceof TileMultiblockBase) {
					structure.notifyRoot(world, rootPos, (TileMultiblockBase) rootTile);
				}
			}
		} else {
			if(rootTile.equals(this)) {
				if(checkStructure() == null) {
					rootTile = null;
					resetStructure();
				}
			} else {
				rootTile.updateStructure();
			}
		}
	}
	
	public void resetStructure() {
		System.out.println("RS: " + rootTile);
		if(rootTile != null) {
			if(rootTile.equals(this)) {
				structure.notifyRoot(world, pos, null);
			} else {
				rootTile.resetStructure();
			}
		}
	}
	
	public void dropItems() {
		//TODO
		
	}
	
	public void updateRoot(TileMultiblockBase tile) {
		System.out.println("UpdateRoot: " + this.pos + " " + (tile == null ? null : tile.pos));
		rootTile = tile;
		this.markDirty(); //TODO rootTile is not saved when world is reopened
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		nbt = super.write(nbt);
		if(rootTile != null) {
			nbt.putInt("rootTileX", rootTile.pos.getX());
			nbt.putInt("rootTileY", rootTile.pos.getY());
			nbt.putInt("rootTileZ", rootTile.pos.getZ());
			System.out.println("WRITING: " + rootTile.pos);
		}
		return nbt;
	}
	
	private BlockPos tmpPos;
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		if(nbt.contains("rootTileX")) {
			int rootTileX, rootTileY, rootTileZ;
			rootTileX = nbt.getInt("rootTileX");
			rootTileY = nbt.getInt("rootTileY");
			rootTileZ = nbt.getInt("rootTileZ");
			tmpPos = new BlockPos(rootTileX, rootTileY, rootTileZ); //world is null at this time
		}
	}

	@Override
	public void tick() {
		if(tmpPos != null) {
			TileEntity tile = world.getTileEntity(tmpPos);
			if(tile instanceof TileMultiblockBase) {
				rootTile = (TileMultiblockBase) tile;
			}
			tmpPos = null;
		}
	}

	//Synchronizing on chunk load
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbtTag = new CompoundNBT();
		return this.write(nbtTag);
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
		this.read(state, nbt);
	}
	
	//Synchronizing on block update
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTag = new CompoundNBT();
		return new SUpdateTileEntityPacket(getPos(), -1, write(nbtTag));
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(null, pkt.getNbtCompound());
	}

}
