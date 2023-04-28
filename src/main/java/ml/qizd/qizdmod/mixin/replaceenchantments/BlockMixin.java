package ml.qizd.qizdmod.mixin.replaceenchantments;

import ml.qizd.qizdmod.enchantment.SmeltingPickaxeEnchantment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedList;
import java.util.List;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("RETURN"), method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", cancellable = true)
    private static void injectIntoGetDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> info) {
        if (!EnchantmentHelper.get(stack).containsKey(SmeltingPickaxeEnchantment.SMELTING_PICKAXE))
            return;

        List<ItemStack> drops = info.getReturnValue();
        List<ItemStack> newDrops = new LinkedList<>();
        for (ItemStack drop : drops) {
            ItemStack smeltedStack = getSmeltedStack(world, drop);
            if (smeltedStack != null) {
                newDrops.add(smeltedStack);
            } else {
                newDrops.add(drop);
            }
        }

        info.setReturnValue(newDrops);
    }

    private static ItemStack getSmeltedStack(ServerWorld world, ItemStack stack) {
        for (SmeltingRecipe recipe : world.getRecipeManager().listAllOfType(RecipeType.SMELTING)) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient.test(stack)) {
                    return new ItemStack(recipe.getOutput().getItem(), recipe.getOutput().getCount() * stack.getCount());
                }
            }
        }
        return null;
    }
}
