package net.shadowydragon.gruppemod.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.client.ThirstHudOverlay;
import net.shadowydragon.gruppemod.networking.ModMessages;
import net.shadowydragon.gruppemod.networking.packet.DrinkWaterClientToServerPacket;
import net.shadowydragon.gruppemod.util.KeyBinding;


public class ClientEvents {
    @Mod.EventBusSubscriber(modid = GruppeMod.MODID, value = Dist.CLIENT)

    public static class ClientForgeEvents{
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            //if (event.getKey() == KeyBinding.DRINKING_KEY.getKey().getValue())
            if (KeyBinding.DRINKING_KEY.consumeClick())
            {
                ModMessages.sendToServer(new DrinkWaterClientToServerPacket());
            }
            if (KeyBinding.PLACEHOLDER_KEY.consumeClick())
            {

            }
        }
    }

    @Mod.EventBusSubscriber(modid = GruppeMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.PLACEHOLDER_KEY);
        }

        @SubscribeEvent
        public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("thirst", ThirstHudOverlay.HUD_THIRST);
        }
    }

}
