package ml.qizd.qizdmod.items;

import com.mojang.authlib.yggdrasil.response.User;
import ml.qizd.qizdmod.MusicNote;
import ml.qizd.qizdmod.Qizdmod;
import ml.qizd.qizdmod.client.screens.InstrumentScreen;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LyreItem extends Item {
    public LyreItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) return super.use(world, user, hand);

        ServerPlayNetworking.send((ServerPlayerEntity) user, Qizdmod.STARTED_PLAYING_LYRE, PacketByteBufs.empty());
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public static void playNote(MusicNote note, World world, BlockPos pos) {
        if (note.lower(MusicNote.C5)) {
            world.playSound(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    Qizdmod.HARP_NOTE_C4,
                    SoundCategory.MUSIC,
                    1f,
                    MusicNote.ratio(note, MusicNote.C4),
                    true
            );
        } else {
            world.playSound(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    Qizdmod.HARP_NOTE_C5,
                    SoundCategory.MUSIC,
                    1f,
                    MusicNote.ratio(note, MusicNote.C5),
                    true
            );
        }

    }
}
