package com.teamkuiper.redoxiation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;
import com.teamkuiper.redoxiation.blocks.fluids.RedoxiationFluids;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("redoxiation")
public class Redoxiation {

	public static final Logger LOGGER = LogManager.getLogger();
	
	public static final String MODID = "redoxiation";


	public Redoxiation() {
		RedoxiationFluids.FLUID_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
		RedoxiationBlocks.BLOCK_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
		RedoxiationItems.ITEM_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
		RedoxiationBlocks.TILE_ENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
		
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		eventBus.addListener(this::onClientSetup);
	}
	
	private void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			RedoxiationFluids.onClientSetup();
		});
	}
		
}
