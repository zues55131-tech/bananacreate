package com.example.bananacreate;

import com.example.bananacreate.registry.ModBlockEntities;
import com.example.bananacreate.registry.ModBlocks;
import com.example.bananacreate.registry.ModCreativeTabs;
import com.example.bananacreate.registry.ModEntities;
import com.example.bananacreate.registry.ModFluids;
import com.example.bananacreate.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Mod(BananaCreate.MOD_ID)
public class BananaCreate {
    public static final String MOD_ID = "bananacreate";

    public BananaCreate(IEventBus modBus) {
        ModFluids.FLUID_TYPES.register(modBus);
        ModFluids.FLUIDS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modBus);
        ModEntities.ENTITIES.register(modBus);
        ModCreativeTabs.TABS.register(modBus);

        modBus.addListener(BananaCreate::registerCapabilities);
    }

    /** Expoe os tanques das maquinas para canos/bombas do Create. */
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.BLOOD_EXTRACTOR.get(), (be, side) -> be.tank);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.BLOOD_CENTRIFUGE.get(), (be, side) -> be.tank);
    }
}
