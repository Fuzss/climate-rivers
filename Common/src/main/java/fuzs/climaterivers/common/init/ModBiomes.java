package fuzs.climaterivers.common.init;

import fuzs.climaterivers.common.ClimateRivers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class ModBiomes {
    public static final ResourceKey<Biome> COLD_RIVER_BIOME = register("cold_river");
    public static final ResourceKey<Biome> LUKEWARM_RIVER_BIOME = register("lukewarm_river");
    public static final ResourceKey<Biome> WARM_RIVER_BIOME = register("warm_river");

    private ModBiomes() {
        // NO-OP
    }

    private static ResourceKey<Biome> register(String path) {
        return ResourceKey.create(Registries.BIOME, ClimateRivers.id(path));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatureLookup = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<WorldCarver> worldCarverLookup = context.lookup(Registries.CARVER);
        context.register(COLD_RIVER_BIOME, coldRiver(placedFeatureLookup, worldCarverLookup));
        context.register(LUKEWARM_RIVER_BIOME, lukeWarmRiver(placedFeatureLookup, worldCarverLookup));
        context.register(WARM_RIVER_BIOME, warmRiver(placedFeatureLookup, worldCarverLookup));
    }

    public static Biome coldRiver(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<WorldCarver> worldCarvers) {
        MobSpawnSettings.Builder spawns = riverSpawns();
        spawns.addSpawn(EntityTypes.SALMON, 5, 1, 5);

        BiomeGenerationSettings.Builder baseGeneration = baseRiverGeneration(placedFeatures, worldCarvers);
        addGravellySoftDisks(baseGeneration);
        BiomeDefaultFeatures.addDefaultFlowers(baseGeneration);
        BiomeDefaultFeatures.addDefaultGrass(baseGeneration);
        BiomeDefaultFeatures.addDefaultMushrooms(baseGeneration);
        BiomeDefaultFeatures.addDefaultExtraVegetation(baseGeneration, true);
        baseGeneration.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);

        return OverworldBiomes.baseBiome(0.25F, 0.8F)
                .hasPrecipitation(true)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4020182).build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(baseGeneration.build())
                .build();
    }

    public static Biome lukeWarmRiver(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<WorldCarver> worldCarvers) {
        MobSpawnSettings.Builder spawns = riverSpawns();
        spawns.addSpawn(EntityTypes.TROPICAL_FISH, 1, 1, 5);
        spawns.addSpawn(EntityTypes.COD, 4, 1, 5);

        BiomeGenerationSettings.Builder baseGeneration = baseRiverGeneration(placedFeatures, worldCarvers);
        BiomeDefaultFeatures.addDefaultSoftDisks(baseGeneration);
        BiomeDefaultFeatures.addLightBambooVegetation(baseGeneration);
        BiomeDefaultFeatures.addDefaultFlowers(baseGeneration);
        BiomeDefaultFeatures.addDefaultGrass(baseGeneration);
        baseGeneration.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
        BiomeDefaultFeatures.addDefaultMushrooms(baseGeneration);
        baseGeneration.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);

        return OverworldBiomes.baseBiome(0.95F, 0.9F)
                .hasPrecipitation(true)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4566514).build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(baseGeneration.build())
                .build();
    }

    public static Biome warmRiver(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<WorldCarver> worldCarvers) {
        MobSpawnSettings.Builder spawns = riverSpawns();
        spawns.addSpawn(EntityTypes.TROPICAL_FISH, 5, 1, 5);

        BiomeGenerationSettings.Builder baseGeneration = baseRiverGeneration(placedFeatures, worldCarvers);
        BiomeDefaultFeatures.addDefaultSoftDisks(baseGeneration);
        BiomeDefaultFeatures.addDefaultFlowers(baseGeneration);
        BiomeDefaultFeatures.addDefaultGrass(baseGeneration);
        baseGeneration.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        BiomeDefaultFeatures.addDefaultMushrooms(baseGeneration);
        baseGeneration.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);

        return OverworldBiomes.baseBiome(2.0F, 0.0F)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4445678).build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(baseGeneration.build())
                .build();
    }

    /**
     * @see OverworldBiomes#river(HolderGetter, HolderGetter, boolean)
     */
    public static MobSpawnSettings.Builder riverSpawns() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder().addSpawn(EntityTypes.SQUID, 2, 1, 4);
        BiomeDefaultFeatures.commonSpawns(builder);
        builder.addSpawn(EntityTypes.DROWNED, 100, 1, 1);
        return builder;
    }

    public static BiomeGenerationSettings.Builder baseRiverGeneration(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<WorldCarver> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        OverworldBiomes.globalOverworldGeneration(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        return builder;
    }

    /**
     * @see BiomeDefaultFeatures#addDefaultSoftDisks(BiomeGenerationSettings.Builder)
     */
    public static void addGravellySoftDisks(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.DISK_SAND_PLACED_FEATURE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.DISK_GRAVEL_PLACED_FEATURE);
    }
}
