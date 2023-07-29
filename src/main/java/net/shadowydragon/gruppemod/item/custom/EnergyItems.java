package net.shadowydragon.gruppemod.item.custom;

import net.minecraft.world.item.Item;
import net.shadowydragon.gruppemod.interfaces.ContainsEnergy;

public class EnergyItems extends Item implements ContainsEnergy {
    public EnergyItems(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int energyValue() {
        return 60;
    }
}
