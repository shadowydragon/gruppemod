package net.shadowydragon.gruppemod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowydragon.gruppemod.GruppeMod;

public class ModFoodItems {
    public static final DeferredRegister<Item> FOODS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GruppeMod.MODID);

    public static void register(IEventBus eventBus)
    {
        FOODS.register(eventBus);
    }

    public static final RegistryObject<Item> STRAWBERRY = FOODS.register("strawberry",
            ()->new Item(new Item.Properties().food(ModFoodProperties.STRAWBERRY)));
}
