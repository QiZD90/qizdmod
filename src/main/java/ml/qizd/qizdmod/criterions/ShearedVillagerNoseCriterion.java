package ml.qizd.qizdmod.criterions;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ShearedVillagerNoseCriterion extends AbstractCriterion<ShearedVillagerNoseCriterion.Condition> {
    public static Identifier ID = Identifier.of("qizdmod", "sheared_villager_nose");

    @Override
    protected Condition conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Condition();
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        trigger(player, condition -> {
            return true;
        });
    }

    public static class Condition extends AbstractCriterionConditions {
        public Condition() {
            super(ID, EntityPredicate.Extended.EMPTY);
        }
    }
}
