package net.aaronterry.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyGroup<I,O> {
    private final I[] inputs;
    private O[] outputs;
    private O output;
    private String type;

    @SafeVarargs
    public KeyGroup(I... inputs) {
        this.inputs = inputs;
    }

    @SafeVarargs
    public final KeyGroup<I,O> out(O... outputs) {
        this.outputs = outputs;
        this.type = "key-value";
        return this;
    }

    public KeyGroup<I,O> out(O output) { this.output = output; this.type = "keys-value"; return this; }

    public KeyGroup<I,O> keys() { this.type = "keys"; return this; }

    public Map<I, O> asMap() {
        List<Map.Entry<I,O>> entries = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            // create an entry for input + output
            entries.add(Map.entry(inputs[i], outputs[i]));
        }
        Map<I,O> map = new HashMap<>(Map.of());
        entries.forEach(entry -> map.put(entry.getKey(),entry.getValue()));
        return map;
    }

    public O tryGetValue(I input) {
        if (this.contains(input) && this.output != null) return output;
        return null;
    }
    private O getValueFromKey(I input) {
        if (this.outputs == null) return null;
        for (int i = 0; i < inputs.length; i++) {
            if (input.equals(inputs[i])) return outputs[i];
        }
        return null;
    }

    public boolean contains(I input) {
        for (I i : inputs) {
            if (input.equals(i)) return true;
        }
        return false;
    }

    public boolean hasValue() { return this.outputs != null || this.output != null; }

    public KeyGroup<O,O> getValues() {
        if (this.outputs != null) return new KeyGroup<O,O>(this.outputs).keys();
        if (this.output != null) return new KeyGroup<O,O>(this.output).keys();
        return null;
    }

    public O atIndex(int index) {
        return switch(this.type) {
            case "key-value" -> {
                assert this.outputs != null;
                yield this.outputs[index];
            }
            case "keys-value" -> this.output;
            default -> null;
        };
    }

    public void setAtIndex(int index, O value) {
        if (this.outputs != null) { this.outputs[index] = value; return; }
        if (this.output != null) this.output = value;
    }

    public I getKey(int index) { return inputs[index]; }

    public O getValue() { return this.output; }

    public O get(I input) {
        return switch(this.type) {
            case "key-value" -> getValueFromKey(input);
            case "keys-value" -> tryGetValue(input);
            default -> null;
        };
    }
    public O get(int i) { return get(getKey(i)); }

    public int find(I input) {
        for (int i = 0; i < inputs.length; i++) {
            if (input.equals(inputs[i])) {
                return i;
            }
        }
        return -1;
    }

    public int count() { return inputs.length; }
}
