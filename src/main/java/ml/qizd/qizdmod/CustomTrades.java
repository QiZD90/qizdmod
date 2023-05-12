package ml.qizd.qizdmod;

import net.minecraft.item.ItemStack;
import net.minecraft.village.*;

import java.util.ArrayList;
import java.util.List;

public class CustomTrades {
    public static List<TradeOffers.Factory> getCustomTradesForVillager(VillagerData data) {
        ArrayList<TradeOffers.Factory> factories = new ArrayList<>();

        if (data.getProfession() == VillagerProfession.CLERIC && data.getLevel() >= 3) {
            factories.add(new TradeOffers.SellItemFactory(
                    new ItemStack(ItemRegistrar.LYRE),
                    64,
                    1,
                    1,
                    5,
                    0.05f
            ));
        }

        return factories;
    }
}
