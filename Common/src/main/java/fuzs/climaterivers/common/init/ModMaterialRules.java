package fuzs.climaterivers.common.init;

import fuzs.climaterivers.common.ClimateRivers;
import fuzs.climaterivers.common.handler.SurfaceRuleBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;

public final class ModMaterialRules {
    public static final ResourceKey<MaterialRule> RIVER_SURFACE = register("river_surface");
    public static final ResourceKey<MaterialRule> RIVER_BIOME_TOP = register("river/biome_top");
    /**
     * @see net.minecraft.data.worldgen.material.OverworldMaterialRules#SURFACE
     */
    public static final ResourceKey<MaterialRule> SURFACE = registerVanilla("overworld/surface");
    /**
     * @see net.minecraft.data.worldgen.material.OverworldMaterialRules#SAND_OR_SANDSTONE_IF_CEILING
     */
    public static final ResourceKey<MaterialRule> SAND_OR_SANDSTONE_IF_CEILING = registerVanilla(
            "overworld/sand_or_sandstone_if_ceiling");
    /**
     * @see net.minecraft.data.worldgen.material.OverworldMaterialRules#GRAVEL_OR_STONE_IF_CEILING
     */
    public static final ResourceKey<MaterialRule> GRAVEL_OR_STONE_IF_CEILING = registerVanilla(
            "overworld/gravel_or_stone_if_ceiling");

    private ModMaterialRules() {
        // NO-OP
    }

    private static ResourceKey<MaterialRule> register(String path) {
        return ResourceKey.create(Registries.MATERIAL_RULE, ClimateRivers.id(path));
    }

    private static ResourceKey<MaterialRule> registerVanilla(String path) {
        return ResourceKey.create(Registries.MATERIAL_RULE, Identifier.withDefaultNamespace(path));
    }

    public static void bootstrap(BootstrapContext<MaterialRule> context) {
        context.register(RIVER_SURFACE, SurfaceRuleBuilder.overworldLike(context));
    }
}
