package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.blocks.InvisibleLampBlock;
import ml.qizd.qizdmod.blocks.PianoBlock;
import ml.qizd.qizdmod.items.LuteItem;
import ml.qizd.qizdmod.items.LyreItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ItemRegistrar {
    public static final int MINECOINS_TYPES_COUNT = 5;
    public static final Item[] MINECOINS = {
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP))
    };

    public static final Item PIANO = new BlockItem(
            PianoBlock.PIANO_BLOCK,
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .rarity(Rarity.RARE)
                    .maxCount(1)
    );

    public static final Item LYRE = new LyreItem(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .rarity(Rarity.UNCOMMON)
                    .maxCount(1)
    );

    public static final Item LUTE = new LuteItem(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .rarity(Rarity.UNCOMMON)
                    .maxCount(1)
    );

    public static void register() {
        Registry.register(
                Registry.ITEM, new Identifier("qizdmod", "bottle_block"),
                new BlockItem(BottleBlock.BOTTLE_BLOCK,
                        new Item.Settings()
                                .group(Qizdmod.ITEM_GROUP)
                                .maxCount(16)
                )
        );

        Registry.register(
                Registry.ITEM,
                new Identifier("qizdmod", "invisible_lamp"),
                new BlockItem(
                        InvisibleLampBlock.INVISIBLE_LAMP_BLOCK,
                        new Item.Settings()
                                .group(Qizdmod.ITEM_GROUP)
                )
        );

        Registry.register(Registry.ITEM, new Identifier("qizdmod", "piano"), PIANO);
        Registry.register(Registry.ITEM, new Identifier("qizdmod", "lyre"), LYRE);
        Registry.register(Registry.ITEM, new Identifier("qizdmod", "lute"), LUTE);

        // Register money
        for (int i = 0; i < MINECOINS_TYPES_COUNT; i++) {
            Registry.register(
                    Registry.ITEM,
                    new Identifier("qizdmod", String.format("minecoin_%d", (int) Math.pow(8, i))),
                    MINECOINS[i]
            );
        }
    }
}
