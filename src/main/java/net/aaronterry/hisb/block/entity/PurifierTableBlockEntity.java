package net.aaronterry.hisb.block.entity;

import net.aaronterry.hisb.block.custom.PurifierTableBlock;
import net.aaronterry.hisb.screen.PurifierScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PurifierTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int HOT_SLOT = 0;
    private static final int WET_SLOT = 1;
    private static final int IMPURE_SLOT = 2;
    private static final int RESULT_SLOT = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int hot_progress = 64;
    private int wet_progress = 32;

    public PurifierTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PURIFIER_TABLE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                PurifierTableBlockEntity thisEntity = PurifierTableBlockEntity.this;
                return switch(index) {
                    case 0 -> thisEntity.progress;
                    case 1 -> thisEntity.hot_progress;
                    case 2 -> thisEntity.wet_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                PurifierTableBlockEntity thisEntity = PurifierTableBlockEntity.this;
                switch(index) {
                    case 0 -> thisEntity.progress = value;
                    case 1 -> thisEntity.hot_progress = value;
                    case 2 -> thisEntity.wet_progress = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Purifier Table");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("purifier_table.progress", progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("purifier_table.progress");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PurifierScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }

        if(outputSlotAvailable()) {
            if(this.hasRecipe()) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if(hasCraftingFinished(0)) this.removeStack(HOT_SLOT, 1);

                if(hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        this.removeStack(WET_SLOT, 1);
        this.removeStack(IMPURE_SLOT, 1);
        ItemStack result = getItemStack();

        this.setStack(RESULT_SLOT, new ItemStack(result.getItem(), getStack(RESULT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasCraftingFinished() {
        return progress >= wet_progress;
    }

    private boolean hasCraftingFinished(int slot) {
        return switch(slot) {
            case 0 -> progress >= hot_progress;
            case 1 -> progress >= wet_progress;
            default -> progress >= wet_progress;
        };
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private ItemStack getItemStack() {
        Object maybeError = PurifierTableBlock.PURIFY_RECIPES.safeGet(getStack(IMPURE_SLOT).getItem());
        if (maybeError == "Error") {
            return ItemStack.EMPTY;
        }
        ItemConvertible item = (ItemConvertible) maybeError;
        return new ItemStack(item);
    }

    private boolean hasRecipe() {
        boolean hasInput = PurifierTableBlock.PURIFY_RECIPES.contains(getStack(IMPURE_SLOT)); // input is valid recipe-wise
        ItemStack result = getItemStack();

        return hasInput && filledIn() && amountAvailable(result) && itemAvailable(result.getItem());
    }

    private boolean filledIn() {
        return PurifierTableBlock.PURIFY_HOT_INPUTS.contains(getStack(HOT_SLOT)) && PurifierTableBlock.PURIFY_WET_INPUTS.contains(getStack(WET_SLOT));
    }

    private boolean itemAvailable(Item item) {
        return this.getStack(RESULT_SLOT).getItem() == item || this.getStack(RESULT_SLOT).isEmpty();
    }

    private boolean amountAvailable(ItemStack result) {
        return this.getStack(RESULT_SLOT).getCount() + result.getCount() <= getStack(RESULT_SLOT).getMaxCount();
    }

    private boolean outputSlotAvailable() {
        return this.getStack(RESULT_SLOT).isEmpty() || this.getStack(RESULT_SLOT).getCount() < this.getStack(RESULT_SLOT).getMaxCount();
    }
}
