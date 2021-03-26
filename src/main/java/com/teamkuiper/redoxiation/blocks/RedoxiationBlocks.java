package com.teamkuiper.redoxiation.blocks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.teamkuiper.redoxiation.RedUtils;
import com.teamkuiper.redoxiation.Redoxiation;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RedoxiationBlocks {
	
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Redoxiation.MODID);
    
    public static final Map<String, Block.Properties> BASIC_BLOCK_DATA = new HashMap<String, Block.Properties>();

	public static final Map<String, RegistryObject<Block>> BASIC_BLOCKS = new HashMap<String, RegistryObject<Block>>();
	public static final Map<String, RegistryObject<BlockItem>> BASIC_BLOCKS_BI = new HashMap<String, RegistryObject<BlockItem>>();
    
    
    static {
    	BASIC_BLOCK_DATA.put("ore_copper", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("ore_tin", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("ore_lead", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("ore_silver", RedUtils.createStoneProperties(2, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("ore_nickel", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("ore_platinum", RedUtils.createStoneProperties(2, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("ore_zinc", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("ore_cobalt", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("ore_chromium", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	
    	BASIC_BLOCK_DATA.put("pitchblend", RedUtils.createStoneProperties(2, 2.5F, 5.0F));
    	BASIC_BLOCK_DATA.put("limestone", RedUtils.createStoneProperties(1, 2.0F, 10.0F));
    	BASIC_BLOCK_DATA.put("dolomite", RedUtils.createStoneProperties(1, 2.0F, 10.0F));
    	BASIC_BLOCK_DATA.put("salt_rock", RedUtils.createStoneProperties(1, 2.0F, 10.0F));
    	BASIC_BLOCK_DATA.put("bauxite", RedUtils.createStoneProperties(2, 2.0F, 10.0F));
    	BASIC_BLOCK_DATA.put("rutile", RedUtils.createStoneProperties(2, 2.0F, 10.0F));
    	BASIC_BLOCK_DATA.put("scheelite", RedUtils.createStoneProperties(2, 2.0F, 10.0F));
    	BASIC_BLOCK_DATA.put("cryolite", RedUtils.createStoneProperties(1, 2.0F, 10.0F));
    	BASIC_BLOCK_DATA.put("ore_sulfur", RedUtils.createStoneProperties(2, 2.0F, 10.0F));
    	
    	BASIC_BLOCK_DATA.put("ore_ferro_nickel", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BASIC_BLOCK_DATA.put("ore_pseudo_bronze", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BASIC_BLOCK_DATA.put("ore_pseudo_brass_ore", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	
    	BASIC_BLOCK_DATA.put("argent_aurum", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BASIC_BLOCK_DATA.put("pseudo_solder", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BASIC_BLOCK_DATA.put("pseudo_stellite", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BASIC_BLOCK_DATA.put("tntium", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	
    	BASIC_BLOCK_DATA.put("obsidian_iron", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_gold", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_copper", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_tin", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_lead", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_silver", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_nickel", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_platinum", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_zinc", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_cobalt", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_chromium", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BASIC_BLOCK_DATA.put("obsidian_uranium", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	
    	Iterator<String> iter = BASIC_BLOCK_DATA.keySet().iterator();
    	
    	while(iter.hasNext()) {
    		String name = iter.next();
    		BASIC_BLOCKS.put(name, BLOCKS.register(name, () -> new Block(BASIC_BLOCK_DATA.get(name))));
    		BASIC_BLOCKS_BI.put(name, RedoxiationItems.ITEMS.register(name, () -> new BlockItem(BASIC_BLOCKS.get(name).get(), RedoxiationItems.BLOCKITEM_BASIC_PROPERTISE)));
    	}
    }
    
}
