package ml.qizd.qizdmod.mixin;

import ml.qizd.qizdmod.FireworkElytraDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {
    @Shadow private @Nullable LivingEntity shooter;

    @Shadow public abstract ItemStack getStack();

    @Inject(at = @At(value = "HEAD"), method = "explode()V")
    public void makeBlankRocketsDoDamageToElytraUser(CallbackInfo info) {
        if (shooter == null)
            return;

        shooter.damage(new FireworkElytraDamageSource(), 9999f);
    }
}
