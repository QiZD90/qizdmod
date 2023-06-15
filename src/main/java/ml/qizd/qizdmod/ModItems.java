package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.blocks.InvisibleLampBlock;
import ml.qizd.qizdmod.items.NoseItem;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import ml.qizd.qizdmod.items.LyreItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {
    private static final Identifier ENDER_DRAGON_LOOT_TABLE = EntityType.ENDER_DRAGON.getLootTableId();
    public static final Item DRAGON_TOOTH = new Item(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .rarity(Rarity.UNCOMMON)
                    .fireproof()
    );
    public static final Item DRAGON_SCALE = new Item(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .rarity(Rarity.UNCOMMON)
                    .fireproof()
    );
    public static final int MINECOINS_TYPES_COUNT = 5;
    public static final Item[] MINECOINS = {
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP))
    };

    /*public static final Item PIANO = new BlockItem(
            PianoBlock.PIANO_BLOCK,
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .rarity(Rarity.RARE)
                    .maxCount(1)
    );*/

    public static final Item LYRE = new LyreItem(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .rarity(Rarity.UNCOMMON)
                    .maxCount(1)
    );

    /*public static final Item LUTE = new LuteItem(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .rarity(Rarity.UNCOMMON)
                    .maxCount(1)
    );*/

    public static final Item LYRE_BODY = new Item(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
                    .maxCount(1)
    );

    public static final Item INSTRUMENT_STRING = new Item(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
    );

    public static final Item VILLAGER_NOSE = new NoseItem(
            new Item.Settings()
                    .group(Qizdmod.ITEM_GROUP)
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

        Registry.register(
                Registry.ITEM,
                new Identifier("qizdmod", "dragon_tooth"),
                DRAGON_TOOTH
        );

        Registry.register(
                Registry.ITEM,
                new Identifier("qizdmod", "dragon_scale"),
                DRAGON_SCALE
        );

        //Registry.register(Registry.ITEM, new Identifier("qizdmod", "piano"), PIANO);
        Registry.register(Registry.ITEM, new Identifier("qizdmod", "lyre"), LYRE);
        //Registry.register(Registry.ITEM, new Identifier("qizdmod", "lute"), LUTE);
        Registry.register(Registry.ITEM, new Identifier("qizdmod", "lyre_body"), LYRE_BODY);
        Registry.register(Registry.ITEM, new Identifier("qizdmod", "instrument_string"), INSTRUMENT_STRING);

        Registry.register(Registry.ITEM, new Identifier("qizdmod", "villager_nose"), VILLAGER_NOSE);

        // Register money
        for (int i = 0; i < MINECOINS_TYPES_COUNT; i++) {
            Registry.register(
                    Registry.ITEM,
                    new Identifier("qizdmod", String.format("minecoin_%d", (int) Math.pow(8, i))),
                    MINECOINS[i]
            );
        }

        modifyLootTables();
    }

    private static void modifyLootTables() {
        // TODO: make it drop during the dragon fight dying phase (like exp orbs)
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && ENDER_DRAGON_LOOT_TABLE.equals(id)) {
                LootPool.Builder poolBuilder =
                        LootPool.builder()
                                .rolls(UniformLootNumberProvider.create(3, 10))
                                .with(ItemEntry.builder(DRAGON_TOOTH));

                tableBuilder.pool(poolBuilder);

                poolBuilder =
                        LootPool.builder()
                                .rolls(UniformLootNumberProvider.create(3, 10))
                                .with(ItemEntry.builder(DRAGON_SCALE));

                tableBuilder.pool(poolBuilder);

                poolBuilder =
                        LootPool.builder()
                                .rolls(UniformLootNumberProvider.create(3, 10))
                                .with(ItemEntry.builder(Items.PHANTOM_MEMBRANE));

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
