package ml.qizd.qizdmod.mixin.ender_dragon;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntityMixin {
    private static final float DRAGON_MAX_HP = 1000f;
    private static final float HEAL_FROM_CRYSTALS = 0.5f;
    @Inject(at = @At("HEAD"), method = "createEnderDragonAttributes", cancellable = true)
    private static void createEnderDragonAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.setReturnValue(
                MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, DRAGON_MAX_HP));
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (source.isExplosive()) {
            info.setReturnValue(false);
        }
    }

    @Redirect(method = "tickWithEndCrystals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;setHealth(F)V"))
    private void healFromCrystals(EnderDragonEntity instance, float v) {
        instance.setHealth(instance.getHealth() + HEAL_FROM_CRYSTALS);
    }
}
