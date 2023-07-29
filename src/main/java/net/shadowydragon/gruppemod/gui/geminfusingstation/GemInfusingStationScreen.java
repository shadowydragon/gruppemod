package net.shadowydragon.gruppemod.gui.geminfusingstation;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.gui.renderer.EnergyInfoArea;
import net.shadowydragon.gruppemod.util.MouseUtil;

import java.util.List;
import java.util.Optional;

public class GemInfusingStationScreen extends AbstractContainerScreen<GemInfusingStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(GruppeMod.MOD_ID,
    "textures/gui/gem_infusing_station_gui.png");

    private EnergyInfoArea energyInfoArea;
    public GemInfusingStationScreen(GemInfusingStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea() {
        int x = (width-imageWidth) / 2;
        int y = (height-imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x+156, y+13, menu.blockEntity.getEnergyStorage());
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float particleTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width-imageWidth) / 2;
        int y = (height-imageHeight) / 2;

        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(graphics,x,y);
        energyInfoArea.draw(graphics);

    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {

        int x = (width-imageWidth) / 2;
        int y = (height-imageHeight) / 2;

        renderAreaToolTips(graphics, mouseX,mouseY, x,y);

    }

/*    @Override
    protected List<Component> getTooltipFromContainerItem(ItemStack p_283689_) {
        return energyInfoArea.getTooltips();
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int x, int y) {


        graphics.renderTooltip(this.font, energyInfoArea.getTooltips(), Optional.empty(), x, y);
    }*/

    private void renderAreaToolTips(GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, 156, 13, 8, 64)) {
           // this.renderTooltip(graphics, mouseX - x, mouseY - y);
            graphics.renderTooltip(this.font,energyInfoArea.getTooltips(),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    private void renderProgressArrow(GuiGraphics graphics, int x, int y)
    {
        if (menu.isCrafting())
        {
            graphics.blit(TEXTURE, x+105, y+33, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
