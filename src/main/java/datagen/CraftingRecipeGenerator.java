package datagen;

import ml.qizd.qizdmod.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;

import java.util.function.Consumer;

public class CraftingRecipeGenerator extends FabricRecipeProvider {
    public CraftingRecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        for (int i = 1; i < ModItems.MINECOINS_TYPES_COUNT; i++) {
            int low = (int) Math.pow(8, i - 1), high = (int) Math.pow(8, i);

            ShapedRecipeJsonBuilder
                    .create(ModItems.MINECOINS[i], 1)
                    .pattern("CCC")
                    .pattern("C C")
                    .pattern("CCC")
                    .input('C', ModItems.MINECOINS[i - 1])
                    .criterion("has_coin", RecipeProvider.conditionsFromItem(ModItems.MINECOINS[i]))
                    .offerTo(exporter, String.format("minecoin_%d_to_%d", low, high));

            ShapelessRecipeJsonBuilder
                    .create(ModItems.MINECOINS[i - 1], 8)
                    .input(ModItems.MINECOINS[i])
                    .criterion("has_coin", RecipeProvider.conditionsFromItem(ModItems.MINECOINS[i]))
                    .offerTo(exporter, String.format("minecoin_%d_to_%d", high, low));
        }
    }
}
