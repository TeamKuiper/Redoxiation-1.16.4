package com.teamkuiper.redoxiation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("redoxiation")
public class Redoxiation {

	public static final Logger LOGGER = LogManager.getLogger();
	
	public static final String MODID = "redoxiation";

	//ItemGroups a.k.a Creative tabs
	public static final ItemGroup BLOCK_GROUP = new RedoxiationItemGroups.RDBlockGroup();
	public static final ItemGroup ITEM_GROUP = new RedoxiationItemGroups.RDItemGroup();

	public Redoxiation() {
		RedoxiationItems.ITEM_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
		RedoxiationBlocks.BLOCK_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
		RedoxiationBlocks.TILE_ENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
		
}
