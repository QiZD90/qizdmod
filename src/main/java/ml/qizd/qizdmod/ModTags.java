package ml.qizd.qizdmod;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {
    public static final TagKey<Item> MUSICAL_INSTRUMENTS
            = TagKey.of(Registry.ITEM_KEY, new Identifier("qizdmod", "musical_instruments"));
    public static final TagKey<Item> MUSICAL_INSTRUMENTS_PARTS
            = TagKey.of(Registry.ITEM_KEY, new Identifier("qizdmod", "musical_instruments_parts"));
}
