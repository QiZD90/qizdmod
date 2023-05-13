package ml.qizd.qizdmod;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

public class VillagerRegistrar {
    public static VillagerProfession LUTHIER = new VillagerProfession(
            "qizdmod:luthier",
            PointOfInterestType.NONE,
            PointOfInterestType.NONE,
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH);
    public static void register() {
        LUTHIER = Registry.register(Registry.VILLAGER_PROFESSION,new Identifier("qizdmod", "luthier"), LUTHIER);
    }
}
