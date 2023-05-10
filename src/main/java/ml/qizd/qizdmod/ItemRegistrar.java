package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.blocks.InvisibleLampBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ItemRegistrar {
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
    public static Item[] MINECOINS = {
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP)),
            new Item(new Item.Settings().group(Qizdmod.ITEM_GROUP))
    };

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
