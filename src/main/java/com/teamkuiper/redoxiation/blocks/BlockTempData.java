package com.teamkuiper.redoxiation.blocks;

import net.minecraft.block.Block;

public class BlockTempData {
	
	String name;
	Block.Properties properties;
	
	public void BlockTempData(String name, Block.Properties properties) {
		this.name = name;
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public Block.Properties getProperties() {
		return properties;
	}

}
