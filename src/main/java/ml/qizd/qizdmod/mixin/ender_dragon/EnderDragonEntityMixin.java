package ml.qizd.qizdmod.mixin.ender_dragon;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin extends MobEntity {
    @Shadow @Final private @Nullable EnderDragonFight fight;
    private static final float DRAGON_MAX_HP = 1000f;
    private static final float HEAL_FROM_CRYSTALS = 0.2f;
    private static final int TICKS_OF_VULNERABILITY = 20 * 60;

    private static TrackedData<Integer> TICKS_UNTIL_INVULNERABILITY
            = DataTracker.registerData(EnderDragonEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public int getTicksUntilInvulnerability() {
        return this.dataTracker.get(TICKS_UNTIL_INVULNERABILITY);
    }

    public void setTicksUntilInvulnerability(int ticks) {
        this.dataTracker.set(TICKS_UNTIL_INVULNERABILITY, ticks);
    }

    @Inject(at = @At("TAIL"), method = "initDataTracker")
    public void initDataTracker(CallbackInfo ci) {
        this.getDataTracker().startTracking(TICKS_UNTIL_INVULNERABILITY, -1);
    }


    @Inject(at = @At("HEAD"), method = "createEnderDragonAttributes", cancellable = true)
    private static void createEnderDragonAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.setReturnValue(
                MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, DRAGON_MAX_HP));
    }

    @Inject(at= @At("TAIL"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.setTicksUntilInvulnerability(nbt.getInt("TicksUntilInvulnerability"));
        //System.out.printf("got %d into nbt; %d\n", nbt.getInt("TicksUntilInvulnerability"), getTicksUntilInvulnerability());
    }

    @Inject(at= @At("TAIL"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("TicksUntilInvulnerability", getTicksUntilInvulnerability());
        //System.out.printf("put %d into nbt\n", getTicksUntilInvulnerability());
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    private void tickMovement(CallbackInfo info) {
        if (this.getTicksUntilInvulnerability() > 0) {
            this.setTicksUntilInvulnerability(this.getTicksUntilInvulnerability() - 1);
            if (this.getTicksUntilInvulnerability() == 0) {
                this.setTicksUntilInvulnerability(-1);
                tryToBroadcast("text.qizdmod.ender_dragon_invulnerable_again");
                this.tryToRespawnCrystals();
            }
        }
    }

    @ModifyConstant(constant = @Constant(floatValue = 10.0f), method = "damageLivingEntities")
    private float damageAmount(float constant) {
        return 20.0f;
    }

    @Inject(at = @At("TAIL"), method = "crystalDestroyed")
    private void crystalDestroyed(EndCrystalEntity crystal, BlockPos pos, DamageSource source, CallbackInfo info) {
        ServerWorld world = getServerWorld();
        if (world == null) {
            //System.out.println("Crystal destroyed on client");
            return;
        }

        EnderDragonFight fight = world.getEnderDragonFight();
        if (fight == null) {
            //System.out.println("Crystal destroyed on server but fight is null");
            return;
        }

        //System.out.printf("Crystal destroyed on server; Alive crystals: %d\n", fight.getAliveEndCrystals());

        if (fight.getAliveEndCrystals() == 0) {
            setTicksUntilInvulnerability(TICKS_OF_VULNERABILITY);
            System.out.println(fight);
            tryToBroadcast("text.qizdmod.ender_dragon_vulnerable");
        }
    }

    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isExplosive())
            return true;

        //System.out.printf("isInvulnerableTo func; ticks: %d\n", getTicksUntilInvulnerability());

        if (this.getTicksUntilInvulnerability() == -1)
            return true;

        return super.isInvulnerableTo(source);
    }

    @Redirect(method = "tickWithEndCrystals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;setHealth(F)V"))
    private void healFromCrystals(EnderDragonEntity instance, float v) {
        instance.setHealth(instance.getHealth() + HEAL_FROM_CRYSTALS);
    }

    private void tryToBroadcast(String id) {
        ServerWorld world = getServerWorld();
        if (world == null)
            return;

        for (ServerPlayerEntity player : world.getPlayers()) {
            player.sendMessage(Text.translatable(id).setStyle(Style.EMPTY.withColor(0xffff00)));
        }
    }

    private void tryToRespawnCrystals() {
        ServerWorld world = this.getServerWorld();
        if (world == null)
            return;

        List<EndSpikeFeature.Spike> list = EndSpikeFeature.getSpikes(world);
        for (EndSpikeFeature.Spike spike: list) {
            for (BlockPos blockPos : BlockPos.iterate(
                    new BlockPos(spike.getCenterX() - 10, spike.getHeight() - 10, spike.getCenterZ() - 10),
                    new BlockPos(spike.getCenterX() + 10, spike.getHeight() + 10, spike.getCenterZ() + 10)
            )) {
                world.removeBlock(blockPos, false);
            }

            world.createExplosion(null, (float)spike.getCenterX() + 0.5f, spike.getHeight(), (float)spike.getCenterZ() + 0.5f, 5.0f, Explosion.DestructionType.DESTROY);
            EndSpikeFeatureConfig endSpikeFeatureConfig = new EndSpikeFeatureConfig(false, ImmutableList.of(spike), null);
            Feature.END_SPIKE.generateIfValid(endSpikeFeatureConfig, world, world.getChunkManager().getChunkGenerator(), Random.create(), new BlockPos(spike.getCenterX(), 45, spike.getCenterZ()));
        }
    }

    @Nullable
    public ServerWorld getServerWorld() {
        World world = this.getWorld();
        return world.isClient() ? null : (ServerWorld) world;
    }
}
