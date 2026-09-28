package fuzs.climaterivers.neoforge;

import dev.worldgen.lithostitched.api.registry.LithostitchedRegistries;
import fuzs.climaterivers.common.ClimateRivers;
import fuzs.climaterivers.common.data.tags.ModBiomeTagsProvider;
import fuzs.climaterivers.common.init.ModBiomes;
import fuzs.climaterivers.common.init.ModMaterialRules;
import fuzs.climaterivers.common.init.ModPlacedFeatures;
import fuzs.climaterivers.common.init.ModWorldgenModifiers;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v3.core.DataProviderBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.fml.common.Mod;

@Mod(ClimateRivers.MOD_ID)
public class ClimateRiversNeoForge {

    public ClimateRiversNeoForge() {
        ModConstructor.construct(ClimateRivers.MOD_ID, ClimateRivers::new);
        DataProviderBuilder.of(ClimateRivers.MOD_ID)
                .addWorldBootstrap(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
                .addWorldBootstrap(Registries.BIOME, ModBiomes::bootstrap)
                .addWorldBootstrap(Registries.MATERIAL_RULE, ModMaterialRules::bootstrap)
                .addWorldBootstrap(LithostitchedRegistries.WORLDGEN_MODIFIER, ModWorldgenModifiers::bootstrap)
                .addProvider(ModBiomeTagsProvider::new);
    }
}
