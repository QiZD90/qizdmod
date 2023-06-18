package ml.qizd.qizdmod.mixin.fireworks_kill_players;

import ml.qizd.qizdmod.FireworkElytraDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {
    @Shadow private @Nullable LivingEntity shooter;

    @Shadow public abstract ItemStack getStack();
    private final Random random = Random.createThreadSafe();

    @Inject(at = @At(value = "HEAD"), method = "explode()V")
    public void makeBlankRocketsDoDamageToElytraUser(CallbackInfo info) {
        if (shooter == null)
            return;

        if (random.nextBetween(1, 5) == 5) {
            shooter.damage(new FireworkElytraDamageSource(), 9999f);
        } else {
            shooter.damage(new FireworkElytraDamageSource(), 7f);
        }
    }
}
