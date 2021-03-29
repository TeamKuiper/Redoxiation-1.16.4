package com.teamkuiper.redoxiation.blocks;

import net.minecraftforge.event.world.BlockEvent;

public interface INotifiableWhenBroken {
	
	public void onBroken(BlockEvent.BreakEvent e);

}
