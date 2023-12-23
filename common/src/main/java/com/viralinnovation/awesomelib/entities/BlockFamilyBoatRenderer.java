package com.viralinnovation.awesomelib.entities;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import com.viralinnovation.awesomelib.context.RegistrationContext;
import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

import java.util.Map;

public class BlockFamilyBoatRenderer extends EntityRenderer<BlockFamilyBoat> {

    private final Map<BlockFamily, Pair<ResourceLocation, BoatModel>> boatResources;

    public BlockFamilyBoatRenderer(RegistrationContext registrationContext, EntityRendererProvider.Context context, boolean hasChest) {
        super(context);
        this.shadowRadius = 0.8F;
        this.boatResources = registrationContext.getFamilies().getOrganicMap().values().stream()
                .collect(ImmutableMap.toImmutableMap((awesomeType) -> {
                    return awesomeType;
                }, (awesomeType) -> {
                    return Pair.of(registrationContext.id(getTextureLocation(awesomeType, hasChest)),
                            this.createBoatModel(hasChest));
                }));
    }

    private BoatModel createBoatModel(boolean hasChest) {
        return hasChest ? new ChestBoatModel(bakeLayer(true)) : new BoatModel(bakeLayer(false));
    }

    public static ModelPart bakeLayer(boolean hasChest) {
        LayerDefinition definition = hasChest ? ChestBoatModel.createBodyModel() : BoatModel.createBodyModel();
        return definition.bakeRoot();
    }

    private static String getTextureLocation(BlockFamily awesomeType, boolean hasChest) {
        return hasChest ? "textures/entity/chest_boat/" + awesomeType.getBaseName() + ".png" : "textures/entity/boat/"
                + awesomeType.getBaseName() + ".png";
    }

    @Override
    public void render(BlockFamilyBoat boat, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource multiBufferSource, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        float h = (float) boat.getHurtTime() - partialTicks;
        float j = boat.getDamage() - partialTicks;
        if (j < 0.0F) {
            j = 0.0F;
        }
        if (h > 0.0F) {
            matrixStackIn.mulPose(Axis.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0F * (float) boat.getHurtDir()));
        }

        float k = boat.getBubbleAngle(partialTicks);
        if (!Mth.equal(k, 0.0F)) {
            matrixStackIn.mulPose(new Quaternionf().setAngleAxis(boat.getBubbleAngle(partialTicks) * ((float)Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }
        Pair<ResourceLocation, BoatModel> pair = this.boatResources.get(boat.getAwesomeBoatType());
        ResourceLocation resourceLocation = (ResourceLocation) pair.getFirst();
        BoatModel boatModel = (BoatModel) pair.getSecond();
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F));
        boatModel.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(boatModel.renderType(resourceLocation));
        boatModel.renderToBuffer(matrixStackIn, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!boat.isUnderWater()) {
            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.waterMask());
            boatModel.waterPatch().render(matrixStackIn, vertexConsumer2, packedLightIn, OverlayTexture.NO_OVERLAY);
        }
        matrixStackIn.popPose();
        super.render(boat, entityYaw, partialTicks, matrixStackIn, multiBufferSource, packedLightIn);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(BlockFamilyBoat boat) {
        return this.boatResources.get(boat.getAwesomeBoatType()).getFirst();
    }
}
