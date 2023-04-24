package ml.qizd.qizdmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SmeltingPickaxeEnchantment extends Enchantment {
    public static final SmeltingPickaxeEnchantment SMELTING_PICKAXE = new SmeltingPickaxeEnchantment();

    public static final Enchantment INTENSIVE_TRAINING = new IntensiveTrainingEnchantment();

    private static EquipmentSlot[] slots = {EquipmentSlot.MAINHAND};
    protected SmeltingPickaxeEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, slots);
    }
}
