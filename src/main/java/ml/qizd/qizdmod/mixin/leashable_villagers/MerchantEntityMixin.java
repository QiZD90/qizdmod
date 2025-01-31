package ml.qizd.qizdmod.mixin.leashable_villagers;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantEntity.class)
public abstract class MerchantEntityMixin {
    @Inject(at = @At("HEAD"), method = "canBeLeashedBy(Lnet/minecraft/entity/player/PlayerEntity;)Z", cancellable = true)
    public void canBeLeashedByMixin(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }
}
