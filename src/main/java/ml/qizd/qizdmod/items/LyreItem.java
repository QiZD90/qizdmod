package ml.qizd.qizdmod.items;

import ml.qizd.qizdmod.Qizdmod;
import ml.qizd.qizdmod.client.screens.InstrumentScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LyreItem extends Item {
    public LyreItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            MinecraftClient.getInstance().setScreen(new InstrumentScreen(world, user));
            //MinecraftClient.getInstance().setCameraEntity();
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
