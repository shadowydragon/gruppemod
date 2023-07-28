package net.shadowydragon.gruppemod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_GRUPPE = "key.category.gruppemod.gruppe";

    public static final String KEY_DRINK_WATER = "key.gruppemod.drink_water";
    public static final String KEY_PLACEHOLDER = "key.gruppemod.place_holder";

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_GRUPPE);

    public static final KeyMapping PLACEHOLDER_KEY = new KeyMapping(KEY_PLACEHOLDER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, KEY_CATEGORY_GRUPPE);

}
