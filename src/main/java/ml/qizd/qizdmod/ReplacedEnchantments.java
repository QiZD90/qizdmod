package ml.qizd.qizdmod;

import ml.qizd.qizdmod.enchantment.EmptyEnchantment;
import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.enchantment.SmeltingPickaxeEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReplacedEnchantments {
    public static boolean isEnchantmentReplaced(Enchantment enchantment) {
        return enchantment == Enchantments.LUCK_OF_THE_SEA || enchantment == Enchantments.FORTUNE;
    }

    public static boolean isEnchantmentByIdReplaced(Identifier identifier) {
        return identifier.getPath().equals("fortune") || identifier.getPath().equals("luck_of_the_sea");
    }

    public static Enchantment getEnchantmentReplacement(Enchantment enchantment) {
        if (enchantment == Enchantments.LUCK_OF_THE_SEA)
            return IntensiveTrainingEnchantment.INTENSIVE_TRAINING;

        if (enchantment == Enchantments.FORTUNE)
            return SmeltingPickaxeEnchantment.SMELTING_PICKAXE;
        
        return EmptyEnchantment.EMPTY_ENCHANTMENT;
    }
    public static Enchantment getEnchantmentReplacementById(Identifier identifier) {
        if (identifier.getPath().equals("luck_of_the_sea"))
            return IntensiveTrainingEnchantment.INTENSIVE_TRAINING;

        if (identifier.getPath().equals("fortune"))
            return SmeltingPickaxeEnchantment.SMELTING_PICKAXE;

        return EmptyEnchantment.EMPTY_ENCHANTMENT;
    }
}
