package net.shadowydragon.gruppemod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.block.ModLeaveBlocks;
import net.shadowydragon.gruppemod.block.ModLogBlocks;
import net.shadowydragon.gruppemod.block.ModOreBlocks;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY_KEY = registerKey("ebony");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWOLD_SAPPHIRE_ORE_KEY = registerKey("overworld_sapphire_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SAPPHIRE_ORE_KEY = registerKey("nether_sapphire_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_SAPPHIRE_ORE_KEY = registerKey("end_sapphire_ore_key");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endstoneReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overwoldSapphireOres = List.of(OreConfiguration.target(stoneReplaceables, ModOreBlocks.SAPPHIRE_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables, ModOreBlocks.DEEPSLATE_SAPPHIRE_ORE.get().defaultBlockState()));

        register(context, EBONY_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModLogBlocks.EBONY_LOG.get()), new StraightTrunkPlacer(5, 6, 3), BlockStateProvider.simple(ModLeaveBlocks.EBONY_LEAVES.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4), new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, OVERWOLD_SAPPHIRE_ORE_KEY, Feature.ORE, new OreConfiguration(overwoldSapphireOres, //replaceable blocks
                9//Veinsize
        ));

        register(context, END_SAPPHIRE_ORE_KEY, Feature.ORE, new OreConfiguration(endstoneReplaceables, //replaceable block
                ModOreBlocks.END_STONE_SAPPHIRE_ORE.get().defaultBlockState(), //Replaceblock
                9));

        register(context, NETHER_SAPPHIRE_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplaceables, //replaceable block
                ModOreBlocks.NETHER_SAPPHIRE_ORE.get().defaultBlockState(), //Replaceblock
                11));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(GruppeMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));

    }
}
