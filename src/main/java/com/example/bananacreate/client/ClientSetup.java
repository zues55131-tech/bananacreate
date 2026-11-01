package com.example.bananacreate.client;

import com.example.bananacreate.BananaCreate;
import com.example.bananacreate.registry.ModBlockEntities;
import com.example.bananacreate.registry.ModEntities;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import com.example.bananacreate.registry.ModFluids;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = BananaCreate.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BANANA_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.CYBER_ROCKET.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.MINI_GOBLIN.get(), MiniGoblinRenderer::new);
        event.registerEntityRenderer(ModEntities.KASANE_TETO.get(), KasaneTetoRenderer::new);
    }

    private static final String[] ROTORS = {
            "blood_extractor", "blood_centrifuge", "titan_treadmill",
            "chaos_dynamo", "death_siphon", "fear_seal"};

    private static ModelResourceLocation rotor(String name) {
        return ModelResourceLocation.standalone(
                ResourceLocation.fromNamespaceAndPath(BananaCreate.MOD_ID, "block/" + name + "_rotor"));
    }

    /** Carrega os modelos dos rotores (nao pertencem a nenhum blockstate). */
    @SubscribeEvent
    public static void registerRotorModels(ModelEvent.RegisterAdditional event) {
        for (String n : ROTORS) event.register(rotor(n));
    }

    /** Renderers que fazem as maquinas girarem como as do Create. */
    @SubscribeEvent
    public static void registerMachineRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.BLOOD_EXTRACTOR.get(), c -> new MachineRenderer<>(rotor("blood_extractor")));
        event.registerBlockEntityRenderer(ModBlockEntities.BLOOD_CENTRIFUGE.get(), c -> new MachineRenderer<>(rotor("blood_centrifuge")));
        event.registerBlockEntityRenderer(ModBlockEntities.TITAN_TREADMILL.get(), c -> new MachineRenderer<>(rotor("titan_treadmill")));
        event.registerBlockEntityRenderer(ModBlockEntities.CHAOS_DYNAMO.get(), c -> new MachineRenderer<>(rotor("chaos_dynamo")));
        event.registerBlockEntityRenderer(ModBlockEntities.DEATH_SIPHON.get(), c -> new MachineRenderer<>(rotor("death_siphon")));
        event.registerBlockEntityRenderer(ModBlockEntities.FEAR_SEAL.get(), c -> new MachineRenderer<>(rotor("fear_seal")));
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(KasaneTetoModel.LAYER, KasaneTetoModel::createBodyLayer);
        event.registerLayerDefinition(CyberwareModel.LAYER, CyberwareModel::createBodyLayer);
    }

    /** Cyberware visivel no corpo de qualquer jogador (skin wide e slim). */
    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new CyberwareLayer(renderer, event.getEntityModels()));
            }
        }
    }

    /** Sangue = textura de agua tingida de vermelho. */
    @SubscribeEvent
    public static void registerFluidExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_still");
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_flow");
            }

            @Override
            public int getTintColor() {
                return 0xFFA01020;
            }
        }, ModFluids.BLOOD_TYPE.get());
    }
}
