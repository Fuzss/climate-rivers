package fuzs.climaterivers.common.init;

import dev.worldgen.lithostitched.api.registry.LithostitchedRegistries;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.BiomeInjector;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.ParameterBuilder;
import fuzs.climaterivers.common.ClimateRivers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public final class ModBiomeInjectors {
    public static final ResourceKey<BiomeInjector> COLD_RIVER = register("cold_river");
    public static final ResourceKey<BiomeInjector> WARM_RIVER = register("warm_river");
    public static final ResourceKey<BiomeInjector> DRY_WARM_RIVER = register("dry_warm_river");
    public static final ResourceKey<BiomeInjector> LUKEWARM_RIVER = register("lukewarm_river");

    private ModBiomeInjectors() {
        // NO-OP
    }

    private static ResourceKey<BiomeInjector> register(String path) {
        return ResourceKey.create(LithostitchedRegistries.BIOME_INJECTOR, ClimateRivers.id(path));
    }

    public static void bootstrap(BootstrapContext<BiomeInjector> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        context.register(COLD_RIVER,
                BiomeInjector.builder(Level.OVERWORLD)
                        .replacePartially(biomeLookup.getOrThrow(Biomes.RIVER),
                                biomeLookup.getOrThrow(ModBiomes.COLD_RIVER_BIOME),
                                ParameterBuilder.create()
                                        .climateRange(BiomeInjector.ClimateParameter.TEMPERATURE, -0.45F, -0.15F)));
        context.register(WARM_RIVER,
                BiomeInjector.builder(Level.OVERWORLD)
                        .replacePartially(biomeLookup.getOrThrow(Biomes.RIVER),
                                biomeLookup.getOrThrow(ModBiomes.WARM_RIVER_BIOME),
                                ParameterBuilder.create()
                                        .climateMin(BiomeInjector.ClimateParameter.TEMPERATURE, 0.55F)));
        context.register(DRY_WARM_RIVER,
                BiomeInjector.builder(Level.OVERWORLD)
                        .replacePartially(biomeLookup.getOrThrow(Biomes.RIVER),
                                biomeLookup.getOrThrow(ModBiomes.WARM_RIVER_BIOME),
                                ParameterBuilder.create()
                                        .climateRange(BiomeInjector.ClimateParameter.TEMPERATURE, 0.2F, 0.55F)
                                        .climateMax(BiomeInjector.ClimateParameter.HUMIDITY, -0.1F)));
        context.register(LUKEWARM_RIVER,
                BiomeInjector.builder(Level.OVERWORLD)
                        .replacePartially(biomeLookup.getOrThrow(Biomes.RIVER),
                                biomeLookup.getOrThrow(ModBiomes.LUKEWARM_RIVER_BIOME),
                                ParameterBuilder.create()
                                        .climateRange(BiomeInjector.ClimateParameter.TEMPERATURE, 0.2F, 0.55F)
                                        .climateMin(BiomeInjector.ClimateParameter.HUMIDITY, 0.1F)));
    }
}
