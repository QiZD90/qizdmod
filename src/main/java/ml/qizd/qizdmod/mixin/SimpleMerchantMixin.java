package ml.qizd.qizdmod.mixin;

import net.minecraft.village.SimpleMerchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(SimpleMerchant.class)
public class SimpleMerchantMixin {
    @Inject(at = @At("HEAD"), method = "setOffersFromServer(Lnet/minecraft/village/TradeOfferList;)V")
    private void onSettingOffers(TradeOfferList tradeOfferList, CallbackInfo info) {
        /*for (Iterator i = tradeOfferList.iterator(); i.hasNext(); ) {
            TradeOffer offer = (TradeOffer) i.next();
            System.out.println(offer);
        }*/
    }
}
