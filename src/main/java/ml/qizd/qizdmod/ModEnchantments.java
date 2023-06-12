package ml.qizd.qizdmod;

import ml.qizd.qizdmod.enchantment.EmptyEnchantment;
import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.enchantment.SmeltingPickaxeEnchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchantments {
    public static void register() {
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
