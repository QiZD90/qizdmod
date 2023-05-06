package datagen;

import ml.qizd.qizdmod.ItemRegistrar;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class CraftingRecipeGenerator extends FabricRecipeProvider {
    public CraftingRecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        for (int i = 1; i < ItemRegistrar.MINECOINS_TYPES_COUNT; i++) {
            int low = (int) Math.pow(8, i - 1), high = (int) Math.pow(8, i);

            ShapedRecipeJsonBuilder
                    .create(ItemRegistrar.MINECOINS[i], 1)
                    .pattern("CCC")
                    .pattern("C C")
                    .pattern("CCC")
                    .input('C', ItemRegistrar.MINECOINS[i - 1])
                    .criterion("has_coin", RecipeProvider.conditionsFromItem(ItemRegistrar.MINECOINS[i]))
                    .offerTo(exporter, String.format("minecoin_%d_to_%d", low, high));

            ShapelessRecipeJsonBuilder
                    .create(ItemRegistrar.MINECOINS[i - 1], 8)
                    .input(ItemRegistrar.MINECOINS[i])
                    .criterion("has_coin", RecipeProvider.conditionsFromItem(ItemRegistrar.MINECOINS[i]))
                    .offerTo(exporter, String.format("minecoin_%d_to_%d", high, low));
        }
    }
}
