package com.teamkuiper.redoxiation.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHammerable {

	public void onHammered(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn);
	
}
