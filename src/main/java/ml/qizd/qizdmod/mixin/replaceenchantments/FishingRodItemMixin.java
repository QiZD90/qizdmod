package ml.qizd.qizdmod.mixin.replaceenchantments;

import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.entity.ModifiedFishingBobberEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {
    @Redirect(
            method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)"
                    + "Lnet/minecraft/util/TypedActionResult;",
            at = @At(value = "NEW", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;II)V"))
    private FishingBobberEntity injected(PlayerEntity user, World world, int luckOfTheSeaLevel, int lureLevel) {
        ItemStack itemStack = user.getStackInHand(Hand.MAIN_HAND);
        int intensiveTrainingLevel = EnchantmentHelper.getLevel(IntensiveTrainingEnchantment.INTENSIVE_TRAINING, itemStack);
        return new ModifiedFishingBobberEntity(user, world, lureLevel, intensiveTrainingLevel);
    }
}