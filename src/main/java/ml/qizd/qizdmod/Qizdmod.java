package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.blocks.InvisibleLampBlock;
import ml.qizd.qizdmod.enchantment.EmptyEnchantment;
import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.enchantment.SmeltingPickaxeEnchantment;
import ml.qizd.qizdmod.items.LyreItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class Qizdmod implements ModInitializer {
    public static String MOD_ID = "qizdmod";
    public static final ItemGroup ITEM_GROUP =
            FabricItemGroupBuilder.create(new Identifier("qizdmod", "qizdmod_group"))
                    .icon(() -> new ItemStack(InvisibleLampBlock.INVISIBLE_LAMP_BLOCK))
                    .build();

    public static SoundEvent HARP_NOTE_C4 = new SoundEvent(new Identifier("qizdmod", "harp_c4"));
    public static SoundEvent HARP_NOTE_C5 = new SoundEvent(new Identifier("qizdmod", "harp_c5"));
    public static SoundEvent LUTE_NOTE_C4 = new SoundEvent(new Identifier("qizdmod", "lute_c4"));
    public static SoundEvent LUTE_NOTE_C5 = new SoundEvent(new Identifier("qizdmod", "lute_c5"));
    public static SoundEvent PIANO_NOTE_C4 = new SoundEvent(new Identifier("qizdmod", "piano_c4"));

    public static final Identifier STARTED_PLAYING = new Identifier("qizdmod", "started_playing_instrument");
    public static final Identifier PLAYED_NOTE = new Identifier("qizdmod", "played_note");


    @Override
    public void onInitialize() {
        BlocksRegistrar.register();
        ItemRegistrar.register();

        Registry.register(Registry.SOUND_EVENT, HARP_NOTE_C4.getId(), HARP_NOTE_C4);
        Registry.register(Registry.SOUND_EVENT, HARP_NOTE_C5.getId(), HARP_NOTE_C5);
        Registry.register(Registry.SOUND_EVENT, LUTE_NOTE_C4.getId(), LUTE_NOTE_C4);
        Registry.register(Registry.SOUND_EVENT, LUTE_NOTE_C5.getId(), LUTE_NOTE_C5);
        Registry.register(Registry.SOUND_EVENT, PIANO_NOTE_C4.getId(), PIANO_NOTE_C4);

        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "empty_enchantment"),
                EmptyEnchantment.EMPTY_ENCHANTMENT);

        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "intensive_training"),
                IntensiveTrainingEnchantment.INTENSIVE_TRAINING);

        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "smelting_pickaxe"),
                SmeltingPickaxeEnchantment.SMELTING_PICKAXE);

        ServerPlayNetworking.registerGlobalReceiver(Qizdmod.PLAYED_NOTE, (server, player, handler, buf, responseSender) -> {
            Instruments.Type type = buf.readEnumConstant(Instruments.Type.class);
            MusicNote note = new MusicNote(buf.readInt());

            Instruments.playNote(player.world, player, type, note);
        });
    }
}
