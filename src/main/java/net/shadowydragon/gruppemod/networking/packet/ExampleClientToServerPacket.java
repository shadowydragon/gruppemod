package net.shadowydragon.gruppemod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExampleClientToServerPacket {

    public ExampleClientToServerPacket() {

    }

    public ExampleClientToServerPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            //Here we are on the server
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getMaxHealth()*2);
            player.heal(player.getMaxHealth());

        });
        return true;
    }

}
