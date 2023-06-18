package ml.qizd.qizdmod.mixin.invisible_item_frames;

import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.apache.logging.log4j.core.util.Assert;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ItemFrameEntity.class)
public class ItemFrameEntityMixin {
    @Inject(at = @At("HEAD"), method = "interact", cancellable = true)
    private void interactMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.world.isClient)
            return;

        ItemFrameEntity e = (ItemFrameEntity)(Object) this;
        ItemStack stack = player.getStackInHand(hand);

        if (!player.isSneaking())
            return;

        if (!e.isInvisible() && stack.isOf(Items.POTION) && stack.hasNbt()) {
            String potion = Objects.requireNonNull(Assert.requireNonEmpty(stack.getNbt())).getString("Potion");
            if (!potion.equals("minecraft:invisibility") && !potion.equals("minecraft:long_invisibility"))
                return;

            e.setInvisible(true);
            cir.setReturnValue(ActionResult.SUCCESS);
        } else if (e.isInvisible() && stack.isOf(Items.MILK_BUCKET)) {
            e.setInvisible(false);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
