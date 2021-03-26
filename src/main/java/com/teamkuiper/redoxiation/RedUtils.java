package com.teamkuiper.redoxiation;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class RedUtils {
	
	public static Block.Properties createBlockProperties(Material material, ToolType toolType, int harvestLevel, float hardness, float resistance) {
		return Block.Properties.create(material).harvestTool(toolType).harvestLevel(harvestLevel).hardnessAndResistance(hardness, resistance);
	}
	
	public static Block.Properties createStoneProperties(int harvestLevel, float hardness, float resistance) {
		return createBlockProperties(Material.ROCK, ToolType.PICKAXE, harvestLevel, hardness, resistance);
	}
		
}
