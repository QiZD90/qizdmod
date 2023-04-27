package ml.qizd.qizdmod.mixin.replaceenchantments;

import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.entity.ModifiedFishingBobberEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {
    // TODO: make it less hacky
    // I tried to redirect the constructor of FishingBobberEntity
    // But it gave me an unknown error
    @Redirect(
            method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)"
                    + "Lnet/minecraft/util/TypedActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private boolean injected(World world, Entity entity) {
        FishingBobberEntity fbEntity = (FishingBobberEntity) entity;
        PlayerEntity user = fbEntity.getPlayerOwner();
        ItemStack itemStack = user.getStackInHand(Hand.MAIN_HAND);
        int lureLevel = EnchantmentHelper.getLure(itemStack);
        Map<Enchantment, Integer> enchants = EnchantmentHelper.get(itemStack);
        int intensiveTrainingLevel = enchants.getOrDefault(IntensiveTrainingEnchantment.INTENSIVE_TRAINING, 0);
        return world.spawnEntity(new ModifiedFishingBobberEntity(user, world, lureLevel, intensiveTrainingLevel));
    }
}