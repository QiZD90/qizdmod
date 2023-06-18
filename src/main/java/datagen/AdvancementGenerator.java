package datagen;

import ml.qizd.qizdmod.ModBlocks;
import ml.qizd.qizdmod.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class AdvancementGenerator extends FabricAdvancementProvider {
    protected AdvancementGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.create()
                .display(
                        ModBlocks.INVISIBLE_LAMP_BLOCK, // The display icon
                        Text.literal("QiZD's mod"), // The title
                        Text.translatable("advancements.qizdmod.qizdmod.description"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        false, // Show toast top right
                        false, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("always_false", new ImpossibleCriterion.Conditions())
                .build(consumer, "qizdmod" + "/root");

        Advancement heartstrings = Advancement.Builder.create()
                .display(
                        ModItems.INSTRUMENT_STRING, // The display icon
                        Text.translatable("advancements.qizdmod.heartstrings.title"), // The title
                        Text.translatable("advancements.qizdmod.heartstrings.description"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_string", InventoryChangedCriterion.Conditions.items(ModItems.INSTRUMENT_STRING))
                .parent(root)
                .build(consumer, "qizdmod" + "/heartstrings");

        Advancement getLyre = Advancement.Builder.create()
                .display(
                        ModItems.LYRE, // The display icon
                        Text.translatable("advancements.qizdmod.get_lyre.title"), // The title
                        Text.translatable("advancements.qizdmod.get_lyre.description"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_lyre", InventoryChangedCriterion.Conditions.items(ModItems.LYRE))
                .parent(heartstrings)
                .build(consumer, "qizdmod" + "/get_lyre");
    }
}
