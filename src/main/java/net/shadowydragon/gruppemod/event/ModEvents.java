package net.shadowydragon.gruppemod.event;


import net.minecraft.ChatFormatting;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.networking.ModMessages;
import net.shadowydragon.gruppemod.networking.packet.ThirstDataSyncServerToClientPacket;
import net.shadowydragon.gruppemod.thirst.PlayerThirst;
import net.shadowydragon.gruppemod.thirst.PlayerThirstProvider;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = GruppeMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                event.addCapability(new ResourceLocation(GruppeMod.MODID, "propertiesthirst"), new PlayerThirstProvider());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> newStore.copyFrom(oldStore));
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);
    }


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //TODO: Rework the calculation when the player lose thirst
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                if(thirst.getThirst() > 0 && event.player.getRandom().nextFloat() < 0.005f) { // Once Every 10 Seconds on Avg
                    thirst.subThirst(1);

                    ModMessages.sendToPlayer(new ThirstDataSyncServerToClientPacket(thirst.getThirst()), (ServerPlayer) event.player);
                }
            });
        }
    }



    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide)
        {
            if (event.getEntity() instanceof ServerPlayer player)
            {
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    ModMessages.sendToPlayer(new ThirstDataSyncServerToClientPacket(thirst.getThirst()), player);
                });
            }
        }
    }
}
