package net.shadowydragon.gruppemod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.item.custom.EnergyItems;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GruppeMod.MOD_ID);

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
            ()->new EnergyItems(new Item.Properties()){
                @Override
                public int energyValue() {
                    return 100;
                }
            });



    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
