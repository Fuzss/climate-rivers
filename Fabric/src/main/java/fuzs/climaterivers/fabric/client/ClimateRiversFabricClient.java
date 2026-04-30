package fuzs.climaterivers.fabric.client;

import fuzs.climaterivers.common.ClimateRivers;
import fuzs.climaterivers.common.client.ClimateRiversClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ClimateRiversFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ClimateRivers.MOD_ID, ClimateRiversClient::new);
    }
}
