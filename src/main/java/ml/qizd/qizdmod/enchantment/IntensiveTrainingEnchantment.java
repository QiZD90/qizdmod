package ml.qizd.qizdmod.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class IntensiveTrainingEnchantment extends LuckEnchantment {
    private static final EquipmentSlot[] slots = { EquipmentSlot.MAINHAND };
    public IntensiveTrainingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.FISHING_ROD, slots);
    }
}
