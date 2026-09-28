package fuzs.climaterivers.common;

import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClimateRivers implements ModConstructor {
    public static final String MOD_ID = "climaterivers";
    public static final String MOD_NAME = "Climate Rivers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
