package com.teamkuiper.redoxiation.blocks.fluids;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import com.teamkuiper.redoxiation.RedUtils;
import com.teamkuiper.redoxiation.Redoxiation;
import com.teamkuiper.redoxiation.RedoxiationItemGroups;
import com.teamkuiper.redoxiation.blocks.RedoxiationBlocks;
import com.teamkuiper.redoxiation.items.RedoxiationItems;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RedoxiationFluids {

	public static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister.create(ForgeRegistries.FLUIDS, Redoxiation.MODID);

	public static final Map<String, Consumer<FluidAttributes.Builder>> FLUID_ATTRIBUTES_CONSUMER = new HashMap<>();
	public static final Map<String, Consumer<ForgeFlowingFluid.Properties>> FLUID_PROPERTIES_CONSUMER = new HashMap<>();
	public static final Map<String, Block.Properties> FLUID_BLOCK_PROPERTIES = new HashMap<String, Block.Properties>();
	
	public static final Map<String, FluidAttributes.Builder> FLUID_ATTRIBUTES = new HashMap<>();
	public static final Map<String, ForgeFlowingFluid.Properties> FLUID_PROPERTIES = new HashMap<>();
	public static final Map<String, RegistryObject<FlowingFluid>> STILL_FLUIDS = new HashMap<>();
	public static final Map<String, RegistryObject<FlowingFluid>> FLOWING_FLUIDS = new HashMap<>();

	public static final Map<String, RegistryObject<FlowingFluidBlock>> FLUID_BLOCKS = new HashMap<>();
	public static final Map<String, RegistryObject<Item>> BUCKETS = new HashMap<>();
    
	static {
		String regName = "molten_pig_iron";
		FLUID_ATTRIBUTES_CONSUMER.put(regName, (attr) -> attr.luminosity(15).density(7874).temperature(1900).viscosity(2000));
		FLUID_PROPERTIES_CONSUMER.put(regName, (prop) -> {});
		FLUID_BLOCK_PROPERTIES.put(regName, RedUtils.createFluidBlockProperties(Material.LAVA, 100f)); //100f is a temp value
		
		
		
		
		Iterator<String> iter = FLUID_ATTRIBUTES_CONSUMER.keySet().iterator();

		while (iter.hasNext()) {
			String name = iter.next();
			
			FluidAttributes.Builder attrBuilder = FluidAttributes.builder(new ResourceLocation("blocks/" + name + "_still"), new ResourceLocation("blocks/" + name + "_flowing"));
			FLUID_ATTRIBUTES_CONSUMER.get(name).accept(attrBuilder);
			FLUID_ATTRIBUTES.put(name, attrBuilder);
			
			ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> STILL_FLUIDS.get(name).get(), () -> FLOWING_FLUIDS.get(name).get(), attrBuilder)
					.block(() -> FLUID_BLOCKS.get(name).get()).bucket(() -> BUCKETS.get(name).get());
			FLUID_PROPERTIES_CONSUMER.get(name).accept(properties);
			FLUID_PROPERTIES.put(name, properties);
			
			STILL_FLUIDS.put(name, FLUID_REGISTER.register(name + "_still", () -> new ForgeFlowingFluid.Source(properties)));
			FLOWING_FLUIDS.put(name, FLUID_REGISTER.register(name + "_flowing", () -> new ForgeFlowingFluid.Flowing(properties)));

			FLUID_BLOCKS.put(name, RedoxiationBlocks.BLOCK_REGISTER.register(name, () -> new FlowingFluidBlock(() -> STILL_FLUIDS.get(name).get(), FLUID_BLOCK_PROPERTIES.get(name))));
			BUCKETS.put(name, RedoxiationItems.ITEM_REGISTER.register(name + "_bucket", () -> new BucketItem(() -> STILL_FLUIDS.get(name).get(), new Item.Properties().stacksTo(1).tab(RedoxiationItemGroups.ITEM_GROUP))));
		}
	}
	
	public static void onClientSetup() {
		Iterator<String> iter = FLUID_ATTRIBUTES_CONSUMER.keySet().iterator();

		while (iter.hasNext()) {
			String name = iter.next();
			/*RenderTypeLookup.setRenderLayer(FLUID_BLOCKS.get(name).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(STILL_FLUIDS.get(name).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(FLOWING_FLUIDS.get(name).get(), RenderType.translucent());*/
		}
	}
}
