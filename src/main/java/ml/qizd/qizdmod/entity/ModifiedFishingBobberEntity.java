package ml.qizd.qizdmod.entity;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModifiedFishingBobberEntity extends FishingBobberEntity {
    private int lureLevel = 0;
    private int intensiveTrainingLevel = 0;
    public ModifiedFishingBobberEntity(PlayerEntity user, World world, int lureLevel, int intensiveTrainingLevel) {
        super(user, world, 0, lureLevel);
        this.lureLevel = lureLevel;
        this.intensiveTrainingLevel = intensiveTrainingLevel;
        System.out.printf("INIT Lure: %d, IT: %d%n", lureLevel, intensiveTrainingLevel);
    }

    @Override
    public int use(ItemStack usedItem) {
        System.out.printf("Lure: %d, IT: %d%n", lureLevel, intensiveTrainingLevel);
        int result = super.use(usedItem);
        if (result == 1) {
            PlayerEntity playerEntity = this.getPlayerOwner();
            playerEntity.world.spawnEntity(
                    new ExperienceOrbEntity(
                            playerEntity.world,
                            playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ() + 0.5,
                            this.random.nextInt(intensiveTrainingLevel) + intensiveTrainingLevel));
        }

        return result;
    }
}
