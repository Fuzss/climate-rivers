package fuzs.climaterivers.common.init;

import fuzs.climaterivers.common.ClimateRivers;
import fuzs.climaterivers.common.world.level.levelgen.LegacyBiomeConditionSource;
import fuzs.puzzleslib.common.api.init.v3.registry.RegistryManager;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class ModRegistry {
    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder().add(Registries.PLACED_FEATURE,
            ModPlacedFeatures::bootstrap).add(Registries.BIOME, ModBiomes::bootstrap);
    static final RegistryManager REGISTRIES = RegistryManager.from(ClimateRivers.MOD_ID);

    public static void bootstrap() {
        REGISTRIES.register(Registries.MATERIAL_CONDITION, "legacy_biome", () -> LegacyBiomeConditionSource.CODEC);
    }
}
