package net.aaronterry.helper;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;

public class LargeMap extends KeyGroup {
    private static KeyGroup[] keysValue;

    public LargeMap(Object[] inputs, Object[] outputs) {
        super(inputs, outputs);
        keysValue = new KeyGroup[] {};
    }

    public LargeMap addKeysValue(Object[] keys, Object value) {
        int len = keysValue.length;
        keysValue = Arrays.copyOf(keysValue,  len + 1);
        keysValue[len] = new KeyGroup(keys, value);
        return this;
    }

    @Override
    public Object get(Object input) {
        Object result = super.get(input);
        if (result != null) return result;
        for (KeyGroup keyGroup : keysValue) {
            result = keyGroup.get(input);
            if (result != null) return result;
        }
        return result;
    }

    @Override
    public boolean contains(Object input) {
        boolean contains = super.contains(input);
        if (contains) return contains;
        for (KeyGroup keyGroup : keysValue) {
            contains = keyGroup.contains(input);
            if (contains) return contains;
        }
        return false;
    }
}
