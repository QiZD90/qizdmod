package ml.qizd.qizdmod;

import ml.qizd.qizdmod.blocks.BottleBlock;
import ml.qizd.qizdmod.blocks.InvisibleLampBlock;
import ml.qizd.qizdmod.enchantment.EmptyEnchantment;
import ml.qizd.qizdmod.enchantment.IntensiveTrainingEnchantment;
import ml.qizd.qizdmod.enchantment.SmeltingPickaxeEnchantment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Qizdmod implements ModInitializer {
    public static String MOD_ID = "qizdmod";
    public static final ItemGroup ITEM_GROUP =
            FabricItemGroupBuilder.create(new Identifier("qizdmod", "qizdmod_group"))
                    .icon(() -> new ItemStack(InvisibleLampBlock.INVISIBLE_LAMP_BLOCK))
                    .build();

    public static SoundEvent HARP_NOTE_C4 = new SoundEvent(new Identifier("qizdmod", "harp_c4"));
    public static SoundEvent HARP_NOTE_C5 = new SoundEvent(new Identifier("qizdmod", "harp_c5"));


    @Override
    public void onInitialize() {
        BlocksRegistrar.register();
        ItemRegistrar.register();


        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "empty_enchantment"),
                EmptyEnchantment.EMPTY_ENCHANTMENT);

        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "intensive_training"),
                IntensiveTrainingEnchantment.INTENSIVE_TRAINING);

        Registry.register(
                Registry.ENCHANTMENT,
                new Identifier("qizdmod", "smelting_pickaxe"),
                SmeltingPickaxeEnchantment.SMELTING_PICKAXE);
    }
}
