package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.blocks.InvisibleLampBlock;
import ml.qizd.qizdmod.blocks.PianoBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block BOTTLE_BLOCK = new BottleBlock();
    public static final Block INVISIBLE_LAMP_BLOCK = new InvisibleLampBlock();
    public static final Block PIANO_BLOCK = new PianoBlock();

    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier("qizdmod", "bottle_block"), BOTTLE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier("qizdmod", "invisible_lamp"), INVISIBLE_LAMP_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier("qizdmod", "piano"), PIANO_BLOCK);
    }
}
