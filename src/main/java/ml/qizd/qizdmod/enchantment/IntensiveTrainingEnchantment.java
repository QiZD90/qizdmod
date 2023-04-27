package ml.qizd.qizdmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class IntensiveTrainingEnchantment extends LuckEnchantment {
    public static final Enchantment INTENSIVE_TRAINING = new IntensiveTrainingEnchantment();

    private static EquipmentSlot[] slots = {EquipmentSlot.MAINHAND};
    protected IntensiveTrainingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.FISHING_ROD, slots);
    }
}
