package com.teamkuiper.redoxiation.blocks;

import com.teamkuiper.redoxiation.blocks.tileentities.TileMultiblockBase;
import com.teamkuiper.redoxiation.items.ItemConstructionHammer;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockMultiblockBase extends Block {

	String name;

	public static final Property<Boolean> IS_MULTIBLOCK = BooleanProperty.create("is_multiblock");
	public static final Property<Boolean> IS_ROOT = BooleanProperty.create("is_root");

	public BlockMultiblockBase(String name, Properties properties) {
		super(properties);
		this.name = name;
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(IS_MULTIBLOCK, false)
				.setValue(IS_ROOT, false));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return RedoxiationBlocks.TILE_ENTITY_TYPES.get(name).get().create();
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit1) {
		if (!worldIn.isClientSide && handIn == Hand.MAIN_HAND) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof TileMultiblockBase) {
				TileMultiblockBase tileMulti = (TileMultiblockBase) tile;
				if (tileMulti.hasRoot()) {
					// TODO open gui
					player.sendMessage(new StringTextComponent("OPEN GUI"), null);
					return ActionResultType.PASS;
				} else {
					player.sendMessage(player.inventory.getSelected().getDisplayName(), null);
					if (player.inventory.getSelected().getItem() == RedoxiationItems.ITEMS.get(ItemConstructionHammer.NAME)
							.get()) {
						tileMulti.updateStructure();
					}
					return ActionResultType.PASS;
				}

			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.playerWillDestroy(worldIn, pos, state, player);
		
		if (!worldIn.isClientSide) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			System.out.println(tile);
			if (tile instanceof TileMultiblockBase) {
				TileMultiblockBase tileMulti = (TileMultiblockBase) tile;
				System.out.println("HR: " + tileMulti.hasRoot());
				if(tileMulti.hasRoot()) {
					tileMulti.resetStructure();
					tileMulti.dropItems();
					player.sendMessage(new StringTextComponent("BROKEN"), null);
				}
			}
		}
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(IS_MULTIBLOCK);
		builder.add(IS_ROOT);
	}
}
