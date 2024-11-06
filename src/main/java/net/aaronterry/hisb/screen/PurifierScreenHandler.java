package net.aaronterry.hisb.screen;

import net.aaronterry.helper.KeyGroup;
import net.aaronterry.hisb.block.entity.PurifierBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class PurifierScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;

    protected PurifierScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(2));
    }

    public PurifierScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.PURIFIER_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.getWorld();
        this.addSlot(new HotSlot(inventory, 0, 44, 10));
        this.addSlot(new WetSlot(inventory, 1, 116, 10));
        this.addSlot(new Slot(inventory, 2, 80, 17));
        this.addSlot(new Slot(inventory, 3, 80, 59));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) { this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142)); }
        this.addProperties(propertyDelegate);
    }

    public PurifierScreenHandler(int i, PlayerInventory playerInventory, Object o) {
        this(i, playerInventory);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the width in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (!slot.hasStack()) return newStack; // slot doesn't have anything in it

        ItemStack originalStack = slot.getStack();
        newStack = originalStack.copy();
        int playerInventoryStart = 4;
        int playerInventoryEnd = playerInventoryStart + 36;

        if (slotIndex >= playerInventoryStart && slotIndex < playerInventoryEnd) {
            if (tryInsertItem(originalStack, 0, PurifierBlockEntity.HOT_GROUP) &&
                tryInsertItem(originalStack, 1, PurifierBlockEntity.WET_GROUP) &&
                tryInsertItem(originalStack, 2, PurifierBlockEntity.IMPURE_GROUP)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!this.insertItem(originalStack, playerInventoryStart, playerInventoryEnd, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (originalStack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }

        return newStack;
    }

    private boolean tryInsertItem(ItemStack stack, int slotIndex, KeyGroup group) {
        Slot targetSlot = this.slots.get(slotIndex); ItemStack slotStack = targetSlot.getStack();
        boolean SUCCESS = false; boolean FAILURE = true;
        if (group.contains(stack.getItem())) {
            if (slotStack.isEmpty()) { targetSlot.setStack(stack.split(stack.getCount())); return SUCCESS;
            } else if (slotStack.isOf(stack.getItem())) {
                int maxCount = Math.min(slotStack.getMaxCount(), targetSlot.getMaxItemCount(stack));
                int transferCount = Math.min(stack.getCount(), maxCount - slotStack.getCount());
                slotStack.increment(transferCount); stack.decrement(transferCount); return SUCCESS;
            }
        }
        return FAILURE;
    }

    @Override public boolean canUse(PlayerEntity player) { return this.inventory.canPlayerUse(player); }

    protected static class HotSlot extends Slot{
        public HotSlot(Inventory inventory, int index, int x, int y) { super(inventory, index, x, y);}
        @Override public boolean canInsert(ItemStack stack) { return PurifierBlockEntity.HOT_GROUP.contains(stack.getItem()) || stack.isOf(Items.BUCKET); }
        @Override public int getMaxItemCount(ItemStack stack) { return stack.isOf(Items.BUCKET) ? 1 : super.getMaxItemCount(stack); }
    }
    protected static class WetSlot extends Slot{
        public WetSlot(Inventory inventory, int index, int x, int y) { super(inventory, index, x, y);}
        @Override public boolean canInsert(ItemStack stack) { return PurifierBlockEntity.WET_GROUP.contains(stack.getItem()) || stack.isOf(Items.BUCKET); }
        @Override public int getMaxItemCount(ItemStack stack) { return stack.isOf(Items.BUCKET) ? 1 : super.getMaxItemCount(stack); }
    }

}
