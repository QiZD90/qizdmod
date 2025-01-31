package ml.qizd.qizdmod.client;

import ml.qizd.qizdmod.Instruments;
import ml.qizd.qizdmod.ModBlocks;
import ml.qizd.qizdmod.Qizdmod;
import ml.qizd.qizdmod.client.screens.LutePlayingScreen;
import ml.qizd.qizdmod.client.screens.LyrePlayingScreen;
import ml.qizd.qizdmod.client.screens.PianoPlayingScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.render.RenderLayer;

public class QizdmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOTTLE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PIANO_BLOCK, RenderLayer.getCutoutMipped());

        ClientPlayNetworking.registerGlobalReceiver(Qizdmod.STARTED_PLAYING, (client, handler, buf, responseSender) -> {
            Instruments.Type type = buf.readEnumConstant(Instruments.Type.class);

            client.execute(() -> {
                switch (type) {
                    case Lyre -> client.setScreen(new LyrePlayingScreen(client.world, client.player));
                    case Lute -> client.setScreen(new LutePlayingScreen(client.world, client.player));
                    case Piano -> client.setScreen(new PianoPlayingScreen(client.world, client.player));
                    default -> Log.error(LogCategory.LOG, "Unknown instrument type");
                }
            });
        });
    }
}
