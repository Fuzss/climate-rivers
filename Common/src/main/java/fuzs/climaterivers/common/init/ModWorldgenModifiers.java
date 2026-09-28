package fuzs.climaterivers.common.init;

import dev.worldgen.lithostitched.api.registry.LithostitchedRegistries;
import dev.worldgen.lithostitched.api.worldgen.modifier.WorldgenModifier;
import fuzs.climaterivers.common.ClimateRivers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;

public final class ModWorldgenModifiers {
    public static final ResourceKey<WorldgenModifier> SET_RIVER_SURFACE = register("set_river_surface");

    private ModWorldgenModifiers() {
        // NO-OP
    }

    private static ResourceKey<WorldgenModifier> register(String path) {
        return ResourceKey.create(LithostitchedRegistries.WORLDGEN_MODIFIER, ClimateRivers.id(path));
    }

    public static void bootstrap(BootstrapContext<WorldgenModifier> context) {
        HolderGetter<MaterialRule> rules = context.lookup(Registries.MATERIAL_RULE);
        Holder<MaterialRule> surface = rules.getOrThrow(ModMaterialRules.SURFACE);
        Holder<MaterialRule> riverSurface = rules.getOrThrow(ModMaterialRules.RIVER_SURFACE);
        context.register(SET_RIVER_SURFACE,
                WorldgenModifier.builder().setMaterialRule(surface, riverSurface)); // PREPEND is the default
    }
}
