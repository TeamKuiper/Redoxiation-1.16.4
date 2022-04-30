package com.teamkuiper.redoxiation.blocks.shapes;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import codechicken.lib.raytracer.IndexedVoxelShape;
import codechicken.lib.raytracer.MultiIndexedVoxelShape;
import codechicken.lib.raytracer.VoxelShapeRayTraceResult;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class CogVoxelShape extends MultiIndexedVoxelShape {

	private static final IndexedVoxelShape SHAPE_DOWN = new IndexedVoxelShape(Block.box(4, 0, 4, 12, 2, 12), 0);
	private static final IndexedVoxelShape SHAPE_UP = new IndexedVoxelShape(Block.box(4, 14, 4, 12, 16, 12), 1);
	private static final IndexedVoxelShape SHAPE_NORTH = new IndexedVoxelShape(Block.box(4, 4, 0, 12, 12, 2), 2);
	private static final IndexedVoxelShape SHAPE_SOUTH = new IndexedVoxelShape(Block.box(4, 4, 14, 12, 12, 16), 3);
	private static final IndexedVoxelShape SHAPE_WEST = new IndexedVoxelShape(Block.box(0, 4, 4, 2, 12, 12), 4);
	private static final IndexedVoxelShape SHAPE_EAST = new IndexedVoxelShape(Block.box(14, 4, 4, 16, 12, 12), 5);
	public static final IndexedVoxelShape[] SHAPES = new IndexedVoxelShape[] {SHAPE_DOWN, SHAPE_UP, SHAPE_NORTH, SHAPE_SOUTH, SHAPE_WEST, SHAPE_EAST};

    private static final ImmutableSet<IndexedVoxelShape> SHAPE_SET = new ImmutableSet.Builder<IndexedVoxelShape>().add(SHAPES).build();
    
    private boolean[] sideEnabled;

    public CogVoxelShape() {
    	super(SHAPE_SET);
    }
    
    public void setSideEnabled(boolean[] sides) {
    	sideEnabled = sides;
    }

    @Nullable
    @Override
    public VoxelShapeRayTraceResult clip(Vector3d start, Vector3d end, BlockPos pos) {
        VoxelShapeRayTraceResult closest = null;
        double dist = Double.MAX_VALUE;
        int index = 0;
        for (IndexedVoxelShape shape : SHAPE_SET) {
        	if(sideEnabled != null && sideEnabled.length > index && sideEnabled[index]) {
	            VoxelShapeRayTraceResult hit = shape.clip(start, end, pos);
	            if (hit != null && dist >= hit.dist) {
	                closest = hit;
	                dist = hit.dist;
	            }
        	}
        	index++;
        }
        return closest;
    }
}
