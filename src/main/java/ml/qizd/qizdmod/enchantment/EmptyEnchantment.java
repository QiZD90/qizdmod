package ml.qizd.qizdmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EmptyEnchantment extends Enchantment {
    private static final EquipmentSlot[] slots = {};
    public EmptyEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.TRIDENT, slots);
    }

    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }
    public boolean isAvailableForRandomSelection() {
        return false;
    }
}
