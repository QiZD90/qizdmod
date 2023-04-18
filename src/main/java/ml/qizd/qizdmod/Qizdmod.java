package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Qizdmod implements ModInitializer {
    public static String MOD_ID = "qizdmod";
    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "bottle_block"), BottleBlock.BOTTLE_BLOCK);
        Registry.register(
                Registry.ITEM, new Identifier(MOD_ID, "bottle_block"),
                new BlockItem(BottleBlock.BOTTLE_BLOCK,
                        new Item.Settings()
                                .group(ItemGroup.DECORATIONS)
                                .maxCount(16)
                )
        );

    }
}
