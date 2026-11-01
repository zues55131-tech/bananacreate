package com.example.bananacreate.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Desenha a parte girante (rotor) das maquinas cineticas do addon, girando de
 * acordo com a velocidade (RPM) recebida do Create, igual as maquinas originais.
 * Usa so a API de render do Minecraft (nao depende dos internals do Flywheel).
 */
public class MachineRenderer<T extends KineticBlockEntity> implements BlockEntityRenderer<T> {
    private final ModelResourceLocation rotorModel;

    public MachineRenderer(ModelResourceLocation rotorModel) {
        this.rotorModel = rotorModel;
    }

    @Override
    public void render(T be, float partialTick, PoseStack ps, MultiBufferSource buffer, int light, int overlay) {
        if (be.getLevel() == null) return;
        BlockState state = be.getBlockState();
        if (!state.hasProperty(RotatedPillarKineticBlock.AXIS)) return;
        Direction.Axis axis = state.getValue(RotatedPillarKineticBlock.AXIS);

        // mesma formula do Create: graus = tempo * RPM * 3/10 (+ fase pelo eixo da posicao)
        float time = be.getLevel().getGameTime() + partialTick;
        float angle = (time * be.getSpeed() * 3f / 10f) % 360f;
        if (be.getSpeed() != 0 && (be.getBlockPos().getX() + be.getBlockPos().getY() + be.getBlockPos().getZ()) % 2 != 0) {
            angle += 22.5f;
        }

        BakedModel model = Minecraft.getInstance().getModelManager().getModel(rotorModel);

        ps.pushPose();
        ps.translate(0.5, 0.5, 0.5);
        // o modelo do rotor e feito com o eixo em Y: alinha com o eixo real do bloco
        if (axis == Direction.Axis.Z) ps.mulPose(Axis.XP.rotationDegrees(90));
        else if (axis == Direction.Axis.X) ps.mulPose(Axis.ZP.rotationDegrees(-90));
        ps.mulPose(Axis.YP.rotationDegrees(angle));
        ps.translate(-0.5, -0.5, -0.5);

        VertexConsumer vc = buffer.getBuffer(RenderType.cutoutMipped());
        Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                .renderModel(ps.last(), vc, state, model, 1f, 1f, 1f, light, OverlayTexture.NO_OVERLAY);
        ps.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(T be) {
        return false;
    }
}
