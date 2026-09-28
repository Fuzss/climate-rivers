package fuzs.climaterivers.common.handler;

import fuzs.climaterivers.common.init.ModBiomes;
import fuzs.climaterivers.common.init.ModMaterialRules;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.material.VanillaMaterialConditions;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;

public class SurfaceRuleBuilder {
    private static final MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRule SANDSTONE = makeStateRule(Blocks.SANDSTONE);

    /**
     * @see net.minecraft.data.worldgen.material.OverworldMaterialRules#makeStateRule(Block)
     */
    private static MaterialRule makeStateRule(Block block) {
        return MaterialRules.state(block.defaultBlockState());
    }

    public static MaterialRule overworldLike(BootstrapContext<MaterialRule> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<MaterialCondition> conditions = context.lookup(Registries.MATERIAL_CONDITION);
        HolderGetter<MaterialRule> rules = context.lookup(Registries.MATERIAL_RULE);

        // 26.3 named conditions (no vanilla constant exists for waterBlockCheck(0, 0)).
        MaterialCondition atWaterSurface = MaterialRules.waterBlockCheck(0, 0);
        MaterialCondition onFloor = MaterialRules.getCondition(conditions, VanillaMaterialConditions.ON_FLOOR);
        MaterialCondition underFloor = MaterialRules.getCondition(conditions, VanillaMaterialConditions.UNDER_FLOOR);
        MaterialCondition deepUnderFloor = MaterialRules.getCondition(conditions,
                VanillaMaterialConditions.DEEP_UNDER_FLOOR);
        MaterialCondition notUnderwater = MaterialRules.getCondition(conditions,
                VanillaMaterialConditions.NOT_UNDERWATER);
        MaterialCondition notUnderDeepWater = MaterialRules.getCondition(conditions,
                VanillaMaterialConditions.NOT_UNDER_DEEP_WATER);

        MaterialCondition isWarm = MaterialRules.isBiome(biomes, ModBiomes.WARM_RIVER_BIOME);
        MaterialCondition isCold = MaterialRules.isBiome(biomes, ModBiomes.COLD_RIVER_BIOME);
        MaterialCondition isWarmOrLukewarm = MaterialRules.isBiome(biomes,
                ModBiomes.WARM_RIVER_BIOME,
                ModBiomes.LUKEWARM_RIVER_BIOME);
        MaterialCondition isRiver = MaterialRules.isBiome(biomes,
                ModBiomes.COLD_RIVER_BIOME,
                ModBiomes.LUKEWARM_RIVER_BIOME,
                ModBiomes.WARM_RIVER_BIOME);

        // Reuse vanilla registered rules instead of redefining them.
        MaterialRule sandOrSandstone = MaterialRules.getRule(rules, ModMaterialRules.SAND_OR_SANDSTONE_IF_CEILING);
        MaterialRule gravelOrStone = MaterialRules.getRule(rules, ModMaterialRules.GRAVEL_OR_STONE_IF_CEILING);

        // Shared sub-rule (used twice) registered as a named entry.
        MaterialRule biomeTop = MaterialRules.registerAndWrap(context,
                ModMaterialRules.RIVER_BIOME_TOP,
                MaterialRules.sequence(MaterialRules.ifTrue(isWarm, sandOrSandstone),
                        MaterialRules.ifTrue(isCold, gravelOrStone)));

        MaterialRule grassOrDirt = MaterialRules.sequence(MaterialRules.ifTrue(atWaterSurface, GRASS_BLOCK), DIRT);
        MaterialRule underwaterUnder = MaterialRules.sequence(MaterialRules.ifTrue(atWaterSurface, biomeTop), DIRT);
        MaterialRule surfaceTop = MaterialRules.sequence(biomeTop, grassOrDirt);

        // Branch order is behavior-critical: UNDER_FLOOR/DEEP_UNDER_FLOOR are supersets of ON_FLOOR.
        MaterialRule riverSurface = MaterialRules.sequence(MaterialRules.ifTrue(onFloor,
                        MaterialRules.ifTrue(notUnderwater, surfaceTop)),
                MaterialRules.ifTrue(notUnderDeepWater,
                        MaterialRules.sequence(MaterialRules.ifTrue(underFloor, underwaterUnder),
                                MaterialRules.ifTrue(isWarm, MaterialRules.ifTrue(deepUnderFloor, SANDSTONE)))),
                MaterialRules.ifTrue(onFloor,
                        MaterialRules.sequence(MaterialRules.ifTrue(isWarmOrLukewarm, sandOrSandstone),
                                gravelOrStone)));

        return MaterialRules.ifTrue(MaterialRules.abovePreliminarySurface(),
                MaterialRules.ifTrue(isRiver, riverSurface));
    }
}
