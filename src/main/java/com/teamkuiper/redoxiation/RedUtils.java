package com.teamkuiper.redoxiation;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class RedUtils {
	
	public static Block.Properties createBlockProperties(Material material, ToolType toolType, int harvestLevel, float hardness, float resistance) {
		return Block.Properties.of(material).harvestTool(toolType).harvestLevel(harvestLevel).strength(hardness, resistance);
	}
	
	public static Block.Properties createStoneProperties(int harvestLevel, float hardness, float resistance) {
		return createBlockProperties(Material.STONE, ToolType.PICKAXE, harvestLevel, hardness, resistance);
	}
		
}
