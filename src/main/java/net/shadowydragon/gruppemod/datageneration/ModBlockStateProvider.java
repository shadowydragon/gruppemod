package net.shadowydragon.gruppemod.datageneration;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.block.ModBlocks;
import net.shadowydragon.gruppemod.block.ModOreBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, GruppeMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //ModBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> blockWithItem(blockRegistryObject));

        ModBlocks.BLOCKS.getEntries().forEach(this::blockWithItem);
        ModOreBlocks.BLOCKS.getEntries().forEach(this::blockWithItem);

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject)
    {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
