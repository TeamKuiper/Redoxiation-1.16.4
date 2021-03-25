package com.teamkuiper.redoxiation.blocks;

import com.teamkuiper.redoxiation.Redoxiation;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RedoxiationBlocks {
	
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Redoxiation.MODID);
    

	public static final Properties BAUXITE_PROPERTISE = Properties.of(Material.STONE)
			.harvestTool(ToolType.PICKAXE).harvestLevel(2).strength(2.0F, 10.0F);

    public static final RegistryObject<Block> BAUXITE = BLOCKS.register("bauxite", () -> new Block(BAUXITE_PROPERTISE));
    public static final RegistryObject<BlockItem> BAUXITE_BI = RedoxiationItems.ITEMS.register("bauxite", () -> new BlockItem(BAUXITE.get(), RedoxiationItems.BLOCKITEM_BASIC_PROPERTISE));

    
}
