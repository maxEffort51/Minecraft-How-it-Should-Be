package net.maxeffort.helper.util;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class HelperBlockTags {
    public static final List<TagSorter> tags = new ArrayList<>();

    public static TagKey<Block> tag(TagSorter sorter, String name) {
        TagKey<Block> tagKey = TagKey.of(RegistryKeys.BLOCK, Identifier.of(sorter.main, name));
        sorter.setKey(tagKey); tags.add(sorter); return tagKey;
    }
    protected static TagKey<Block> tag(String main, String name) {
        return tag(sorter(main, TagSorter.Type.MISC), name);
    }
    protected static TagKey<Block> incorrect(TagSorter sorter, String ore) { return tag(sorter.type(TagSorter.Type.INCORRECT), "incorrect_for_"+ore+"_tool"); }
    protected static TagKey<Block> incorrect(String main, String ore) { return incorrect(sorter(main), ore); }
    protected static TagKey<Block> needs(TagSorter sorter, String ore) { return tag(sorter.type(TagSorter.Type.NEEDS), "needs_"+ore+"_tool"); }
    protected static TagKey<Block> needs(String main, String ore) { return needs(sorter(main, TagSorter.Type.NEEDS), ore); }
    protected static TagKey<Block> all(TagSorter sorter, String name) { return tag(sorter.type(TagSorter.Type.ALL), name); }
    protected static TagKey<Block> all(String main, String name) { return all(sorter(main), name); }

    public static TagSorter sorter(String main) { return new TagSorter(main); }
    public static TagSorter sorter(String main, TagSorter.Type sortType) { return new TagSorter(main, sortType); }

    public static List<TagKey<Block>> search(TagSorter.Type type) {
        List<TagKey<Block>> keys = new ArrayList<>();
        tags.forEach(tag -> {
            if (tag.sortType.same(type)) keys.add(tag.getKey());
        });
        return keys;
    }
    public static List<TagKey<Block>> getIncorrect() { return search(TagSorter.Type.INCORRECT); }
    public static List<TagKey<Block>> getNeeds() { return search(TagSorter.Type.NEEDS); }
    public static List<TagKey<Block>> getAll() { return search(TagSorter.Type.ALL); }
    public static List<TagKey<Block>> all() { return tags.stream().map(TagSorter::getKey).toList(); }

    public static class TagSorter {
        private final String main;
        private Type sortType;
        private TagKey<Block> tagKey;

        private TagSorter(String main) { this.main = main; }
        private TagSorter(String main, Type type) { sortType = type; this.main = main; }

        public TagSorter type(Type type) { return new TagSorter(main, type); }

        public String getMain() { return main; }
        public Type getType() { return sortType; }
        protected void setKey(TagKey<Block> key) { tagKey = key; }
        public TagKey<Block> getKey() { return tagKey; }

        public static class Type {
            public static final Type NEEDS = new Type("needs");
            public static final Type INCORRECT = new Type("incorrect");
            public static final Type ALL = new Type("all");
            public static final Type MISC = new Type("misc");

            private final String type;

            private Type(String type) { this.type = type; }

            public boolean same(Type other) { return type.equals(other.type); }

            public Type copy() { return new Type(type); }
        }
    }
}
