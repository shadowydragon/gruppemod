package net.shadowydragon.gruppemod.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.item.custom.MetalDetectorItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GruppeMod.MODID);

    //This is an Example how you crate a new Item
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("itemname",
            ()-> new Item(
                    new Item.Properties()
                    .stacksTo(32)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            ()->new Item(new Item.Properties()));

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            ()->new Item(new Item.Properties()));


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
