package fuzs.climaterivers.common.init;

import fuzs.climaterivers.common.ClimateRivers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

public final class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DISK_GRAVEL_PLACED_FEATURE = register("disk_gravel");
    public static final ResourceKey<PlacedFeature> DISK_SAND_PLACED_FEATURE = register("disk_sand");

    private ModPlacedFeatures() {
        // NO-OP
    }

    private static ResourceKey<PlacedFeature> register(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ClimateRivers.id(path));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> holderGetter = context.lookup(Registries.FEATURE);
        Holder<Feature> gravelDisk = holderGetter.getOrThrow(MiscOverworldFeatures.DISK_GRAVEL);
        Holder<Feature> sandDisk = holderGetter.getOrThrow(MiscOverworldFeatures.DISK_SAND);
        PlacementUtils.register(context,
                DISK_GRAVEL_PLACED_FEATURE,
                gravelDisk,
                CountPlacement.of(9),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
                BiomeFilter.biome());
        PlacementUtils.register(context,
                DISK_SAND_PLACED_FEATURE,
                sandDisk,
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
                BiomeFilter.biome());
    }
}
