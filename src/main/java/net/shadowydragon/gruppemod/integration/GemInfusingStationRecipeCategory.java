package net.shadowydragon.gruppemod.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.shadowydragon.gruppemod.GruppeMod;
import net.shadowydragon.gruppemod.block.ModCustomModelBlocks;
import net.shadowydragon.gruppemod.recipe.GemInfusingStationRecipe;

public class GemInfusingStationRecipeCategory implements IRecipeCategory<GemInfusingStationRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(GruppeMod.MOD_ID, "gem_infusing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(GruppeMod.MOD_ID, "textures/gui/gem_infusing_station_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public GemInfusingStationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModCustomModelBlocks.GEM_INFUSING_STATION.get()));
    }


    @Override
    public RecipeType<GemInfusingStationRecipe> getRecipeType() {
        return JEIGruppeModPlugin.INFUSION_TYPE;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("gruppemod.jeicategory.gem_infusing_station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GemInfusingStationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 86, 15).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 86, 60).addItemStack(recipe.getResultItem(null));


    }
}
