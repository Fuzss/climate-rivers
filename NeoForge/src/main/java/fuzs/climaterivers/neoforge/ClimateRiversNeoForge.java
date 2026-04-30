package fuzs.climaterivers.neoforge;

import fuzs.climaterivers.common.ClimateRivers;
import fuzs.climaterivers.common.data.tags.ModBiomeTagsProvider;
import fuzs.climaterivers.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(ClimateRivers.MOD_ID)
public class ClimateRiversNeoForge {

    public ClimateRiversNeoForge() {
        ModConstructor.construct(ClimateRivers.MOD_ID, ClimateRivers::new);
        DataProviderHelper.registerDataProviders(ClimateRivers.MOD_ID,
                ModRegistry.REGISTRY_SET_BUILDER,
                ModBiomeTagsProvider::new);
    }
}
