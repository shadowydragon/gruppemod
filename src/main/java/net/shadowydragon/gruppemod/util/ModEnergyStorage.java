package net.shadowydragon.gruppemod.util;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {
    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.energy = 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
/*        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if(extractedEnergy != 0) {
            onEnergyChanged();
        }

        return extractedEnergy;*/

        int buf = this.energy;


        this.energy -= maxExtract;

        if (buf != energy)
        {
            onEnergyChanged();
        }
        return maxExtract;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
/*        int receiveEnergy = super.receiveEnergy(maxReceive, simulate);
        if(receiveEnergy != 0) {
            onEnergyChanged();
        }

        return receiveEnergy;*/

        int buf = this.energy;

        this.energy += maxReceive;

        if (buf != energy)
        {
            onEnergyChanged();
        }

        return maxReceive;
    }


    public int setEnergy(int energy)
    {
        int buf = this.energy;
        this.energy = energy;
        if (buf != energy)
        {
            onEnergyChanged();
        }

        return energy;
    }

    @Override
    public int getEnergyStored() {
        return this.energy;
    }

    public abstract void onEnergyChanged();
}
