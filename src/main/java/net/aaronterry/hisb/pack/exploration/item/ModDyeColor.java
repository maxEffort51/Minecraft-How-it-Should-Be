package net.aaronterry.hisb.pack.exploration.item;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.MapColor;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public enum ModDyeColor implements StringIdentifiable {
    CELESTE(1, "celeste", 1481884, MapColor.CYAN, 2651799, 65535);

    private static final IntFunction<ModDyeColor> BY_ID = ValueLists.createIdToValueFunction(ModDyeColor::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
    private static final Int2ObjectOpenHashMap<ModDyeColor> BY_FIREWORK_COLOR = new Int2ObjectOpenHashMap((Map) Arrays.stream(values()).collect(Collectors.toMap((color) -> {
        return color.fireworkColor;
    }, (color) -> {
        return color;
    })));
    public static final StringIdentifiable.EnumCodec<ModDyeColor> CODEC = StringIdentifiable.createCodec(ModDyeColor::values);
    public static final PacketCodec<ByteBuf, ModDyeColor> PACKET_CODEC = PacketCodecs.indexed(BY_ID, ModDyeColor::getId);
    private final int id;
    private final String name;
    private final MapColor mapColor;
    private final int entityColor;
    private final int fireworkColor;
    private final int signColor;

    private ModDyeColor(final int id, final String name, final int entityColor, final MapColor mapColor, final int fireworkColor, final int signColor) {
        this.id = id;
        this.name = name;
        this.mapColor = mapColor;
        this.signColor = signColor;
        this.entityColor = ColorHelper.Argb.fullAlpha(entityColor);
        this.fireworkColor = fireworkColor;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getEntityColor() {
        return this.entityColor;
    }

    public MapColor getMapColor() {
        return this.mapColor;
    }

    public int getFireworkColor() {
        return this.fireworkColor;
    }

    public int getSignColor() {
        return this.signColor;
    }

    public static ModDyeColor byId(int id) {
        return (ModDyeColor)BY_ID.apply(id);
    }

    @Nullable
    @Contract("_,!null->!null;_,null->_")
    public static ModDyeColor byName(String name, @Nullable ModDyeColor defaultColor) {
        ModDyeColor dyeColor = (ModDyeColor)CODEC.byId(name);
        return dyeColor != null ? dyeColor : defaultColor;
    }

    @Nullable
    public static ModDyeColor byFireworkColor(int color) {
        return (ModDyeColor)BY_FIREWORK_COLOR.get(color);
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}