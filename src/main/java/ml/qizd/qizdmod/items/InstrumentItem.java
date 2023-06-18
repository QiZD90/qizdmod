package ml.qizd.qizdmod.items;

import ml.qizd.qizdmod.Instruments;
import ml.qizd.qizdmod.Qizdmod;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class InstrumentItem extends Item {
    private final Instruments.Type type;

    public InstrumentItem(Instruments.Type type, Settings settings) {
        super(settings);
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) return super.use(world, user, hand);

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeEnumConstant(type);

        ServerPlayNetworking.send((ServerPlayerEntity) user, Qizdmod.STARTED_PLAYING, buf);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}