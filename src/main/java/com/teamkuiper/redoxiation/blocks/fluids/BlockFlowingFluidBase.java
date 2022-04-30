package com.teamkuiper.redoxiation.blocks.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BlockFlowingFluidBase extends FlowingFluid {
	
	String name;
	int slopeFindDistance;
	int levelDecreasePerBlock;
	
	
	public BlockFlowingFluidBase(String name) {
		this.name = name;
	}

	@Override
	public Fluid getFlowing() {
		return RedoxiationFluids.STILL_FLUIDS.get(name).get();
	}

	@Override
	public Fluid getSource() {
		return RedoxiationFluids.STILL_FLUIDS.get(name).get();
	}

	@Override
	protected boolean canConvertToSource() {
		return false;
	}

	@Override
	protected void beforeDestroyingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
	      TileEntity tileentity = state.hasTileEntity() ? worldIn.getBlockEntity(pos) : null;
	      Block.dropResources(state, worldIn, pos, tileentity);		
	}

	@Override
	protected int getSlopeFindDistance(IWorldReader worldIn) {
		return 0;
	}

	@Override
	protected int getDropOff(IWorldReader worldIn) {
		return 0;
	}

	@Override
	public Item getBucket() {
		return RedoxiationFluids.BUCKETS.get(name).get();
	}

	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid,
			Direction direction) {
		return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
	}

	@Override
	public int getTickDelay(IWorldReader p_205569_1_) {
		return 0;//TODO FROM THIS
	}

	@Override
	protected float getExplosionResistance() {
		return 0;
	}

	@Override
	protected BlockState createLegacyBlock(FluidState state) {
		return null;
	}

	@Override
	public boolean isSource(FluidState state) {
		return false;
	}

	@Override
	public int getAmount(FluidState state) {
		return 0;
	}}
