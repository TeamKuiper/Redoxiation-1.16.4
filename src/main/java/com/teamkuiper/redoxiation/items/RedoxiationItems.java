package com.teamkuiper.redoxiation.items;

import java.util.HashMap;
import java.util.Map;

import com.teamkuiper.redoxiation.Redoxiation;
import com.teamkuiper.redoxiation.RedoxiationItemGroups;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RedoxiationItems {

    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Redoxiation.MODID);
    

	public static final Item.Properties ITEM_BASIC_PROPERTISE = new Item.Properties().tab(RedoxiationItemGroups.ITEM_GROUP);
	public static final Item.Properties BLOCKITEM_BASIC_PROPERTISE = new Item.Properties().tab(RedoxiationItemGroups.BLOCK_GROUP);
    
	
	public static final String[] BASIC_ITEM_NAMES = {
			"calcite", "salt_chunk", "raw_bauxite",
			"raw_rutile", "raw_scheelite", "sulfur_chunk",
			"dust_sulfur", "item_cryolite", "dolomite_shard",
			"dolomite_powder",
			//Crushed ores
			"crushed_ore_iron", "crushed_ore_gold", "crushed_ore_copper", "crushed_ore_tin",
			"crushed_ore_lead", "crushed_ore_silver", "crushed_ore_nickel",
			"crushed_ore_platinum", "crushed_ore_zinc", "crushed_ore_chromium",
			"crushed_ore_cobalt", "crushed_pitchblend",
			//Dust ores
			"dust_ore_iron", "dust_ore_gold", "dust_ore_copper", "dust_ore_tin", "dust_ore_lead",
			"dust_ore_silver", "dust_ore_nickel", "dust_ore_platinum", "dust_ore_zinc", "dust_ore_cobalt",
			"dust_ore_chromium", "dust_salt", "dust_bauxite", "dust_rutile", "dust_scheelite",
			"dust_ore_aluminium", "dust_ore_titanium", "dust_ore_tungsten",
			//Ingots
			"ingot_copper", "ingot_tin", "ingot_lead", "ingot_silver", "ingot_nickel",
			"ingot_platinum", "ingot_zinc", "ingot_cobalt", "ingot_chromium", "ingot_uranium",
			"ingot_plutonium", "ingot_steel", "ingot_aluminum", "ingot_tungsten", "ingot_titanium",
			//Nuggets
			"nugget_iron", "nugget_copper", "nugget_tin", "nugget_lead",
			"nugget_silver", "nugget_platinum", "nugget_zinc", "nugget_nickel",
			"nugget_chromium", "nugget_aluminum", "nugget_titanium", "nugget_tungsten",
			"nugget_steel",
			//Crashed blocks
			"crashed_netherrack", "crashed_obsidian"};
	
	
	public static final Map<String, RegistryObject<Item>> ITEMS = new HashMap<String, RegistryObject<Item>>();
    
    
    static {
    	for(String name : BASIC_ITEM_NAMES) {
    		ITEMS.put(name, ITEM_REGISTER.register(name, () -> new Item(ITEM_BASIC_PROPERTISE)));
    	}
    	
    	ITEMS.put(ItemConstructionHammer.NAME, ITEM_REGISTER.register(ItemConstructionHammer.NAME, () -> new ItemConstructionHammer()));
    }



}
