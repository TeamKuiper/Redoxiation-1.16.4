package com.teamkuiper.redoxiation;

import com.teamkuiper.redoxiation.blocks.fluids.RedoxiationFluids;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class RedUtils {
	
	public static Block.Properties createFluidBlockProperties(Material mat, float strength) {
		return AbstractBlock.Properties.of(mat).noCollission().strength(strength).noDrops();
	}
	
	public static Block.Properties createBlockProperties(Material material, ToolType toolType, int harvestLevel, float hardness, float resistance) {
		return Block.Properties.of(material).harvestTool(toolType).harvestLevel(harvestLevel).strength(hardness, resistance);
	}
	
	public static Block.Properties createStoneProperties(int harvestLevel, float hardness, float resistance) {
		return createBlockProperties(Material.STONE, ToolType.PICKAXE, harvestLevel, hardness, resistance);
	}
	
	public static FluidAttributes createFluidAttributes() {
		return null; //TODO
	}
	
	public static ForgeFlowingFluid.Properties createDefaultFluidProperties(String name, FluidAttributes.Builder fluidAttributesBuilder) {
		return new ForgeFlowingFluid.Properties(() -> RedoxiationFluids.STILL_FLUIDS.get(name).get(), () -> RedoxiationFluids.FLOWING_FLUIDS.get(name).get(), fluidAttributesBuilder);
	}
		
}
