package ml.qizd.qizdmod.mixin.swamp_golem_fix;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.vedycrew.wetlands.WetlandsMod;
import net.vedycrew.wetlands.entity.custom.SwampGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SwampGolemEntity.class)
public abstract class SwampGolemEntityMixin extends GolemEntity {
    @Shadow public abstract int getStan();

    @Shadow public abstract void setAnim(int s);

    @Shadow public abstract void setStan(int stan);

    protected SwampGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean damage(DamageSource source, float amount) {
        double headStart = this.getPos().add(0.0, this.getBoundingBox(this.getPose()).getYLength() * 0.85, 0.0).y - 0.3;
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source instanceof ProjectileDamageSource) {
            if (this.getStan() <= 0) {
                if (source.getPosition().y >= headStart) {
                    this.setAnim(3); // STUN animation
                    this.setStan(140);
                    //System.out.println(amount * 2.0F + "  " + this.getHealth());
                    if (amount * 2.0F >= this.getHealth()) {
                        //System.out.println("killed with a headshot");
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 1, 255, false, false));
                        if (source.getAttacker() instanceof ServerPlayerEntity) {
                            WetlandsMod.HEADSHOT.trigger((ServerPlayerEntity) source.getAttacker(), this, source);
                        }
                    }
                }

                return super.damage(source, amount * 2.0F);
            } else {
                return false;
            }
        } else {
            return super.damage(source, amount);
        }
    }
}
