package com.teamkuiper.redoxiation.blocks.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

public class TileCog extends TileBase implements ITickableTileEntity {
	
    public TileCog(String name) {
		super(name);
	}
    
    public static final float ROTATE_DEGREES = 5.0F;

	public boolean[] sideExists = new boolean[6]; //D-U-N-S-W-E
	public float[] rotationInDegrees = new float[6];
	public float[] rotationPending = new float[6];
	
	public boolean placeSide(int side) {
		if(sideExists[side])
			return false;
		sideExists[side] = true;
		return true;
	}
	
	public boolean removeSide(int side) {
		if(sideExists[side]) {
			sideExists[side] = false;
			return true;
		}
		return false;
	}
	/*
	public boolean rotateSide(World world, BlockPos blockPos, int side, float rotInDegrees) {
		if(sideExists[side]) {
			rotationPending[side] = rotInDegrees;
			Direction direction = Direction.byIndex(side);
			int opposite = direction.getOpposite().getIndex();
			boolean canRotate = true;
			for(int i = 0; i < sideExists.length; i++ ) {
				if(i == side || i == opposite)
					continue;
				if(sideExists[i]) {
					if(!rotateSide(world, blockPos, i, -rotInDegrees))
						canRotate = false;
				} else {
					TileEntity tile = world.getTileEntity(blockPos.offset(direction));
					if(tile instanceof TileCog) {
						if(!((TileCog) tile).rotateSide(world, blockPos, side, -rotInDegrees)) //TODO what if at different speed?
							canRotate = false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean canRotate(World world, BlockPos blockPos, int side, float rotInDegrees) {
		if(sideExists[side]) {
			if(rotationPending[side] == 0 || rotationPending[side] == rotInDegrees) {
				rotationPending[side] = rotInDegrees;
				Direction direction = Direction.byIndex(side);
				int opposite = direction.getOpposite().getIndex();
				boolean canRotate = true;
				for(int i = 0; i < sideExists.length; i++ ) {
					if(i == side || i == opposite)
						continue;
					if(sideExists[i]) {
						if(!canRotate(world, blockPos, i, -rotInDegrees))
							canRotate = false;
					} else {
						TileEntity tile = world.getTileEntity(blockPos.offset(direction));
						if(tile instanceof TileCog) {
							if(!((TileCog) tile).canRotate(world, blockPos, side, -rotInDegrees)) //TODO what if at different speed?
								canRotate = false;
						}
					}
				}
				return canRotate;
			}
			return false;
		}
		return true;
	}
	
	private void confirmRotate(World world, BlockPos blockPos, boolean canRotate, int side) {
		if(canRotate) {
			rotationInDegrees[side] += rotationPending[side];
		}
		rotationPending[side] = 0;

		Direction direction = Direction.byIndex(side);
		int opposite = direction.getOpposite().getIndex();
		for(int i = 0; i < sideExists.length; i++) {
			if(i == side || i == opposite)
				continue;
			if(sideExists[i]) {
				confirmRotate(world, blockPos, canRotate, i);
			} else {
				TileEntity tile = world.getTileEntity(blockPos.offset(direction));
				if(tile instanceof TileCog) {
					((TileCog) tile).confirmRotate(world, blockPos, canRotate, side);
				}
			}
		}
	}*/
	
	public void rotateSide(int side, float rotInDegrees) {
		System.out.println("Attempting to rotate cog at " + this.worldPosition + "[" + side + "] for " + rotInDegrees + "deg.");
		if(rotInDegrees == 0)
			return;
		if(pendRotate(side, rotInDegrees) != PendResult.IMPOSSIBLE) {
			List<BlockCogSideInfo> neighbors = investigateNeighbors(side, rotInDegrees);
			if(neighbors != null) {
				for(BlockCogSideInfo neighbor : neighbors) {
					BlockState bs = level.getBlockState(this.worldPosition);
					neighbor.cog.confirmRotation(neighbor.side, true);
					level.sendBlockUpdated(neighbor.cog.worldPosition, bs, bs, Constants.BlockFlags.BLOCK_UPDATE);
				}
			}
		}
	}
	
	private List<BlockCogSideInfo> investigateNeighbors(int side, float rotInDegrees) {
		List<BlockCogSideInfo> neighbors = new ArrayList<BlockCogSideInfo>();
		System.out.println("Investigating: " + this.worldPosition + "[" + side + "]");
		
		if(sideExists[side]) {
			BlockPos tmpBlockPos;
			boolean canRotate = true;
			Direction direction = Direction.values()[side];
			int opposite = direction.getOpposite().get3DDataValue();
			List<BlockCogSideInfo> tmp;
			visit:
			for(int i = 0; i < sideExists.length; i++) { //Check all sides without myself and opposite
				Direction neighborDirection = Direction.values()[i];
				if(i == side || i == opposite)
					continue;
				if(sideExists[i]) { //Check inner cog (90deg)
					System.out.println("Pending-Inner: [" + neighborDirection + "]");
					switch(pendRotate(i, -rotInDegrees)) {
					case POSSIBLE:
						tmp = investigateNeighbors(i, -rotInDegrees);
						if(tmp == null) {
							canRotate = false;
							break visit;
						}
						neighbors.addAll(tmp);
						break;
					case IMPOSSIBLE:
						canRotate = false;
						break visit;
					default:
						break;
					}
				} else {
					tmpBlockPos = this.worldPosition.relative(neighborDirection);
					TileEntity tile = level.getBlockEntity(tmpBlockPos);
					System.out.println("Pending-Aside: " + tmpBlockPos + "[" + direction + "]");
					if(tile instanceof TileCog) { //Side by side (180 deg)
						switch(((TileCog) tile).pendRotate(side, -rotInDegrees)) {
						case POSSIBLE:
							tmp = ((TileCog) tile).investigateNeighbors(side, -rotInDegrees);
							if(tmp == null) {
								canRotate = false;
								break visit;
							}
							neighbors.addAll(tmp);
							break;
						case IMPOSSIBLE:
							canRotate = false;
							break visit;
						default:
							break;
						}
					} else { //Outer cog  (270 deg)
						tmpBlockPos = this.worldPosition.relative(neighborDirection).relative(direction);
						TileEntity tile1 = level.getBlockEntity(tmpBlockPos);
						System.out.println("Pending-Outer: " + tmpBlockPos + "[" + neighborDirection.getOpposite() + "]");
	
						if(tile1 instanceof TileCog) {
							switch(((TileCog) tile1).pendRotate(neighborDirection.getOpposite().get3DDataValue(), -rotInDegrees)) {
							case POSSIBLE:
								tmp = ((TileCog) tile1).investigateNeighbors(neighborDirection.getOpposite().get3DDataValue(), -rotInDegrees);
								if(tmp == null) {
									canRotate = false;
									break visit;
								}
								neighbors.addAll(tmp);
								break;
							case IMPOSSIBLE:
								canRotate = false;
								break visit;
							default:
								break;
							}
						} 
					}
				}
			}
			
			if(!canRotate) {
				confirmRotation(side, false);
				for(BlockCogSideInfo neighbor : neighbors) { //If rotation cannot be made, tell neighbors to reset and return null.
					neighbor.cog.confirmRotation(side, false);
				}
				return null;
			}
	
			neighbors.add(new BlockCogSideInfo(this, side));
		}
		return neighbors;
	}
	
	/**
	 * If rotation can be made, save rotation value and return true;
	 * @param side
	 * @param rotInDegrees
	 * @return If rotation can be made, save rotation value and return true. Otherwise, reset rotation value and return false.
	 */
	private PendResult pendRotate(int side, float rotInDegrees) {
		if(rotationPending[side] == 0) {
			rotationPending[side] = rotInDegrees;
			System.out.println("POSSIBLE");
			return PendResult.POSSIBLE;
		}
		if(rotationPending[side] == rotInDegrees) {
			System.out.println("VISITED");
			return PendResult.VISITED;
		}
		System.out.println("IMPOSSIBLE");
		rotationPending[side] = 0;
		return PendResult.IMPOSSIBLE;
	}
	
	private void confirmRotation(int side, boolean canRotate) {
		System.out.println("CONFIRMED: " + this.worldPosition + "[" + side + "] " + (canRotate ? "CAN" : "CANNOT") + " be rotated.");
		System.out.println("ROTAION: " + rotationPending[side]);
		System.out.println("BEFORE: " + rotationInDegrees[side]);
		if(canRotate) {
			rotationInDegrees[side] += rotationPending[side];
			rotationInDegrees[side] %= 180.0;
			this.setChanged();
			
		}
		rotationPending[side] = 0;

		System.out.println("AFTER: " + rotationInDegrees[side]);
	}
	
	
	public boolean isEmpty() {
		for(boolean bool : sideExists) {
			if(bool)
				return false;
		}
		return true;
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt = super.save(nbt);
		byte val = 0;
		for(int i = 0; i < sideExists.length; i++) {
			val <<= 1;
			val |= sideExists[i] ? 1 : 0;
		}
		nbt.putByte("sideExists", val);
		
		for(int i = 0; i < rotationInDegrees.length; i++) {
			nbt.putFloat("side_" + i, rotationInDegrees[i]);
		}
		
		return nbt;
	}
	
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		byte val = nbt.getByte("sideExists");
		for(int i = 0; i < sideExists.length; i++) {
			sideExists[i] = (val & 0b100000) != 0;
			val <<= 1;
		}

		for(int i = 0; i < rotationInDegrees.length; i++) {
			rotationInDegrees[i] = nbt.getFloat("side_" + i);
		}
	}
	
	@Override
	public void tick() {
		
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
	
	
	public static enum PendResult {
		POSSIBLE, VISITED, IMPOSSIBLE
	}
	
	public static class BlockCogSideInfo {
		public final TileCog cog;
		public final int side;
		
		public BlockCogSideInfo(TileCog cog, int side) {
			this.cog = cog;
			this.side = side;
		}
	}
}
