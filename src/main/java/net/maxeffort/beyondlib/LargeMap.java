package net.maxeffort.helper;

import java.util.ArrayList;
import java.util.List;

public class LargeMap<I> {
    private final List<KeyGroup<I,I>> keysValue = new ArrayList<>();
    private final I[] inputs;
    private I[] outputs;

    @SafeVarargs
    public LargeMap(I... inputs) {
        this.inputs = inputs;
    }

    @SafeVarargs
    public final LargeMap<I> out(I... outputs) {
        this.outputs = outputs;
        return this;
    }

    @SafeVarargs
    public final LargeMap<I> addKeysValue(I value, I... keys) {
        keysValue.add(new KeyGroup<I,I>(keys).out(value));
        return this;
    }

    private I getFromKey(I input) {
        for (int i = 0; i < inputs.length; i++) {
            if (input.equals(inputs[i])) return outputs[i];
        }
        return null;
    }

    public I get(I input) {
        I result = getFromKey(input);
        if (result != null) return result;
        for (KeyGroup<I,I> keyGroup : keysValue) {
            result = keyGroup.get(input);
            if (result != null) return result;
        }
        return null;
    }

    public boolean contains(I input) {
        for (I i : inputs) {
            if (input.equals(i)) return true;
        }
        for (KeyGroup<I,I> keyGroup : keysValue) {
            if(keyGroup.contains(input)) return true;
        }
        return false;
    }

}
