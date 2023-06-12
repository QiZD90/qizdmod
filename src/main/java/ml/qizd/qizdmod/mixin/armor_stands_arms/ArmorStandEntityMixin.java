package ml.qizd.qizdmod.mixin.armor_stands_arms;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntityMixin {
    @Shadow public abstract boolean shouldShowArms();

    @Shadow protected abstract void setShowArms(boolean showArms);

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    @Inject(at = @At("HEAD"), method = "interactAt", cancellable = true)
    private void interactAtMixin(PlayerEntity player, Vec3d hitPos, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (!shouldShowArms() && stack.isOf(Items.STICK) && stack.getCount() >= 2 && player.isSneaking()) {
            this.setShowArms(true);
            stack.decrement(2);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Inject(at = @At("HEAD"), method = "handleAttack", cancellable = true)
    private void handleAttackMixin(Entity attacker, CallbackInfoReturnable<Boolean> cir) {
        if (attacker instanceof PlayerEntity && shouldShowArms() && attacker.isSneaking()) {
            if (!attacker.world.isClient) {
                BlockPos pos = ((ArmorStandEntity) (Object) this).getBlockPos();
                Block.dropStack(attacker.getWorld(), pos, new ItemStack(Items.STICK, 2));
                this.setShowArms(false);

                if (this.getEquippedStack(EquipmentSlot.MAINHAND) != null) {
                    Block.dropStack(attacker.getWorld(), pos, this.getEquippedStack(EquipmentSlot.MAINHAND));
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
                }

                if (this.getEquippedStack(EquipmentSlot.OFFHAND) != null) {
                    Block.dropStack(attacker.getWorld(), pos, this.getEquippedStack(EquipmentSlot.OFFHAND));
                    this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
                }
            }
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "onBreak")
    private void onBreakMixin(DamageSource damageSource, CallbackInfo ci) {
        World world = ((ArmorStandEntity) (Object) this).getWorld();
        if (world.isClient)
            return;

        if (this.shouldShowArms()) {
            BlockPos pos = ((ArmorStandEntity) (Object) this).getBlockPos();
            Block.dropStack(world, pos, new ItemStack(Items.STICK, 2));
        }
    }
}
