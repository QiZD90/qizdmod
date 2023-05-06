package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.blocks.InvisibleLampBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlocksRegistrar {
    public static void register() {
        Registry.register(
                Registry.BLOCK,
                new Identifier("qizdmod", "bottle_block"),
                BottleBlock.BOTTLE_BLOCK);

        Registry.register(
                Registry.BLOCK,
                new Identifier("qizdmod", "invisible_lamp"),
                InvisibleLampBlock.INVISIBLE_LAMP_BLOCK);

    }
}
