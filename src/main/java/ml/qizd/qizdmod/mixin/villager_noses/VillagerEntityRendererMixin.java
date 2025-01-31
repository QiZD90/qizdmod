package ml.qizd.qizdmod.mixin.villager_noses;

import ml.qizd.qizdmod.mixin_interfaces.INoseOwner;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(VillagerEntityRenderer.class)
public abstract class VillagerEntityRendererMixin
extends MobEntityRenderer<VillagerEntity, VillagerResemblingModel<VillagerEntity>> {
    public VillagerEntityRendererMixin(EntityRendererFactory.Context context, VillagerResemblingModel<VillagerEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Override
    public void render(VillagerEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        ModelPart noseModel = this.getModel().getHead().getChild(EntityModelPartNames.NOSE);
        Optional<INoseOwner.Nose> nose = ((INoseOwner) mobEntity).getNose();

        if (nose.isEmpty()) {
            noseModel.visible = false;
        } else {
            float scale = nose.get().getScale();
            noseModel.xScale = scale;
            noseModel.yScale = scale;
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

        noseModel.visible = true;
        noseModel.xScale = 1f;
        noseModel.yScale = 1f;
    }
}
