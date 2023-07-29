package net.shadowydragon.gruppemod.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.shadowydragon.gruppemod.block.entity.GemInfusingStationBlockEntity;
import net.shadowydragon.gruppemod.client.ClientThirstData;
import net.shadowydragon.gruppemod.gui.geminfusingstation.GemInfusingStationMenu;

import java.util.function.Supplier;

public class EnergyDataSyncServerToClientPacket {
    private final int energy;
    private final BlockPos pos;




    public EnergyDataSyncServerToClientPacket(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public EnergyDataSyncServerToClientPacket(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
           //Here we are on the client

            if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof GemInfusingStationBlockEntity blockEntity)
            {
                blockEntity.setEnergyLevel(energy);

                if (Minecraft.getInstance().player.containerMenu instanceof GemInfusingStationMenu menu &&
                menu.getBlockEntity().getBlockPos().equals(pos))
                {
                    blockEntity.setEnergyLevel(energy);
                }
            }
        });

        return true;
    }


}
