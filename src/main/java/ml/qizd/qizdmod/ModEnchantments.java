package ml.qizd.qizdmod;

import ml.qizd.qizdmod.enchantment.EmptyEnchantment;
import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.enchantment.SmeltingPickaxeEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchantments {
    public static final Enchantment EMPTY_ENCHANTMENT = new EmptyEnchantment();
    public static final Enchantment INTENSIVE_TRAINING = new IntensiveTrainingEnchantment();
    public static final Enchantment SMELTING_PICKAXE = new SmeltingPickaxeEnchantment();


    public static void register() {
        Registry.register(Registry.ENCHANTMENT, new Identifier("qizdmod", "empty_enchantment"), EMPTY_ENCHANTMENT);
        Registry.register(Registry.ENCHANTMENT, new Identifier("qizdmod", "intensive_training"), INTENSIVE_TRAINING);
        Registry.register(Registry.ENCHANTMENT, new Identifier("qizdmod", "smelting_pickaxe"), SMELTING_PICKAXE);
    }
}
