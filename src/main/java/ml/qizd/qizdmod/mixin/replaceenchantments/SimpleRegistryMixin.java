package ml.qizd.qizdmod.mixin.replaceenchantments;

import ml.qizd.qizdmod.ReplacedEnchantments;
import ml.qizd.qizdmod.enchantment.EmptyEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleRegistry.class)
public abstract class SimpleRegistryMixin<T> {
    @Inject(method = "get(Lnet/minecraft/util/Identifier;)Ljava/lang/Object;", at = @At("HEAD"), cancellable = true)
    private void injectIntoGet(Identifier id, CallbackInfoReturnable<Enchantment> info) {
        if (ReplacedEnchantments.isEnchantmentByIdReplaced(id)) {
            info.setReturnValue(ReplacedEnchantments.getEnchantmentReplacementById(id));
        }
    }

    @Inject(method = "getId", at = @At("HEAD"), cancellable = true)
    private void injectIntoGetId(Object value, CallbackInfoReturnable<@Nullable Identifier> info) {
        if (!(value instanceof Enchantment))
            return;

        Enchantment enchantment = (Enchantment) value;
        if (ReplacedEnchantments.isEnchantmentReplaced(enchantment)) {
            info.setReturnValue(
                    Registry.ENCHANTMENT.getId(ReplacedEnchantments.getEnchantmentReplacement(enchantment)));
        }
    }

    @Inject(method = "getRawId", at = @At("HEAD"), cancellable = true)
    private void injectIntoGetRawId(Object value, CallbackInfoReturnable<Integer> info) {
        if (!(value instanceof Enchantment))
            return;

        Enchantment enchantment = (Enchantment) value;
        if (ReplacedEnchantments.isEnchantmentReplaced(enchantment)) {
            info.setReturnValue(
                    Registry.ENCHANTMENT.getRawId(ReplacedEnchantments.getEnchantmentReplacement(enchantment)));
        }
    }
}