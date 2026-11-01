package com.example.bananacreate.block;

import com.example.bananacreate.block.entity.TitanTreadmillBE;
import com.example.bananacreate.registry.ModBlockEntities;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;

/** Esteira de Tita: gerador cinetico movido por titas andando em circulo. */
public class TitanTreadmillBlock extends RotatedPillarKineticBlock implements IBE<TitanTreadmillBE> {
    public TitanTreadmillBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<TitanTreadmillBE> getBlockEntityClass() {
        return TitanTreadmillBE.class;
    }

    @Override
    public BlockEntityType<? extends TitanTreadmillBE> getBlockEntityType() {
        return ModBlockEntities.TITAN_TREADMILL.get();
    }
}
