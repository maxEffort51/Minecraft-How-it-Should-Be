package net.aaronterry.helper.block.table;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {
    private int progress;
    private int maxProgress;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override public int get(int index) {
            return switch (index) {
                case 0 -> TableBlockEntity.this.progress;
                case 1 -> TableBlockEntity.this.maxProgress;
                default -> 0;
            };
        }
        @Override public void set(int index, int value) {
            switch (index) {
                case 0: TableBlockEntity.this.progress = value;
                case 1: TableBlockEntity.this.maxProgress = value;
                default:
            }
        }
        @Override public int size() { return 2; }
    };

    public TableBlockEntity(BlockPos pos, BlockState state) { super(TableVariables.TABLE_ENTITY, pos, state); }

    @Override public int size() { return 0; }
    @Override public boolean isEmpty() { return false; }
    @Override public ItemStack getStack(int slot) { return null; }
    @Override public ItemStack removeStack(int slot, int amount) { return null; }
    @Override public ItemStack removeStack(int slot) { return null; }
    @Override public void setStack(int slot, ItemStack stack) { }
    @Override public boolean canPlayerUse(PlayerEntity player) { return false; }

    @Override public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        // NEEDS: Table Screen Handler + this.propertyDelegate

        // new PurifierScreenHandler(i, playerInventory, this, this.propertyDelegate);
        return null;
    }

    // NEEDS: inventory, inputs / outputs, etc.

    // NEEDS: read / write Nbt

    // NEEDS: tick function
    public static void tick(World world, BlockPos blockPos, BlockState blockState, TableBlockEntity tableBlockEntity) {
        // yay
    }

    @Override public Text getDisplayName() {
        // NEEDS: Display name
        return null;
    }

    @Override public void clear() {  }
}
