package net.shadowydragon.gruppemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.block.*;

public class ModCrativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GruppeMod.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> MOD_MATERIAL_TAB =
            CREATIVE_MODE_TABS.register("mod_material_tab",
                    ()->CreativeModeTab
                            .builder()
                            .icon(() -> new ItemStack(ModItems.RAW_SAPPHIRE.get()))
                            .title(Component.translatable("creativetab.mod_material_tab"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItems.RAW_SAPPHIRE.get());
                                pOutput.accept(ModItems.SAPPHIRE.get());

                            })
                            .build());

    public static final RegistryObject<CreativeModeTab> MOD_USEABLE_ITEMS_TAB = CREATIVE_MODE_TABS.register("mod_useable_items_tab",
            () -> CreativeModeTab
                    .builder()
                    .title(Component.translatable("creativetab.mod_useable_items"))
                    .icon(() -> new ItemStack(ModUseableItems.METAL_DETECTOR.get()))
                    .displayItems((pIn, pOut) ->
                            ModUseableItems.ITEMS.getEntries().forEach(itemRegistryObject -> pOut.accept(itemRegistryObject.get())))
                    .build());

    public static final RegistryObject<CreativeModeTab> MOD_BLOCK_TAB =
            CREATIVE_MODE_TABS.register("mod_block_tab",
                    ()->CreativeModeTab
                            .builder()
                            .icon(() -> new ItemStack(ModBlocks.SAPPHIRE_BLOCK.get()))
                            .title(Component.translatable("creativetab.mod_block_tab"))
                            .displayItems((pParameters, pOutput) -> {

                                ModBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> pOutput.accept(blockRegistryObject.get()));
                                ModCustomModelBlocks.BLOCKS.getEntries().stream().forEach(blockRegistryObject -> pOutput.accept(blockRegistryObject.get()));
                            })
                            .build());

    public static final RegistryObject<CreativeModeTab> MOD_FOOD_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("mod_food_items_tab",
                    ()->CreativeModeTab
                            .builder()
                            .icon(() -> new ItemStack(ModFoodItems.STRAWBERRY.get()))
                            .title(Component.translatable("creativetab.mod_food_items_tab"))
                            .displayItems((pParameters, pOutput) -> {

                                ModFoodItems.FOODS.getEntries().forEach(itemRegistryObject -> pOutput.accept(itemRegistryObject.get()));
                            })
                            .build());

    public static final RegistryObject<CreativeModeTab> MOD_TREE_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("mod_tree_items_tab",
                    ()->CreativeModeTab
                            .builder()
                            .icon(() -> new ItemStack(ModLeaveBlocks.EBONY_LEAVES.get()))
                            .title(Component.translatable("creativetab.mod_tree_items_tab"))
                            .displayItems((pParameters, pOutput) -> {

                                ModLogBlocks.LOGS.getEntries().forEach(itemRegistryObject -> pOutput.accept(itemRegistryObject.get()));
                                ModLeaveBlocks.LEAVES.getEntries().forEach(blockRegistryObject -> pOutput.accept(blockRegistryObject.get()));
                                ModSaplingBlocks.SAPLINGS.getEntries().forEach(blockRegistryObject -> pOutput.accept(blockRegistryObject.get()));
                            })
                            .build());
}
