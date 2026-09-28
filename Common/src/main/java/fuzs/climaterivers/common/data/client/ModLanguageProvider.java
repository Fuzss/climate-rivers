package fuzs.climaterivers.common.data.client;

import fuzs.climaterivers.common.init.ModBiomes;
import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.addBiome(ModBiomes.COLD_RIVER_BIOME, "Cold River");
        this.addBiome(ModBiomes.LUKEWARM_RIVER_BIOME, "Lukewarm River");
        this.addBiome(ModBiomes.WARM_RIVER_BIOME, "Warm River");
    }
}
