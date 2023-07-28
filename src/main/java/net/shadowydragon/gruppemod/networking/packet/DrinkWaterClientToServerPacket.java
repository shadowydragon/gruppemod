package net.shadowydragon.gruppemod.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrinkWaterClientToServerPacket {

    private static final String MESSAGE_DRINK_WATER = "message.gruppemod.drink_water";
    private static final String MESSAGE_NO_DRINK_WATER = "message.gruppemod.no_drink_water";

    public DrinkWaterClientToServerPacket() {

    }

    public DrinkWaterClientToServerPacket(FriendlyByteBuf buf) {

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

            //Check if player is near water
            if (hasWaterAroundThem(player, level, 1))
            {
                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_WATER).withStyle(ChatFormatting.DARK_AQUA));

                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                //TODO increase thirstlevel
            }
            else
            {
                player.sendSystemMessage(Component.translatable(MESSAGE_NO_DRINK_WATER).withStyle(ChatFormatting.DARK_RED));
                //TODO show the thurstlevel
            }


        });
        return true;
    }

    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size)
    {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(Blocks.WATER)).toArray().length > 0 ||
                level.getBlockStates(player.getBoundingBox().inflate(2))
                        .filter(state -> state.is(Blocks.WATER_CAULDRON)).toArray().length > 0;
    }

}
