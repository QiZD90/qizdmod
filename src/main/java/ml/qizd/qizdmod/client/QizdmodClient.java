package ml.qizd.qizdmod.client;

import ml.qizd.qizdmod.MusicNote;
import ml.qizd.qizdmod.Qizdmod;
import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.client.screens.InstrumentScreen;
import ml.qizd.qizdmod.items.LyreItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class QizdmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BottleBlock.BOTTLE_BLOCK, RenderLayer.getTranslucent());

        ClientPlayNetworking.registerGlobalReceiver(Qizdmod.STARTED_PLAYING_LYRE, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                client.setScreen(new InstrumentScreen(client.world, client.player));
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(Qizdmod.PLAYED_LYRE_NOTE, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            MusicNote note = new MusicNote(buf.readInt());
            UUID uuid = buf.readUuid();

            System.out.println(client.player.getUuid());
            System.out.println(uuid);

            if (!client.player.getUuid().equals(uuid))
                LyreItem.playNote(note, client.world, pos);
        });
    }
}
