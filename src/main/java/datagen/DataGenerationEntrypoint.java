package datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerationEntrypoint  implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(CraftingRecipeGenerator::new);
        fabricDataGenerator.addProvider(ModelProvider::new);
        fabricDataGenerator.addProvider(AdvancementGenerator::new);
    }
}
