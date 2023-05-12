package ml.qizd.qizdmod.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import ml.qizd.qizdmod.CustomTrades;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.SimpleMerchant;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
    @Shadow public abstract VillagerData getVillagerData();

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    // TODO: come up with a better way to do this
    @Override
    public void fillRecipes() {
        VillagerData villagerData = this.getVillagerData();
        Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(villagerData.getProfession());
        if (int2ObjectMap == null || int2ObjectMap.isEmpty()) {
            return;
        }
        TradeOffers.Factory[] vanillaFactories = int2ObjectMap.get(villagerData.getLevel());
        ArrayList<TradeOffers.Factory> factories =
                vanillaFactories == null
                        ? new ArrayList<>()
                        : new ArrayList<>(Arrays.asList(vanillaFactories));

        List<TradeOffers.Factory> moddedFactories = CustomTrades.getCustomTradesForVillager(villagerData);
        factories.addAll(moddedFactories);

        if (factories.isEmpty()) {
            return;
        }

        TradeOfferList tradeOfferList = this.getOffers();
        TradeOffers.Factory[] factoriesArray = new TradeOffers.Factory[factories.size()];
        factories.toArray(factoriesArray);
        this.fillRecipesFromPool(tradeOfferList, factoriesArray, 2);
    }
}
