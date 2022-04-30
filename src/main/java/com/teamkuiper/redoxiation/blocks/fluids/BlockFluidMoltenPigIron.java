package com.teamkuiper.redoxiation.blocks.fluids;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BlockFluidMoltenPigIron extends FlowingFluid {

	protected static int mapColor = 0xFFFFFFFF;
	protected static float overlayAlpha = 0.2F;
	protected static SoundEvent emptySound = SoundEvents.BUCKET_EMPTY_LAVA;
	protected static SoundEvent fillSound = SoundEvents.BUCKET_FILL_LAVA;
	protected static Material material = Material.WATER;
	
	@Override
	public Fluid getFlowing() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Fluid getSource() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected boolean canConvertToSource() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void beforeDestroyingBlock(IWorld p_205580_1_, BlockPos p_205580_2_, BlockState p_205580_3_) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected int getSlopeFindDistance(IWorldReader p_185698_1_) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected int getDropOff(IWorldReader p_204528_1_) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Item getBucket() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected boolean canBeReplacedWith(FluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_,
			Fluid p_215665_4_, Direction p_215665_5_) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getTickDelay(IWorldReader p_205569_1_) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected float getExplosionResistance() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected BlockState createLegacyBlock(FluidState p_204527_1_) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isSource(FluidState p_207193_1_) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getAmount(FluidState p_207192_1_) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}