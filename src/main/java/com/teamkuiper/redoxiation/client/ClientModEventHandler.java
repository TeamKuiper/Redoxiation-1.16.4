package com.teamkuiper.redoxiation.client;

import com.teamkuiper.redoxiation.Redoxiation;
import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;
import com.teamkuiper.redoxiation.blocks.tileentities.TileCog;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Redoxiation.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEventHandler {
	
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent e) {
		ClientRegistry.bindTileEntityRenderer((TileEntityType<TileCog>) RedoxiationBlocks.TILE_ENTITY_TYPES.get("wooden_cog").get(), TileCogRenderer::new);
	}

}
