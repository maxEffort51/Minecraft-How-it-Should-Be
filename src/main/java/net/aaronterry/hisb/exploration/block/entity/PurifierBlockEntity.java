package net.aaronterry.hisb.exploration.block.entity;

import net.aaronterry.helper.KeyGroup;
import net.aaronterry.helper.LargeMap;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.exploration.block.ModBlocks;
import net.aaronterry.hisb.exploration.item.ModItems;
import net.aaronterry.hisb.exploration.screen.PurifierScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PurifierBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {
    protected static final int HOT_SLOT = 0;
    protected static final int WET_SLOT = 1;
    protected static final int IMPURE_SLOT = 2;
    protected static final int PURE_SLOT = 3;
    public static final KeyGroup<Item,Object> HOT_GROUP = new KeyGroup<>(Items.LAVA_BUCKET, Items.BLAZE_ROD).keys();
    public static final KeyGroup<Item,Object> WET_GROUP = new KeyGroup<>(Items.POTION,Items.WATER_BUCKET).keys();
    public static final LargeMap<ItemConvertible> IMPURE_GROUP = new LargeMap<ItemConvertible>(
            Items.IRON_INGOT, Items.COPPER_INGOT, Items.LAPIS_LAZULI, Items.REDSTONE, Items.EMERALD, Items.DIAMOND,
            ModItems.ANCIENT_STAR, Items.OBSIDIAN, Items.REINFORCED_DEEPSLATE, Items.NETHERITE_SCRAP, Items.QUARTZ,
            ModItems.DEPNETUM_CLUMP, ModBlocks.DEAD_SCULK, ModBlocks.CORRUPTED_MUD, ModBlocks.HARDENED_SCULK, ModItems.PRISMALITE_SHARD,
            ModItems.BAKED_QUARTZ //, LAVA_BUCKET, COSMIUM, DEAD SCULK, WATER, CORRUPTED MUD, COBBLESTONE, HARDENED SCULK, DUST
    ).out(
            ModItems.PURIFIED_IRON, ModItems.PURIFIED_COPPER, ModItems.PURIFIED_LAPIS, ModItems.PURIFIED_REDSTONE, ModItems.PURIFIED_EMERALD, ModItems.PURIFIED_DIAMOND,
            Items.NETHER_STAR, Items.LAVA_BUCKET, Items.DEEPSLATE, ModItems.PURIFIED_SCRAP, ModItems.CRYSTALLINE_QUARTZ,
            ModItems.DEMANDUM_CHUNK, ModBlocks.STIFF_SOIL, Blocks.MUD, ModBlocks.IMPERVIUM_BLOCK, Items.PRISMARINE_SHARD,
            ModItems.CRYSTALLINE_QUARTZ
    ).addKeysValue(Items.GLASS, // STAINED GLASS -> GLASS
            Items.WHITE_STAINED_GLASS,Items.GRAY_STAINED_GLASS,Items.BROWN_STAINED_GLASS,Items.BLACK_STAINED_GLASS,
            Items.YELLOW_STAINED_GLASS,Items.GREEN_STAINED_GLASS,Items.ORANGE_STAINED_GLASS,Items.RED_STAINED_GLASS,
            Items.PURPLE_STAINED_GLASS,Items.PINK_STAINED_GLASS,Items.MAGENTA_STAINED_GLASS,Items.BLUE_STAINED_GLASS,
            Items.LIGHT_BLUE_STAINED_GLASS,Items.LIGHT_GRAY_STAINED_GLASS,Items.CYAN_STAINED_GLASS,Items.LIME_STAINED_GLASS,Items.TINTED_GLASS
    ).addKeysValue(Items.GLASS_PANE, // STAINED GLASS PANE -> GLASS PANE
            Items.WHITE_STAINED_GLASS_PANE,Items.GRAY_STAINED_GLASS_PANE,Items.BROWN_STAINED_GLASS_PANE,Items.BLACK_STAINED_GLASS_PANE,
            Items.YELLOW_STAINED_GLASS_PANE,Items.GREEN_STAINED_GLASS_PANE,Items.ORANGE_STAINED_GLASS_PANE,Items.RED_STAINED_GLASS_PANE,
            Items.PURPLE_STAINED_GLASS_PANE,Items.PINK_STAINED_GLASS_PANE,Items.MAGENTA_STAINED_GLASS_PANE,Items.BLUE_STAINED_GLASS_PANE,
            Items.LIGHT_BLUE_STAINED_GLASS_PANE,Items.LIGHT_GRAY_STAINED_GLASS_PANE,Items.CYAN_STAINED_GLASS_PANE,Items.LIME_STAINED_GLASS_PANE
    );
    public static final KeyGroup<Item,Integer> WATER_USAGE_AMOUNT = new KeyGroup<Item,Integer>(Items.POTION,Items.WATER_BUCKET).out(1,4);
    private int progress;
    private int maxProgress;
    private static int waterUsages;
    private static int maxWaterUsages;
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override public int get(int index) {
            return switch (index) {
                case 0 -> PurifierBlockEntity.this.progress;
                case 1 -> PurifierBlockEntity.this.maxProgress;
                default -> 0;
            };
        }
        @Override public void set(int index, int value) {
            switch (index) {
                case 0: PurifierBlockEntity.this.progress = value;
                case 1: PurifierBlockEntity.this.maxProgress = value;
                default:
            }
        }
        @Override public int size() { return 2; }
    };

    @Override
    public @Nullable PurifierScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        HisbMod.debug("Creating menu");
        return new PurifierScreenHandler(i, playerInventory, this, this.propertyDelegate);
    }

    public PurifierBlockEntity(BlockPos pos, BlockState state) { super(ModBlockEntities.PURIFIER_BLOCK_ENTITY, pos, state); maxProgress = 2000; maxWaterUsages = 4; }

    public void increment() { progress++; }
    public void reset() { progress = 0; }
    public boolean isFinished() { return progress >= maxProgress; }
    public void updateProgress() { propertyDelegate.set(0, progress); }

    @Override public int size() { return 4; }
    @Override public boolean isEmpty() { return false; }
    @Override public ItemStack getStack(int slot) { return inventory.get(slot); }
    @Override public ItemStack removeStack(int slot, int amount) { return null; }
    @Override public ItemStack removeStack(int slot) { return inventory.remove(slot); }
    @Override public void setStack(int slot, ItemStack stack) { inventory.set(slot, stack); }
    @Override public boolean canPlayerUse(PlayerEntity player) { return true; }

    @Override protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        nbt.putInt("purifier_table.progress",progress);
    }

    @Override protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("purifier_table.progress");
    }

    public static void tick(World world, BlockPos pos, BlockState state, PurifierBlockEntity blockEntity) {
        if (world.isClient()) return;
        if (slotsAreFilled(blockEntity.inventory)) {
            if (outputSlotAvailable(blockEntity.inventory)) {
                blockEntity.increment();
                if (blockEntity.isFinished()) craftItem(blockEntity.inventory);
                blockEntity.markDirty();
            } else blockEntity.reset();
        } else blockEntity.reset();
        blockEntity.updateProgress();
    }

    private static void craftItem(DefaultedList<ItemStack> inventory) {
        ItemStack PURE = inventory.get(PURE_SLOT); ItemStack IMPURE = inventory.get(IMPURE_SLOT); Item result = (Item) IMPURE_GROUP.get(IMPURE.getItem());
        ItemStack HOT = inventory.getFirst(); ItemStack WET = inventory.get(WET_SLOT);
        if (HOT.isOf(Items.LAVA_BUCKET)) { inventory.set(HOT_SLOT,new ItemStack(Items.BUCKET)); } else { HOT.decrement(1); }
        // DEPLETE WATER
        maxWaterUsages = (int) WATER_USAGE_AMOUNT.get(WET.getItem());
        if (waterUsages > maxWaterUsages) {
            if (WET.isOf(Items.WATER_BUCKET)) { inventory.set(WET_SLOT,new ItemStack(Items.BUCKET)); } else { WET.decrement(1); }
            waterUsages = 0;
        } else waterUsages++;

        IMPURE.decrement(1);
        if (PURE.isEmpty()) { inventory.set(3, new ItemStack(result));
        } else if (ItemStack.areItemsAndComponentsEqual(PURE, new ItemStack(result))) { PURE.increment(1); }
    }

    private static boolean outputSlotAvailable(DefaultedList<ItemStack> inventory) {
        ItemStack PURE = inventory.get(PURE_SLOT); ItemStack IMPURE = inventory.get(IMPURE_SLOT); Item result = (Item) IMPURE_GROUP.get(IMPURE.getItem());
        boolean k = PURE.isOf(result) && PURE.getCount() < PURE.getMaxCount();
        return k || PURE.isEmpty();
    }

    private static boolean slotsAreFilled(DefaultedList<ItemStack> inventory) {
        Item HOT = inventory.getFirst().getItem(); Item WET = inventory.get(WET_SLOT).getItem(); Item IMPURE = inventory.get(IMPURE_SLOT).getItem();
        boolean areFilled = HOT_GROUP.contains(HOT) && WET_GROUP.contains(WET) && IMPURE_GROUP.contains(IMPURE);
        if (IMPURE_GROUP.get(IMPURE) instanceof BucketItem) {
            areFilled = areFilled && inventory.get(PURE_SLOT).isOf(Items.BUCKET);
        }
        return areFilled;
    }

    @Override
    public Text getDisplayName() { return Text.literal("Purifier Table"); }

    @Override
    public void clear() { inventory.clear(); }
}