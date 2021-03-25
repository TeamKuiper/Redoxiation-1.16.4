package com.teamkuiper.redoxiation.items;

import com.teamkuiper.redoxiation.Redoxiation;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RedoxiationItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Redoxiation.MODID);
    

	public static final Item.Properties ITEM_BASIC_PROPERTISE = new Item.Properties().tab(Redoxiation.ITEM_GROUP);
	public static final Item.Properties BLOCKITEM_BASIC_PROPERTISE = new Item.Properties().tab(Redoxiation.BLOCK_GROUP);
    
    public static final RegistryObject<Item> RAW_BAUXITE = ITEMS.register("raw_bauxite", () -> new Item(ITEM_BASIC_PROPERTISE));



}
