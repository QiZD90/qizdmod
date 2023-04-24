package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.enchantment.EmptyEnchantment;
import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.enchantment.SmeltingPickaxeEnchantment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Qizdmod implements ModInitializer {
    public static String MOD_ID = "qizdmod";
    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "bottle_block"), BottleBlock.BOTTLE_BLOCK);
        Registry.register(
                Registry.ITEM, new Identifier(MOD_ID, "bottle_block"),
                new BlockItem(BottleBlock.BOTTLE_BLOCK,
                        new Item.Settings()
                                .group(ItemGroup.DECORATIONS)
                                .maxCount(16)
                )
        );

        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "empty_enchantment"),
                EmptyEnchantment.EMPTY_ENCHANTMENT);

        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "intensive_training"),
                IntensiveTrainingEnchantment.INTENSIVE_TRAINING);

        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "smelting_pickaxe"),
                SmeltingPickaxeEnchantment.SMELTING_PICKAXE);
    }
}
