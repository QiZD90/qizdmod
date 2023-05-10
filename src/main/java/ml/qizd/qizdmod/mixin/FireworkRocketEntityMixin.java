package ml.qizd.qizdmod.mixin;

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

        ItemStack stack = this.getStack();
        NbtCompound nbtCompound = stack.isEmpty() ? null : stack.getSubNbt("Fireworks");
        NbtList list =  nbtCompound != null ? nbtCompound.getList("Explosions", NbtElement.COMPOUND_TYPE) : null;
        if (list != null && !list.isEmpty())
            return;

        FireworkRocketEntity _this = (FireworkRocketEntity)(Object) this;
        shooter.damage(fireworkBypassArmor(_this, this.shooter), 5.0f);
    }

    private static DamageSource fireworkBypassArmor(FireworkRocketEntity firework, @Nullable Entity attacker) {
        return new ProjectileDamageSource("fireworks", firework, attacker)
                .setExplosive()
                .setBypassesArmor()
                .setBypassesProtection();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), method = "explode")
    public boolean damage(LivingEntity instance, DamageSource source, float amount) {
        instance.damage(source.setBypassesArmor().setBypassesProtection(), amount);
        return false;
    }
}
