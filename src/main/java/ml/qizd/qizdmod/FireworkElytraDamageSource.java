package ml.qizd.qizdmod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;

public class FireworkElytraDamageSource extends DamageSource {
    public FireworkElytraDamageSource() {
        super("firework_elytra");
        setExplosive();
        setBypassesArmor();
        setBypassesProtection();
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        return Text.translatable("death.attack.firework_elytra", entity.getDisplayName());
    }
}
