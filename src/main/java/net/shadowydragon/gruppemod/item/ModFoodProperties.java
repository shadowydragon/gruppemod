package net.shadowydragon.gruppemod.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder()
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1), 1)
            .fast()
            .build();
}
