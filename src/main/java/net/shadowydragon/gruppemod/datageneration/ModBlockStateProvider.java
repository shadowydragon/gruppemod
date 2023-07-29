package net.shadowydragon.gruppemod.datageneration;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.block.*;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, GruppeMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //ModBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> blockWithItem(blockRegistryObject));

        ModBlocks.BLOCKS.getEntries().forEach(this::blockWithItem);
        ModOreBlocks.BLOCKS.getEntries().forEach(this::blockWithItem);

        //ModLogBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> logBlock((RotatedPillarBlock) blockRegistryObject.get()));
        logBlock((RotatedPillarBlock) ModLogBlocks.EBONY_LOG.get());
        axisBlock((RotatedPillarBlock) ModLogBlocks.EBONY_WOOD.get(), blockTexture(ModLogBlocks.EBONY_LOG.get()), blockTexture(ModLogBlocks.EBONY_LOG.get()));

        axisBlock((RotatedPillarBlock) ModLogBlocks.STRIPPED_EBONY_LOG.get(), new ResourceLocation(GruppeMod.MOD_ID, "block/stripped_ebony_log"),
                new ResourceLocation(GruppeMod.MOD_ID, "block/stripped_ebony_log_top"));
        axisBlock((RotatedPillarBlock) ModLogBlocks.STRIPPEDE_EBONY_WOOD.get(), new ResourceLocation(GruppeMod.MOD_ID, "block/stripped_ebony_log"),
                new ResourceLocation(GruppeMod.MOD_ID, "block/stripped_ebony_log"));

        ModLeaveBlocks.LEAVES.getEntries().forEach(this::blockWithItem);

        simpleBlockItem(ModLogBlocks.EBONY_LOG.get(), models().withExistingParent("gruppemod:ebony_log", "minecraft:block/cube_column"));
        simpleBlockItem(ModLogBlocks.EBONY_WOOD.get(), models().withExistingParent("gruppemod:ebony_wood", "minecraft:block/cube_column"));
        simpleBlockItem(ModLogBlocks.STRIPPED_EBONY_LOG.get(), models().withExistingParent("gruppemod:stripped_ebony_log", "minecraft:block/cube_column"));
        simpleBlockItem(ModLogBlocks.STRIPPEDE_EBONY_WOOD.get(), models().withExistingParent("gruppemod:stripped_ebony_wood", "minecraft:block/cube_column"));

        ModSaplingBlocks.SAPLINGS.getEntries().forEach(this::saplingBlock);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject)
    {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject)
    {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),
                        blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
}
