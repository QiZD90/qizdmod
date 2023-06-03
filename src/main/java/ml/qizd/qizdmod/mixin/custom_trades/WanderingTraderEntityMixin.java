package ml.qizd.qizdmod.mixin.custom_trades;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import ml.qizd.qizdmod.CustomTrades;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity {
    public WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void fillRecipes() {
        Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap = TradeOffers.WANDERING_TRADER_TRADES;;
        TradeOffers.Factory[] factories1 = int2ObjectMap.get(1);
        TradeOffers.Factory[] factories2 = int2ObjectMap.get(2);

        ArrayList<TradeOffers.Factory> factories = new ArrayList<>();
        factories.addAll(Arrays.asList(factories1));
        factories.addAll(Arrays.asList(factories2));

        TradeOfferList tradeOfferList = this.getOffers();
        TradeOffers.Factory[] factoriesArray = new TradeOffers.Factory[factories.size()];
        factories.toArray(factoriesArray);
        this.fillRecipesFromPool(tradeOfferList, factoriesArray, 4);

        factories.clear();
        List<TradeOffers.Factory> moddedFactories = CustomTrades.getCustomTradesForWanderingTrader((WanderingTraderEntity)(Object) this);
        factories.addAll(moddedFactories);
        tradeOfferList = this.getOffers();
        factoriesArray = new TradeOffers.Factory[factories.size()];
        factories.toArray(factoriesArray);
        this.fillRecipesFromPool(tradeOfferList, factoriesArray, 1);
    }
}
