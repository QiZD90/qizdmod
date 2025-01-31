package ml.qizd.qizdmod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomTrades {
    private static class SellForDiamondsFactory implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellForDiamondsFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        @Nullable
        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(
                    new ItemStack(Items.DIAMOND, this.price),
                    new ItemStack(this.sell.getItem(), this.count),
                    this.maxUses, this.experience, this.multiplier);
        }
    }
    public static List<TradeOffers.Factory> getCustomTradesForVillager(VillagerData data) {
        ArrayList<TradeOffers.Factory> factories = new ArrayList<>();
        return factories;
    }

    public static List<TradeOffers.Factory> getCustomTradesForWanderingTrader(WanderingTraderEntity trader) {
        ArrayList<TradeOffers.Factory> factories = new ArrayList<>();
        factories.add(new SellForDiamondsFactory(
           new ItemStack(ModItems.LYRE_BODY),
           9,
           1,
           1,
           20,
           0.05f
        ));

        factories.add(new SellForDiamondsFactory(
                new ItemStack(ModItems.INSTRUMENT_STRING),
                3,
                1,
                3,
                20,
                0.05f
        ));

        return factories;
    }
}
