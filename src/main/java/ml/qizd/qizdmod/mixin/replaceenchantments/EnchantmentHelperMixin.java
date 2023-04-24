package ml.qizd.qizdmod.mixin.replaceenchantments;

import ml.qizd.qizdmod.ReplacedEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @ModifyVariable(at = @At("HEAD"), method = "set", argsOnly = true)
    private static Map<Enchantment, Integer> removeDisabledEnchantmentsFromSet(Map<Enchantment, Integer> value, Map<Enchantment, Integer> map, ItemStack stack) {
        HashMap<Enchantment, Integer> newValue = new LinkedHashMap<>();
        for (Map.Entry<Enchantment, Integer> entry : value.entrySet()) {
            if (ReplacedEnchantments.isEnchantmentReplaced(entry.getKey())) {
                newValue.put(
                        ReplacedEnchantments.getEnchantmentReplacement(entry.getKey()),
                        entry.getValue()
                );
            } else {
                newValue.put(entry.getKey(), entry.getValue());
            }
        }

        return newValue;
    }
}
