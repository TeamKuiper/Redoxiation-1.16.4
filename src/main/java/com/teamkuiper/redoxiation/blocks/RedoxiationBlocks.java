package com.teamkuiper.redoxiation.blocks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.teamkuiper.redoxiation.RedUtils;
import com.teamkuiper.redoxiation.Redoxiation;
import com.teamkuiper.redoxiation.blocks.blockitems.BlockItemCog;
import com.teamkuiper.redoxiation.blocks.tileentities.TileBlastFurnace;
import com.teamkuiper.redoxiation.blocks.tileentities.TileCog;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RedoxiationBlocks {
	
    public static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Redoxiation.MODID);
    
    public static final Map<String, Block.Properties> BLOCK_DATA = new HashMap<String, Block.Properties>();

	public static final Map<String, RegistryObject<Block>> BLOCKS = new HashMap<String, RegistryObject<Block>>();
	public static final Map<String, RegistryObject<BlockItem>> BLOCKS_BI = new HashMap<String, RegistryObject<BlockItem>>();

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_REGISTER = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, Redoxiation.MODID);
	public static final Map<String, RegistryObject<TileEntityType<?>>> TILE_ENTITY_TYPES = new HashMap<String, RegistryObject<TileEntityType<?>>>();
    
    static {
    	
    	//Registering basic blocks
    	BLOCK_DATA.put("ore_copper", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BLOCK_DATA.put("ore_tin", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BLOCK_DATA.put("ore_lead", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BLOCK_DATA.put("ore_silver", RedUtils.createStoneProperties(2, 2.5F, 5.0F));
    	BLOCK_DATA.put("ore_nickel", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BLOCK_DATA.put("ore_platinum", RedUtils.createStoneProperties(2, 2.5F, 5.0F));
    	BLOCK_DATA.put("ore_zinc", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BLOCK_DATA.put("ore_cobalt", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	BLOCK_DATA.put("ore_chromium", RedUtils.createStoneProperties(1, 2.5F, 5.0F));
    	
    	BLOCK_DATA.put("pitchblend", RedUtils.createStoneProperties(2, 2.5F, 5.0F));
    	BLOCK_DATA.put("limestone", RedUtils.createStoneProperties(1, 2.0F, 10.0F));
    	BLOCK_DATA.put("dolomite", RedUtils.createStoneProperties(1, 2.0F, 10.0F));
    	BLOCK_DATA.put("salt_rock", RedUtils.createStoneProperties(1, 2.0F, 10.0F));
    	BLOCK_DATA.put("bauxite", RedUtils.createStoneProperties(2, 2.0F, 10.0F));
    	BLOCK_DATA.put("rutile", RedUtils.createStoneProperties(2, 2.0F, 10.0F));
    	BLOCK_DATA.put("scheelite", RedUtils.createStoneProperties(2, 2.0F, 10.0F));
    	BLOCK_DATA.put("cryolite", RedUtils.createStoneProperties(1, 2.0F, 10.0F));
    	BLOCK_DATA.put("ore_sulfur", RedUtils.createStoneProperties(2, 2.0F, 10.0F));
    	
    	BLOCK_DATA.put("ore_ferro_nickel", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BLOCK_DATA.put("ore_pseudo_bronze", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BLOCK_DATA.put("ore_pseudo_brass_ore", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	
    	BLOCK_DATA.put("argent_aurum", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BLOCK_DATA.put("pseudo_solder", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BLOCK_DATA.put("pseudo_stellite", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	BLOCK_DATA.put("tntium", RedUtils.createStoneProperties(2, 3.0F, 15.0F));
    	
    	BLOCK_DATA.put("obsidian_iron", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_gold", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_copper", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_tin", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_lead", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_silver", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_nickel", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_platinum", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_zinc", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_cobalt", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_chromium", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	BLOCK_DATA.put("obsidian_uranium", RedUtils.createStoneProperties(3, 50.0F, 2000.0F));
    	
    	Iterator<String> iter = BLOCK_DATA.keySet().iterator();
    	
    	while(iter.hasNext()) {
    		String name = iter.next();
    		BLOCKS.put(name, BLOCK_REGISTER.register(name, () -> new Block(BLOCK_DATA.get(name))));
    		BLOCKS_BI.put(name, RedoxiationItems.ITEM_REGISTER.register(name, () -> new BlockItem(BLOCKS.get(name).get(), RedoxiationItems.BLOCKITEM_BASIC_PROPERTISE)));
    	}
    	
    	//Registering blocks having tile entities
    	
    	final String WOODEN_COG_NAME = BlockCog.WOODEN_NAME;
    	BLOCK_DATA.put(WOODEN_COG_NAME, Block.Properties.of(Material.WOOD).noOcclusion());
    	BLOCKS.put(WOODEN_COG_NAME, BLOCK_REGISTER.register(WOODEN_COG_NAME, () -> new BlockCog(WOODEN_COG_NAME, BLOCK_DATA.get(WOODEN_COG_NAME))));
    	BLOCKS_BI.put(WOODEN_COG_NAME, RedoxiationItems.ITEM_REGISTER.register(WOODEN_COG_NAME, () -> new BlockItemCog(WOODEN_COG_NAME)));
    	TILE_ENTITY_TYPES.put(WOODEN_COG_NAME, TILE_ENTITY_REGISTER.register(WOODEN_COG_NAME, () -> TileEntityType.Builder.of(() -> new TileCog(WOODEN_COG_NAME), RedoxiationBlocks.BLOCKS.get(WOODEN_COG_NAME).get()).build(null)));
    	
    	final String BLAST_FURNACE_NAME = BlockBlastFurnace.NAME;
    	BLOCK_DATA.put(BLAST_FURNACE_NAME, Block.Properties.of(Material.STONE).noOcclusion());
    	BLOCKS.put(BLAST_FURNACE_NAME, BLOCK_REGISTER.register(BLAST_FURNACE_NAME, () -> new BlockBlastFurnace(BLOCK_DATA.get(BLAST_FURNACE_NAME))));
    	BLOCKS_BI.put(BLAST_FURNACE_NAME, RedoxiationItems.ITEM_REGISTER.register(BLAST_FURNACE_NAME, () -> new BlockItem(BLOCKS.get(BLAST_FURNACE_NAME).get(), RedoxiationItems.BLOCKITEM_BASIC_PROPERTISE)));
    	TILE_ENTITY_TYPES.put(BLAST_FURNACE_NAME, TILE_ENTITY_REGISTER.register(BLAST_FURNACE_NAME, () -> TileEntityType.Builder.of(() -> new TileBlastFurnace(), RedoxiationBlocks.BLOCKS.get(BLAST_FURNACE_NAME).get()).build(null)));
    	
    	//Registering fluids
    }
    
}
