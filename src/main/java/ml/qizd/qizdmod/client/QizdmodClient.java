package ml.qizd.qizdmod.client;

import ml.qizd.qizdmod.Qizdmod;
import ml.qizd.qizdmod.blocks.BottleBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class QizdmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BottleBlock.BOTTLE_BLOCK, RenderLayer.getTranslucent());
    }
}
