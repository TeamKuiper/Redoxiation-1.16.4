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
	
	public boolean isStructured() {
		return structure.isStructured(world, pos);
	}
	
	public void updateStructure() {
		System.out.println("UPDATE STRUCTURE");
		if(rootTile == null) {
			if(isStructured()) {
				structure.notifyRoot(world, pos, this);
			}
		} else {
			if(rootTile.equals(this)) {
				if(!isStructured()) {
					rootTile = null;
					resetStructure();
				}
			} else {
				rootTile.updateStructure();
			}
		}
	}
	
	public void resetStructure() {
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
