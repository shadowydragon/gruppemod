package net.shadowydragon.gruppemod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.shadowydragon.gruppemod.GruppeMod;

public class ThirstHudOverlay {
    private static final ResourceLocation FILLED_THIRST = new ResourceLocation(GruppeMod.MOD_ID,
            "textures/hud/thirst/filled_thirst.png");
    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(GruppeMod.MOD_ID,
            "textures/hud/thirst/empty_thirst.png");

    public static final IGuiOverlay HUD_THIRST = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth/2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, EMPTY_THIRST);

        for(int i = 0; i < 10; i++) {
/*            guiGraphics.blit(x - 94 + (i * 9),y - 54,0,0,12,12,
                    12,12);*/
            guiGraphics.blit(EMPTY_THIRST, x + 8 + (i * 8), y -51, 0, 0, 13, 12,
                    13, 12);
        }


        RenderSystem.setShaderTexture(0, FILLED_THIRST);
        for(int i = 0; i < 10; i++) {
            if (ClientThirstData.getPlayerThirst() > i)
            {
                guiGraphics.blit(FILLED_THIRST, x + 8 + (i * 8), y -51, 0, 0, 13, 12,
                        13, 12);
            }
            else
            {
                break;
            }
        }

    });
}
