package ml.qizd.qizdmod.mixin.invisible_item_frames;

import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameEntity.class)
public class ItemFrameEntityMixin {
    @Inject(at = @At("HEAD"), method = "interact", cancellable = true)
    private void interactMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.isSneaking()) {
            ((ItemFrameEntity)(Object) this).setInvisible(!((ItemFrameEntity)(Object) this).isInvisible());
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
