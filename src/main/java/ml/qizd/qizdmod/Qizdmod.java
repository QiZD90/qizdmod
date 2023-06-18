package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.InvisibleLampBlock;
import ml.qizd.qizdmod.enchantment.EmptyEnchantment;
import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.enchantment.SmeltingPickaxeEnchantment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Qizdmod implements ModInitializer {
    public static String MOD_ID = "qizdmod";
    public static final ItemGroup ITEM_GROUP =
            FabricItemGroupBuilder.create(new Identifier("qizdmod", "qizdmod_group"))
                    .icon(() -> new ItemStack(ModBlocks.INVISIBLE_LAMP_BLOCK))
                    .build();

    public static final Identifier STARTED_PLAYING = new Identifier("qizdmod", "started_playing_instrument");
    public static final Identifier PLAYED_NOTE = new Identifier("qizdmod", "played_note");


    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModItems.register();
        ModSounds.register();
        ModEntities.register();
        ModVillagerProfessions.register();
        ModEnchantments.register();

        ServerPlayNetworking.registerGlobalReceiver(Qizdmod.PLAYED_NOTE, (server, player, handler, buf, responseSender) -> {
            Instruments.Type type = buf.readEnumConstant(Instruments.Type.class);
            MusicNote note = new MusicNote(buf.readInt());

            Instruments.playNote(player.world, player, type, note);
        });
    }
}
