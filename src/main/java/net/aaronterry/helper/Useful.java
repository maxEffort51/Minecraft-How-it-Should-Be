package net.aaronterry.helper;

import net.minecraft.block.Block;
import net.minecraft.item.*;

public class Useful {
    public static void doIf(Runnable yes, Runnable no, boolean maybe) { if (maybe) { yes.run(); } else { no.run(); } }

    public static boolean toggle(Runnable yes, Runnable no, boolean previous) { doIf(yes, no, !previous); return !previous; }

    public static Item[] append(Item[] arr, Item item) { Item[] combined = new Item[arr.length + 1]; System.arraycopy(arr, 0, combined, 0, combined.length - 1); combined[combined.length-1] = item; return combined; }
    public static Block[] append(Block[] arr, Block item) { Block[] combined = new Block[arr.length + 1]; System.arraycopy(arr, 0, combined, 0, combined.length - 1); combined[combined.length-1] = item; return combined; }

    private static int getCount(Object[] arr1, Object[][] arrays) { int count = arr1.length; for (Object[] array : arrays) { count += array.length; } return count; }

    public static Block[] combine(Block[] arr1, Block[]... arrays) {
        int total = Useful.getCount(arr1, arrays); Block[] combined = new Block[total];
        int cumulative = arr1.length; System.arraycopy(arr1, 0, combined, 0, arr1.length);
        for (Block[] array : arrays) { System.arraycopy(array, 0, combined, cumulative, array.length); cumulative += array.length; }
        return combined;
    }
    public static Item[] combine(Item[] arr1, Item[]... arrays) {
        int total = Useful.getCount(arr1, arrays); Item[] combined = new Item[total];
        int cumulative = arr1.length; System.arraycopy(arr1, 0, combined, 0, arr1.length);
        for (Item[] array : arrays) { System.arraycopy(array, 0, combined, cumulative, array.length); cumulative += array.length; }
        return combined;
    }
}
