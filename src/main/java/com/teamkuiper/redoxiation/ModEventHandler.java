package com.teamkuiper.redoxiation;

import com.teamkuiper.redoxiation.blocks.INotifiableWhenBroken;

import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Redoxiation.MODID, bus = Bus.FORGE)
public class ModEventHandler {
	
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent e) {
		Block block = e.getState().getBlock();
		if(block instanceof INotifiableWhenBroken) {
			((INotifiableWhenBroken) block).onBroken(e);
		}
	}

}
