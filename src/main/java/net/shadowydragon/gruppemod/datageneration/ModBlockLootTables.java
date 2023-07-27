package net.shadowydragon.gruppemod.datageneration;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.block.ModBlocks;
import net.shadowydragon.gruppemod.block.ModOreBlocks;
import net.shadowydragon.gruppemod.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        ModBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> dropSelf(blockRegistryObject.get()));


        add(ModOreBlocks.SAPPHIRE_ORE.get(),
                block -> createOreDrop(ModOreBlocks.SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        add(ModOreBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
                block -> createOreDrop(ModOreBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        add(ModOreBlocks.NETHER_SAPPHIRE_ORE.get(),
                block -> createOreDrop(ModOreBlocks.NETHER_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        add(ModOreBlocks.END_STONE_SAPPHIRE_ORE.get(),
                block -> createOreDrop(ModOreBlocks.END_STONE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> toReturn = new ArrayList<>();

        ModBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> toReturn.add(blockRegistryObject.get()));

        ModOreBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> toReturn.add(blockRegistryObject.get()));

        //toReturn. ModOreBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;


        return toReturn; //ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
