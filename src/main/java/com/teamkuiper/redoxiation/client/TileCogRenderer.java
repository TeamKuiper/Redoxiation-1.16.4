package com.teamkuiper.redoxiation.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.teamkuiper.redoxiation.blocks.BlockCog;
import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;
import com.teamkuiper.redoxiation.blocks.tileentities.TileCog;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;

public class TileCogRenderer extends TileEntityRenderer<TileCog> {
	
	public TileCogRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		
	}

	@Override
	public void render(TileCog tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(1, 1, 1);

		BlockState state = RedoxiationBlocks.BLOCKS.get("wooden_cog").get().defaultBlockState()
				.setValue(BlockCog.USE_WAVEFRONT_OBJ_MODEL, true);
		BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
		IBakedModel model = dispatcher.getBlockModel(state);

		World world = tileEntityIn.getLevel();
		if (world == null)
			return;
		
		MatrixStack.Entry currentMatrix = matrixStackIn.last();
		IVertexBuilder vertexBuffer = bufferIn.getBuffer(RenderType.solid());

		for (int i = 0; i < tileEntityIn.sideExists.length; i++) {
			if (tileEntityIn.sideExists[i]) {
				readyToRender(Direction.values()[i], tileEntityIn.rotationInDegrees[i], matrixStackIn);
				
				dispatcher.getModelRenderer().renderModel(currentMatrix, vertexBuffer, null, model, 1, 1, 1,
						combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);

				endRender(Direction.values()[i], tileEntityIn.rotationInDegrees[i], matrixStackIn);
			}
		}
		matrixStackIn.popPose();
		
	}
	
	private void readyToRender(Direction direction, float rotationInDegrees, MatrixStack matrixStack) {
		//D(0, 0, 0),0 U(1, 1, 0),ZP180 N(0, 1, 0)XP-90 S(0, 0, -1)XP90 W(1, 0, 0),ZP90 E(0, 1, 0),ZP-90
		switch(direction) {
		case UP:
			matrixStack.translate(0.5, 1, 0.5);
			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(rotationInDegrees));
			break;
		case NORTH:
			matrixStack.translate(0.5, 0.5, 0);
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(90));
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(rotationInDegrees));
			break;
		case SOUTH:
			matrixStack.translate(0.5, 0.5, 1);
			matrixStack.mulPose(Vector3f.XN.rotationDegrees(90));
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(rotationInDegrees));
			break;
		case WEST:
			matrixStack.translate(0, 0.5, 0.5);
			matrixStack.mulPose(Vector3f.ZN.rotationDegrees(90));
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(rotationInDegrees));
			break;
		case EAST:
			matrixStack.translate(1, 0.5, 0.5);
			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90));
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(rotationInDegrees));
			break;
		default:
			matrixStack.translate(0.5, 0, 0.5);
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(rotationInDegrees));
			break;
		}
	}
	
	private void endRender(Direction direction, float rotationInDegrees, MatrixStack matrixStack) {
		//D(0, 0, 0),0 U(1, 1, 0),ZP180 N(0, 1, 0)XP-90 S(0, 0, -1)XP90 W(1, 0, 0),ZP90 E(0, 1, 0),ZP-90
		switch(direction) {
		case UP:
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(-rotationInDegrees));
			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-180));
			matrixStack.translate(-0.5, -1, -0.5);
			break;
		case NORTH:
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(-rotationInDegrees));
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90));
			matrixStack.translate(-0.5, -0.5, 0);
			break;
		case SOUTH:
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(-rotationInDegrees));
			matrixStack.mulPose(Vector3f.XN.rotationDegrees(-90));
			matrixStack.translate(-0.5, -0.5, -1);
			break;
		case WEST:
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(-rotationInDegrees));
			matrixStack.mulPose(Vector3f.ZN.rotationDegrees(-90));
			matrixStack.translate(0, -0.5, -0.5);
			break;
		case EAST:
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(-rotationInDegrees));
			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90));
			matrixStack.translate(-1, -0.5, -0.5);
			break;
		default:
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(-rotationInDegrees));
			matrixStack.translate(-0.5, 0, -0.5);
			break;
		}
	}
	
}
