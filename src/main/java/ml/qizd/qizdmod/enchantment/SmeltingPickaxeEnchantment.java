package ml.qizd.qizdmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SmeltingPickaxeEnchantment extends Enchantment {
    private static final EquipmentSlot[] slots = { EquipmentSlot.MAINHAND };
    public SmeltingPickaxeEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, slots);
    }
}
