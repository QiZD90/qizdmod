package ml.qizd.qizdmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

        shooter.damage(DamageSource.GENERIC, 5.0f); // TODO: Make damage source into a firework damage
    }
}
