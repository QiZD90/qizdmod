package ml.qizd.qizdmod.mixin;

import net.minecraft.client.render.entity.FoxEntityRenderer;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxEntityRenderer.class)
public class FoxEntityRendererMixin {
    private static final Identifier LUKA_TEXTURE
            = new Identifier("qizdmod", "textures/entity/fox/blue_fox.png");

    private static final Identifier SLEEPING_LUKA_TEXTURE
            = new Identifier("qizdmod", "textures/entity/fox/blue_fox.png");

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/entity/passive/FoxEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    public void getTexture(FoxEntity foxEntity, CallbackInfoReturnable<Identifier> info) {
        if (foxEntity.hasCustomName() && foxEntity.getCustomName().getString().equals("Luka")) {
            info.setReturnValue(foxEntity.isSleeping() ? SLEEPING_LUKA_TEXTURE : LUKA_TEXTURE);
        }
    }
}
