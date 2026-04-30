plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-common")
}

dependencies {
    modCompileOnlyApi(sharedLibs.puzzleslib.common)
    modCompileOnlyApi(sharedLibs.biolith.common)
}

multiloader {
    mixins {
        mixin("BiomeDataMixin", "OverworldBiomeBuilderMixin", "PlacementUtilsMixin")
    }
}
