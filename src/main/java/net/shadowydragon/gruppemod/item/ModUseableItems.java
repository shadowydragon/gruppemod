package net.shadowydragon.gruppemod.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.item.custom.MetalDetectorItem;

public class ModUseableItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GruppeMod.MODID);


    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(20)
                    .rarity(Rarity.UNCOMMON)
                    .setNoRepair()
            ));



    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
