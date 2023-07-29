package net.shadowydragon.gruppemod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.shadowydragon.gruppemod.block.custom.GemInfusingStationBlock;
import net.shadowydragon.gruppemod.gui.geminfusingstation.GemInfusingStationMenu;
import net.shadowydragon.gruppemod.interfaces.ContainsEnergy;
import net.shadowydragon.gruppemod.item.ModItems;
import net.shadowydragon.gruppemod.networking.ModMessages;
import net.shadowydragon.gruppemod.networking.packet.EnergyDataSyncServerToClientPacket;
import net.shadowydragon.gruppemod.recipe.GemInfusingStationRecipe;
import net.shadowydragon.gruppemod.recipe.ModRecipes;
import net.shadowydragon.gruppemod.util.ModEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class GemInfusingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {


            return switch (slot){
                case 0 -> stack.getItem() == ModItems.RAW_SAPPHIRE.get() || true;
                case 1 -> stack.getItem() == ModItems.SAPPHIRE.get() || true;
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClient(new EnergyDataSyncServerToClientPacket(this.energy, getBlockPos()));

        }
    };

    private static final int ENERGY_REQ = 32;

    LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 0 || index == 1,
                            (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));

    private int progress = 0;
    private int maxProgress = 78;
    protected final ContainerData data;

    public GemInfusingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GEM_INFUSING_STATION_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 ->GemInfusingStationBlockEntity.this.progress;
                    case 1 -> GemInfusingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 ->GemInfusingStationBlockEntity.this.progress = pValue;
                    case 1 -> GemInfusingStationBlockEntity.this.maxProgress = pValue;
                };
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gruppemod.entitytitel.gem_infusing_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GemInfusingStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ENERGY)
        {
            return lazyEnergyHandler.cast();
        }



        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(GemInfusingStationBlock.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {

        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("gem_infusing_station.progress", this.progress);
        pTag.putInt("gem_infusing_station.energy", this.ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.progress = pTag.getInt("gem_infusing_station.progress");
        this.ENERGY_STORAGE.setEnergy(pTag.getInt("gem_infusing_station.energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, GemInfusingStationBlockEntity entity) {
        if (level.isClientSide)
        {
            return;
        }

        if (hasEnergyItemInFirstSlot(entity))
        {
            if (entity.itemHandler.getStackInSlot(0).getItem() instanceof ContainsEnergy energyItem
            && entity.ENERGY_STORAGE.getEnergyStored() + energyItem.energyValue() <= entity.ENERGY_STORAGE.getMaxEnergyStored())
            {
                int energy = energyItem.energyValue();
                entity.ENERGY_STORAGE.receiveEnergy(energy, false);
                entity.itemHandler.extractItem(0, 1, false);
            }

        }


        if (hasRecipe(entity) && hasEnoughEnergy(entity) && !hasEnergyItemInFirstSlot(entity))
        {
            entity.progress++;
            extractEnergy(entity);
            setChanged(level, blockPos, state);


            if (entity.progress >= entity.maxProgress)
            {
                craftItem(entity);
            }
        }else {
            entity.resetProgress();
            setChanged(level, blockPos, state);
        }
    }

    private static void extractEnergy(GemInfusingStationBlockEntity entity) {
        entity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(GemInfusingStationBlockEntity entity) {

        return entity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * entity.maxProgress + 10; //- (++entity.progress);
    }

    private static boolean hasEnergyItemInFirstSlot(GemInfusingStationBlockEntity entity) {

        return entity.itemHandler.getStackInSlot(0).getCount() > 0;

    }

    private static void craftItem(GemInfusingStationBlockEntity entity) {

        Level level1 = entity.getLevel();

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional< GemInfusingStationRecipe> recipe = level1.getRecipeManager().getRecipeFor(
                GemInfusingStationRecipe.Type.INSTANCE, inventory, level1);

        if (hasRecipe(entity))
        {
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem(null).getItem(),
                    entity.itemHandler.getStackInSlot(2).getCount() +
                    recipe.get().getResultItem(null).getCount()));

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean hasRecipe(GemInfusingStationBlockEntity entity) {

        Level level1 = entity.getLevel();

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional< GemInfusingStationRecipe> recipe = level1.getRecipeManager().getRecipeFor(
                GemInfusingStationRecipe.Type.INSTANCE, inventory, level1);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem(null));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).isEmpty() || inventory.getItem(2).is(itemStack.getItem());
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }
}
