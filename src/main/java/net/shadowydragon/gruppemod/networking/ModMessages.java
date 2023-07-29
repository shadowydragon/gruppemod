package net.shadowydragon.gruppemod.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.networking.packet.DrinkWaterClientToServerPacket;
import net.shadowydragon.gruppemod.networking.packet.EnergyDataSyncServerToClientPacket;
import net.shadowydragon.gruppemod.networking.packet.ThirstDataSyncServerToClientPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int packetID()
    {
        return packetID++;
    }

    public static void register()
    {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(GruppeMod.MOD_ID, "packages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(DrinkWaterClientToServerPacket.class, packetID(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrinkWaterClientToServerPacket::new)
                .encoder(DrinkWaterClientToServerPacket::toBytes)
                .consumerMainThread(DrinkWaterClientToServerPacket::handle)
                .add();

        net.messageBuilder(ThirstDataSyncServerToClientPacket.class, packetID(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ThirstDataSyncServerToClientPacket::new)
                .encoder(ThirstDataSyncServerToClientPacket::toBytes)
                .consumerMainThread(ThirstDataSyncServerToClientPacket::handle)
                .add();

        net.messageBuilder(EnergyDataSyncServerToClientPacket.class, packetID(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergyDataSyncServerToClientPacket::new)
                .encoder(EnergyDataSyncServerToClientPacket::toBytes)
                .consumerMainThread(EnergyDataSyncServerToClientPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message)
    {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClient(MSG message)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
