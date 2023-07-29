package net.shadowydragon.gruppemod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.shadowydragon.gruppemod.block.*;
import net.shadowydragon.gruppemod.block.entity.ModBlockEntities;
import net.shadowydragon.gruppemod.gui.ModMenuTypes;
import net.shadowydragon.gruppemod.gui.geminfusingstation.GemInfusingStationScreen;
import net.shadowydragon.gruppemod.item.ModCrativeTabs;
import net.shadowydragon.gruppemod.item.ModFoodItems;
import net.shadowydragon.gruppemod.item.ModItems;
import net.shadowydragon.gruppemod.item.ModUseableItems;
import net.shadowydragon.gruppemod.networking.ModMessages;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GruppeMod.MODID)
public class GruppeMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "gruppemod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public GruppeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCrativeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModUseableItems.register(modEventBus);
        ModFoodItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModOreBlocks.register(modEventBus);
        ModLogBlocks.register(modEventBus);
        ModLeaveBlocks.register(modEventBus);
        ModCustomModelBlocks.register(modEventBus);
        ModSaplingBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        ModMessages.register();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            //event.accept(ModItems.EXAMPLE_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code

            MenuScreens.register(ModMenuTypes.GEM_INFUSING_STATION_MENU.get(), GemInfusingStationScreen::new);

        }
    }
}
