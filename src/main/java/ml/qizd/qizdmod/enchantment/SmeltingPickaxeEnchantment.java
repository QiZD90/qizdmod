package ml.qizd.qizdmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SmeltingPickaxeEnchantment extends Enchantment {
    public static final Enchantment SMELTING_PICKAXE = new SmeltingPickaxeEnchantment();

    private static EquipmentSlot[] slots = {EquipmentSlot.MAINHAND};
    protected SmeltingPickaxeEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, slots);
    }
}
