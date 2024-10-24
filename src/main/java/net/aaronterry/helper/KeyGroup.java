package net.aaronterry.helper;

public class KeyGroup {
    private final Object[] inputs;
    private final Object[] outputs;
    private Object output;
    private final String type;

    public KeyGroup(Object[] inputs, Object[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.output = null;
        this.type = "key-value";
    }

    public KeyGroup(Object[] inputs, Object output) {
        this.inputs = inputs;
        this.output = output;
        this.outputs = null;
        this.type = "keys-value";
    }

    public KeyGroup(Object[] keys) {
        this.inputs = keys;
        this.output = null;
        this.outputs = null;
        this.type = "keys";
    }

    public Object tryGetValue(Object input) {
        if (this.contains(input) && this.output != null) return output;
        return null;
    }
    private Object getValueFromKey(Object input) {
        if (this.outputs == null) return null;
        for (int i = 0; i < inputs.length; i++) {
            if (input.equals(inputs[i])) return outputs[i];
        }
        return null;
    }

    public boolean contains(Object input) {
        for (Object i : inputs) {
            if (input.equals(i)) return true;
        }
        return false;
    }

    public boolean hasValue() { return this.outputs != null || this.output != null; }

    public KeyGroup getValues() {
        if (this.outputs != null) return new KeyGroup(this.outputs);
        if (this.output != null) return new KeyGroup(new Object[] {this.output});
        return null;
    }

    public Object atIndex(int index) {
        return switch(this.type) {
            case "key-value" -> {
                assert this.outputs != null;
                yield this.outputs[index];
            }
            case "keys-value" -> this.output;
            case "keys" -> this.inputs[index];
            default -> null;
        };
    }

    public void setAtIndex(int index, Object value) {
        if (this.outputs != null) { this.outputs[index] = value; return; }
        if (this.output != null) this.output = value;
    }

    public Object getKey(int index) { return inputs[index]; }

    public Object getValue() { return this.output; }

    public Object get(Object input) {
        return switch(this.type) {
            case "key-value" -> getValueFromKey(input);
            case "keys-value" -> tryGetValue(input);
            default -> null;
        };
    }

    public int find(Object input) {
        for (int i = 0; i < inputs.length; i++) {
            if (input.equals(inputs[i])) {
                return i;
            }
        }
        return -1;
    }

    public int count() { return inputs.length; }
}
