package ml.qizd.qizdmod.mixin.eggs_now_stack;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Items.class)
public class ItemsMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/EggItem;<init>(Lnet/minecraft/item/Item$Settings;)V"), method = "<clinit>")
    private static Item.Settings registerMixin(Item.Settings settings) {
        return settings.maxCount(64);
    }
}
