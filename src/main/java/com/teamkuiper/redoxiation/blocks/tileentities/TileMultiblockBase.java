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
		return structure.checkStructure(level, worldPosition);
	}
	
	public void updateStructure() {
		System.out.println("UPDATE STRUCTURE");
		if(rootTile == null) {
			BlockPos rootPos = checkStructure();
			if(rootPos != null) {
				TileEntity rootTile = level.getBlockEntity(rootPos);
				if(rootTile instanceof TileMultiblockBase) {
					structure.notifyRoot(level, rootPos, (TileMultiblockBase) rootTile);
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
				structure.notifyRoot(level, worldPosition, null);
			} else {
				rootTile.resetStructure();
			}
		}
	}
	
	public void dropItems() {
		//TODO
		
	}
	
	public void updateRoot(TileMultiblockBase tile) {
		System.out.println("UpdateRoot: " + this.worldPosition + " " + (tile == null ? null : tile.worldPosition));
		rootTile = tile;
		this.setChanged(); //TODO rootTile is not saved when level is reopened
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt = super.save(nbt);
		if(rootTile != null) {
			nbt.putInt("rootTileX", rootTile.worldPosition.getX());
			nbt.putInt("rootTileY", rootTile.worldPosition.getY());
			nbt.putInt("rootTileZ", rootTile.worldPosition.getZ());
			System.out.println("WRITING: " + rootTile.worldPosition);
		}
		return nbt;
	}
	
	private BlockPos tmpPos;
	
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if(nbt.contains("rootTileX")) {
			int rootTileX, rootTileY, rootTileZ;
			rootTileX = nbt.getInt("rootTileX");
			rootTileY = nbt.getInt("rootTileY");
			rootTileZ = nbt.getInt("rootTileZ");
			tmpPos = new BlockPos(rootTileX, rootTileY, rootTileZ); //level is null at this time
		}
	}

	@Override
	public void tick() {
		if(tmpPos != null) {
			TileEntity tile = level.getBlockEntity(tmpPos);
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
		return this.save(nbtTag);
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
		this.load(state, nbt);
	}
	
	//Synchronizing on block update
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTag = new CompoundNBT();
		return new SUpdateTileEntityPacket(getBlockPos(), -1, save(nbtTag));
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.load(null, pkt.getTag());
	}

}
