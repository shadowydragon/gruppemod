package net.shadowydragon.gruppemod.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import net.shadowydragon.gruppemod.client.ClientThirstData;
import net.shadowydragon.gruppemod.thirst.PlayerThirstProvider;

import java.util.function.Supplier;

public class ThirstDataSyncServerToClientPacket {
    private final int thirst;



    public ThirstDataSyncServerToClientPacket(int thirst) {
        this.thirst = thirst;
    }

    public ThirstDataSyncServerToClientPacket(FriendlyByteBuf buf) {
        this.thirst = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(thirst);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
           //Here we are on the client

            ClientThirstData.setPlayerThirst(thirst);
        });

        return true;
    }


}
