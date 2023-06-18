package ml.qizd.qizdmod.mixin.villager_noses;

import ml.qizd.qizdmod.ModItems;
import ml.qizd.qizdmod.ModTrackedDataHandlers;
import ml.qizd.qizdmod.Qizdmod;
import ml.qizd.qizdmod.items.NoseItem;
import ml.qizd.qizdmod.mixin_interfaces.INoseOwner;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.swing.text.html.Option;
import java.util.Optional;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements INoseOwner {
    private static final TrackedData<Optional<Nose>> NOSE = DataTracker.registerData(VillagerEntityMixin.class, ModTrackedDataHandlers.NOSE);

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initDataTracker")
    private void initDataTrackerMixin(CallbackInfo ci) {
        this.dataTracker.startTracking(NOSE, Optional.of(new Nose()));
    }

    @Override
    public boolean hasNose() {
        return this.dataTracker.get(NOSE).isPresent();
    }

    @Override
    public void setNose(Optional<Nose> nose) {
        this.dataTracker.set(NOSE, nose);
    }

    @Override
    public Optional<Nose> getNose() {
        return this.dataTracker.get(NOSE);
    }

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    private void interactMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.world.isClient)
            return;

        ItemStack stack = player.getStackInHand(hand);
        if (this.hasNose() && stack.isOf(Items.SHEARS)) {
            stack.damage(1, player, null);
            Block.dropStack(player.getWorld(), this.getBlockPos(), NoseItem.fromVillager((VillagerEntity)(Object) this));
            setNose(Optional.empty());
            player.world.playSound(
                    null, player.getBlockPos(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.AMBIENT,
                    1.0f,1.0f);
            Qizdmod.SHEARED_VILLAGER_NOSE.trigger((ServerPlayerEntity) player);
            cir.setReturnValue(ActionResult.SUCCESS);
        } else if (!this.hasNose() && stack.isOf(ModItems.VILLAGER_NOSE)) {
            setNose(Nose.readFromNbt(stack.getNbt()));
            stack.decrement(1);
            player.world.playSound(
                    null, this.getBlockPos(), SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.AMBIENT,
                    1.0f, 1.0f
            );
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    private void writeToNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        if (this.hasNose()) {
            this.getNose().get().writeToNbt(nbt);
        } else {
            INoseOwner.Nose.writeEmptyToNbt(nbt);
        }
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    private void readFromNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        this.dataTracker.set(NOSE, Nose.readFromNbt(nbt));
    }
}
