package net.shadowydragon.gruppemod.thirst;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public class PlayerThirst {
    private int thirst;
    private final int MIN_THIRST = 0;
    private final int MAX_THIRST = 10;

    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public void addThirst(int value)
    {
        thirst = Math.min(thirst + value, MAX_THIRST);
    }
    public void subThirst(int value)
    {
        thirst = Math.max(thirst - value, MIN_THIRST);
    }

    public int getMIN_THIRST() {
        return MIN_THIRST;
    }

    public int getMAX_THIRST() {
        return MAX_THIRST;
    }

    public void copyFrom(PlayerThirst source)
    {
        this.thirst = source.thirst;
    }

    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putInt("thirst", thirst);
    }

    public void loadNBTData(CompoundTag nbt)
    {
        thirst = nbt.getInt("thirst");
    }
}
