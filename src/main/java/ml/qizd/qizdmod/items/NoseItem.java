package ml.qizd.qizdmod.items;

import ml.qizd.qizdmod.ModItems;
import ml.qizd.qizdmod.Qizdmod;
import ml.qizd.qizdmod.mixin_interfaces.INoseOwner;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NoseItem extends Item {
    @Nullable
    public static ItemStack fromVillager(VillagerEntity villager) {
        INoseOwner noseOwner = (INoseOwner) villager;
        if (!noseOwner.hasNose())
            return null;

        ItemStack stack = new ItemStack(ModItems.VILLAGER_NOSE);
        INoseOwner.Nose nose = noseOwner.getNose().get();
        NbtCompound nbt = stack.getOrCreateNbt();
        nose.writeToNbt(nbt);
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        // I had to resort to this hack since there is no method to set the default nbt
        NbtCompound nose = stack.getOrCreateSubNbt("Nose");
        if (!nose.contains("SizeInCm"))
            nose.putInt("SizeInCm", 15);
        tooltip.add(Text.translatable("text.villager_nose.size_in_cm", nose.getInt("SizeInCm")));
    }

    public NoseItem(Settings settings) {
        super(settings);
    }
}
