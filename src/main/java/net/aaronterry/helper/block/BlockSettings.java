package net.aaronterry.helper.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class BlockSettings {
    private final AbstractBlock.Settings settings;

    public static BlockSettings copy(AbstractBlock block) { return new BlockSettings(AbstractBlock.Settings.copy(block)); }

    public static Block makeBlock(float strength) { return new Block(makeFinal(strength)); }
    public static Block makeBlock(float strength, boolean requiresTool) { return new Block(makeFinal(strength, requiresTool)); }
    public static Block makeBlock(float strength, float resistance) { return new Block(makeFinal(strength, resistance)); }

    public static AbstractBlock.Settings makeFinal(float strength) { return AbstractBlock.Settings.create().strength(strength); }
    public static AbstractBlock.Settings makeFinal(float strength, boolean requiresTool) {
        return requiresTool ? AbstractBlock.Settings.create().strength(strength).requiresTool() : AbstractBlock.Settings.create().strength(strength); }
    public static AbstractBlock.Settings makeFinal(float strength, float resistance) { return AbstractBlock.Settings.create().strength(strength, resistance); }

    public static BlockSettings copy(Block block) { return new BlockSettings(block.getSettings()); }

    public BlockSettings() { settings = AbstractBlock.Settings.create(); }
    public BlockSettings(float strength) { this(); settings.strength(strength); }
    public BlockSettings(float strength, float resistance) { this(); strength(strength, resistance); }
    public BlockSettings(AbstractBlock.Settings settings) { this.settings = settings; }

    public BlockSettings strength(float strength) { settings.strength(strength); return this; }
    public BlockSettings strength(float strength, float resistance) { settings.strength(strength, resistance); return this; }

    public BlockSettings requires() { settings.requiresTool(); return this; }
    public BlockSettings requires(boolean tool) { if (tool) settings.requiresTool(); return this; }
    public BlockSettings requires(FeatureFlag... flags) { settings.requires(flags); return this; }

    public BlockSettings sound(NoteBlockInstrument instrument, BlockSoundGroup group) { settings.instrument(instrument).sounds(group); return this; }
    public BlockSettings sound(BlockSoundGroup group) { settings.sounds(group); return this; }

    public BlockSettings model(String type) {
        switch(type) {
            case "non_opaque": settings.nonOpaque(); break;
            case "air": settings.air(); break;
            case "liquid": settings.liquid(); break;
            case "no_particles": settings.noBlockBreakParticles(); break;
            case "solid": settings.solid(); break;
            default: break;
        } return this;
    }

    public BlockSettings replace() { settings.replaceable(); return this; }
    public BlockSettings noDrop() { settings.dropsNothing(); return this; }
    public BlockSettings dropLike(Block block) { settings.dropsLike(block); return this; }

    public BlockSettings burn() { settings.burnable(); return this; }
    public BlockSettings spawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) { settings.allowsSpawning(predicate); return this; }
    public BlockSettings instabreak() { settings.breakInstantly(); return this; }

    public BlockSettings blockVision(AbstractBlock.ContextPredicate predicate) { settings.blockVision(predicate); return this; }
    public BlockSettings lighting(AbstractBlock.ContextPredicate predicate) { settings.emissiveLighting(predicate); return this; }
    public BlockSettings luminance(ToIntFunction<BlockState> luminance) { settings.luminance(luminance); return this; }
    public BlockSettings jump(float multiplier) { settings.jumpVelocityMultiplier(multiplier); return this; }

    public BlockSettings colorMap(DyeColor color) { settings.mapColor(color); return this; }
    public BlockSettings colorMap(MapColor color) { settings.mapColor(color); return this; }
    public BlockSettings colorMap(Function<BlockState, MapColor> color) { settings.mapColor(color); return this; }

    public BlockSettings noCollide() { settings.noCollision(); return this; }
    public BlockSettings randomTick() { settings.ticksRandomly(); return this; }
    public BlockSettings piston(PistonBehavior behavior) { settings.pistonBehavior(behavior); return this; }
    public BlockSettings suffocate(AbstractBlock.ContextPredicate predicate) { settings.suffocates(predicate); return this; }
    public BlockSettings ice(float slip) { settings.slipperiness(slip); return this; }

    public AbstractBlock.Settings get() { return settings; }
    public Block getBlock() { return new Block(settings); }
}
