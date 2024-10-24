package net.aaronterry.helper.customtable;

import net.aaronterry.helper.KeyGroup;
import net.aaronterry.hisb.block.entity.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private DefaultedList<ItemStack> inventory;
    protected final PropertyDelegate propertyDelegate;
    private String name;
    private KeyGroup[] inputs;
    private KeyGroup output;
    private String id;
    private int progress = 0;
    private KeyGroup progressPoints;
    private int maxProgress;
    private CustomTableBlock.Builder.TickSettings settings;
    private String textureLocation;
    private final int INPUT = 0;
    private int OUTPUT;
    private KeyGroup[] positions;

    public CustomTableBlockEntity(BlockPos pos, BlockState state) {
        super(CustomTableBlock.ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int size() {
                return 0;
            }
        };
    }

    public CustomTableBlockEntity(CustomTableBlock.Builder builder, CustomTableBlock block, BlockPos pos, BlockState state) {
        super(CustomTableBlock.ENTITY, pos, state);
        this.inputs = (KeyGroup[]) builder.getVariable("inputs");
        inventory = DefaultedList.ofSize(this.inputs.length + 1, ItemStack.EMPTY);
        this.OUTPUT = this.inputs.length;

        this.progressPoints = (KeyGroup) builder.getVariable("progress");
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                if (index >= this.size() - 1) {
                    return CustomTableBlockEntity.this.maxProgress;
                }
                return (int) CustomTableBlockEntity.this.progressPoints.atIndex(index);
            }

            @Override
            public void set(int index, int value) {
                if (index >= this.size() - 1) {
                    CustomTableBlockEntity.this.maxProgress = value;
                    return;
                }
                CustomTableBlockEntity.this.progressPoints.setAtIndex(index, value);
            }

            @Override
            public int size() {
                return CustomTableBlockEntity.this.progressPoints.count() + 1;
            }
        };
        this.positions = (KeyGroup[]) builder.getVariable("pos");
        this.name = (String) builder.getVariable("name");
        this.id = (String) builder.getVariable("id");
        this.settings = (CustomTableBlock.Builder.TickSettings) builder.getVariable("settings");
        this.textureLocation = (String) builder.getVariable("textureLocation");
        this.maxProgress = (int) builder.getVariable("maxProgress");
    }

    //private DefaultedList<ItemStack> inventory;
    //    protected final PropertyDelegate propertyDelegate;
    //    private String name;
    //    private KeyGroup[] inputs;
    //    private KeyGroup output;
    //    private String id;
    //    private int progress = 0;
    //    private KeyGroup progressPoints;
    //    private int maxProgress;
    //    private CustomTableBlock.Builder.TickSettings settings;
    //    private String textureLocation;
    //    private final int INPUT = 0;
    //    private int OUTPUT;

    public Object getVariable(String identifier) {
        return switch(identifier) {
            case "inventory-size" -> inputs.length + 1;
            default -> null;
        };
    }

    public KeyGroup getPos(int index) {
        return positions[index];
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal(name);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt(id + ".progress", progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt(id + ".progress");
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (!settings.crafting) return;

        if (this.getStack(OUTPUT).isEmpty() || this.getStack(OUTPUT).getCount() < this.getStack(OUTPUT).getMaxCount()) {
            if (this.hasRecipe()) {
                this.progress++;
                markDirty(world, pos, state);
                checkProgressPoints();
                if (canCraft()) {
                    ItemStack result = getItemStack();
                    this.removeStack(OUTPUT, 1);
                    this.setStack(OUTPUT, new ItemStack(result.getItem(), getStack(OUTPUT).getCount() + result.getCount()));
                    this.progress = 0;
                }
            } else {
                this.progress = 0;
            }
        } else {
            this.progress = 0;
            markDirty(world, pos, state);
        }
    }

    private boolean hasRecipe() {
        boolean hasInput = inputs[INPUT].contains(getStack(INPUT)); // input is valid recipe-wise
        ItemStack result = getItemStack();
        boolean itemAvailable = this.getStack(OUTPUT).getItem() == result.getItem() || this.getStack(OUTPUT).isEmpty();
        boolean amountAvailable = this.getStack(OUTPUT).getCount() + result.getCount() <= getStack(OUTPUT).getMaxCount();
        return hasInput && filledIn() && amountAvailable && itemAvailable;
    }

    private ItemStack getItemStack() {
        Object maybeError = inputs[INPUT].tryGetValue(getStack(INPUT).getItem());
        if (maybeError == "Error") return ItemStack.EMPTY;
        ItemConvertible item = (ItemConvertible) maybeError;
        return new ItemStack(item);
    }

    private boolean filledIn() {
        boolean filled = true;
        for (int i = 1; i < inputs.length - 1; i++) {
            if (!inputs[i].contains(getStack(i))) return false;
        }
        return filled;
    }

    private void checkProgressPoints() {
        for (int i = 0; i < progressPoints.count(); i++) {
            if (progress == (int) progressPoints.get(i)) this.removeStack(i + 1,1);
        }
    }

    private boolean canCraft() { return this.progress >= this.maxProgress; }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CustomTableScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}

//    private static final int HOT_SLOT = 0;
//    private static final int WET_SLOT = 1;
//    private static final int IMPURE_SLOT = 2;
//    private static final int RESULT_SLOT = 3;
//
//    protected final PropertyDelegate propertyDelegate;
//    private int progress = 0;
//    private int hot_progress = 64;
//    private int wet_progress = 32;
//
//    @Override
//    public DefaultedList<ItemStack> getItems() {
//        return inventory;
//    }
//
//    @Override
//    public Text getDisplayName() {
//        return Text.literal("Purifier Table");
//    }
//
//    @Override
//    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
//        super.writeNbt(nbt, registryLookup);
//        Inventories.writeNbt(nbt, inventory, registryLookup);
//        nbt.putInt("purifier_table.progress", progress);
//    }
//
//    @Override
//    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
//        super.readNbt(nbt, registryLookup);
//        Inventories.readNbt(nbt, inventory, registryLookup);
//        progress = nbt.getInt("purifier_table.progress");
//    }
//
//    @Override
//    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
//        return new PurifierScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
//    }
//
//    public void tick(World world, BlockPos pos, BlockState state) {
//        if(world.isClient()) {
//            return;
//        }
//
//        if(outputSlotAvailable()) {
//            if(this.hasRecipe()) {
//                this.increaseCraftProgress();
//                markDirty(world, pos, state);
//
//                if(hasCraftingFinished(0)) this.removeStack(HOT_SLOT, 1);
//
//                if(hasCraftingFinished()) {
//                    this.craftItem();
//                    this.resetProgress();
//                }
//            } else {
//                this.resetProgress();
//            }
//        } else {
//            this.resetProgress();
//            markDirty(world, pos, state);
//        }
//    }
//
//    private void resetProgress() {
//        this.progress = 0;
//    }
//
//    private void craftItem() {
//        this.removeStack(WET_SLOT, 1);
//        this.removeStack(IMPURE_SLOT, 1);
//        ItemStack result = getItemStack();
//
//        this.setStack(RESULT_SLOT, new ItemStack(result.getItem(), getStack(RESULT_SLOT).getCount() + result.getCount()));
//    }
//
//    private boolean hasCraftingFinished() {
//        return progress >= wet_progress;
//    }
//
//    private boolean hasCraftingFinished(int slot) {
//        return switch(slot) {
//            case 0 -> progress >= hot_progress;
//            case 1 -> progress >= wet_progress;
//            default -> progress >= wet_progress;
//        };
//    }
//
//    private void increaseCraftProgress() {
//        progress++;
//    }
//
//    private ItemStack getItemStack() {
//        Object maybeError = PurifierTableBlock.PURIFY_RECIPES.safeGet(getStack(IMPURE_SLOT).getItem());
//        if (maybeError == "Error") {
//            return ItemStack.EMPTY;
//        }
//        ItemConvertible item = (ItemConvertible) maybeError;
//        return new ItemStack(item);
//    }
//
//    private boolean hasRecipe() {
//        boolean hasInput = PurifierTableBlock.PURIFY_RECIPES.contains(getStack(IMPURE_SLOT)); // input is valid recipe-wise
//        ItemStack result = getItemStack();
//
//        return hasInput && filledIn() && amountAvailable(result) && itemAvailable(result.getItem());
//    }
//
//    private boolean filledIn() {
//        return PurifierTableBlock.PURIFY_HOT_INPUTS.contains(getStack(HOT_SLOT)) && PurifierTableBlock.PURIFY_WET_INPUTS.contains(getStack(WET_SLOT));
//    }
//
//    private boolean itemAvailable(Item item) {
//        return this.getStack(RESULT_SLOT).getItem() == item || this.getStack(RESULT_SLOT).isEmpty();
//    }
//
//    private boolean amountAvailable(ItemStack result) {
//        return this.getStack(RESULT_SLOT).getCount() + result.getCount() <= getStack(RESULT_SLOT).getMaxCount();
//    }
//
//    private boolean outputSlotAvailable() {
//        return this.getStack(RESULT_SLOT).isEmpty() || this.getStack(RESULT_SLOT).getCount() < this.getStack(RESULT_SLOT).getMaxCount();
//    }