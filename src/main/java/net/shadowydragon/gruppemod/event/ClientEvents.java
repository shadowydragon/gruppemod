package net.shadowydragon.gruppemod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.networking.ModMessages;
import net.shadowydragon.gruppemod.networking.packet.DrinkWaterClientToServerPacket;
import net.shadowydragon.gruppemod.networking.packet.ExampleClientToServerPacket;
import net.shadowydragon.gruppemod.util.KeyBinding;

import java.util.logging.Logger;

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
    }

}
