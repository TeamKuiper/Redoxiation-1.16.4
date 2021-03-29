package com.teamkuiper.redoxiation.blocks;

import com.teamkuiper.redoxiation.blocks.tileentities.TileCog;

import codechicken.lib.raytracer.RayTracer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class BlockCog extends Block implements INotifiableWhenBroken {

	String name;
	
	private static final CogVoxelShape SHAPE = new CogVoxelShape();
	
	public static final Property<Direction> FIRST_SIDE = DirectionProperty.create("first_side", Direction.values());
	
	public BlockCog(String name, Properties properties) {
		super(properties);
		this.name = name;
		this.setDefaultState(
				this.stateContainer.getBaseState()
				.with(USE_WAVEFRONT_OBJ_MODEL, false)
				.with(FIRST_SIDE, Direction.DOWN)
				);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit1) {
		if(!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof TileCog) {
				TileCog tileCog = (TileCog) tile;
				RayTraceResult hit = RayTracer.retrace(player);
				tileCog.rotateSide(hit.subHit, TileCog.ROTATE_DEGREES); //TODO hit.subHit can sometimes be -1
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onBroken(BreakEvent e) {
		TileEntity tile = e.getWorld().getTileEntity(e.getPos());
		if(tile instanceof TileCog) {
			TileCog tileCog = (TileCog) tile;
			RayTraceResult hit = RayTracer.retrace(e.getPlayer());
			if(hit.subHit > -1) {
				tileCog.removeSide(hit.subHit);
	            spawnDrops(e.getState(), (World) e.getWorld(), e.getPos());
				if(!tileCog.isEmpty()) {
					e.setCanceled(true);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
		BlockPos pos1 = fromPos.subtract(pos);
		Direction direction = Direction.getFacingFromVector(pos1.getX(), pos1.getY(), pos1.getZ());
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TileCog) {
			TileCog tileCog = (TileCog) tile;
			if(tileCog.sideExists[direction.getIndex()]) {
				if(!worldIn.getBlockState(fromPos).isSolid()) {
					tileCog.removeSide(direction.getIndex());
		            spawnDrops(state, worldIn, pos);
					//TODO make effects
				}
			}
		}
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TileCog) {			
			SHAPE.setSideEnabled(((TileCog) tile).sideExists);
		}
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) {
		TileCog tileCog = (TileCog) worldIn.getTileEntity(pos);
		if(tileCog == null)
			return VoxelShapes.empty();
		VoxelShape shape = null;
		for(int i = 0; i < tileCog.sideExists.length; i++) {
			if(tileCog.sideExists[i]) {
				if(shape == null) {
					shape = CogVoxelShape.SHAPES[i];
				} else {
					shape = VoxelShapes.or(shape, CogVoxelShape.SHAPES[i]);
				}
			}
		}
		return shape == null ? VoxelShapes.empty() : shape;
	}
	
	
	@Override
	public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.fullCube();
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		TileCog tileCog = (TileCog) RedoxiationBlocks.TILE_ENTITY_TYPES.get(name).get().create();
		tileCog.placeSide(state.get(FIRST_SIDE).getIndex());
		return tileCog;
	}

	
	// Used for visuals only, as an easy way to get Forge to load the obj model used
	// by the WaveFront render style
	public static final Property<Boolean> USE_WAVEFRONT_OBJ_MODEL = BooleanProperty.create("use_wavefront_obj_model");
	

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(USE_WAVEFRONT_OBJ_MODEL);
		builder.add(FIRST_SIDE);
	}
}
