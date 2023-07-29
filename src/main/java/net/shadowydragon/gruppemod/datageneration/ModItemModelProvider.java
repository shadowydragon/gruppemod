package net.shadowydragon.gruppemod.datageneration;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.block.ModSaplingBlocks;
import net.shadowydragon.gruppemod.item.ModFoodItems;
import net.shadowydragon.gruppemod.item.ModItems;
import net.shadowydragon.gruppemod.item.ModUseableItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GruppeMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> simpleItem(itemRegistryObject));
        //ModItems.ITEMS.getEntries().forEach(this::simpleItem);

        simpleItem(ModItems.EXAMPLE_ITEM);
        simpleItem(ModItems.SAPPHIRE);
        simpleItem(ModItems.RAW_SAPPHIRE);

        simpleItem(ModFoodItems.STRAWBERRY);

        handheldItem(ModUseableItems.METAL_DETECTOR);

        ModSaplingBlocks.SAPLINGS.getEntries().forEach(this::saplingItem);

    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(GruppeMod.MOD_ID, "block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(GruppeMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld")).texture("layer0", new ResourceLocation(GruppeMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
