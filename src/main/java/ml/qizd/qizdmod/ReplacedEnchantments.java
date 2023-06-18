package ml.qizd.qizdmod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.Identifier;

public class ReplacedEnchantments {
    public static boolean isEnchantmentReplaced(Enchantment enchantment) {
        return enchantment == Enchantments.LUCK_OF_THE_SEA || enchantment == Enchantments.FORTUNE;
    }

    public static boolean isEnchantmentByIdReplaced(Identifier identifier) {
        return identifier.getPath().equals("fortune") || identifier.getPath().equals("luck_of_the_sea");
    }

    public static Enchantment getEnchantmentReplacement(Enchantment enchantment) {
        if (enchantment == Enchantments.LUCK_OF_THE_SEA)
            return ModEnchantments.INTENSIVE_TRAINING;

        if (enchantment == Enchantments.FORTUNE)
            return ModEnchantments.SMELTING_PICKAXE;
        
        return ModEnchantments.EMPTY_ENCHANTMENT;
    }
    public static Enchantment getEnchantmentReplacementById(Identifier identifier) {
        if (identifier.getPath().equals("luck_of_the_sea"))
            return ModEnchantments.INTENSIVE_TRAINING;

        if (identifier.getPath().equals("fortune"))
            return ModEnchantments.SMELTING_PICKAXE;

        return ModEnchantments.EMPTY_ENCHANTMENT;
    }
}
