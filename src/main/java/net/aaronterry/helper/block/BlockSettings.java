package net.aaronterry.helper.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.sound.BlockSoundGroup;

public class BlockSettings {
    private final AbstractBlock.Settings settings;

    public static AbstractBlock.Settings makeFinal(float strength) { return AbstractBlock.Settings.create().strength(strength); }
    public static AbstractBlock.Settings makeFinal(float strength, boolean requiresTool) {
        return requiresTool ? AbstractBlock.Settings.create().strength(strength).requiresTool() : AbstractBlock.Settings.create().strength(strength); }
    public static AbstractBlock.Settings makeFinal(float strength, float resistance) { return AbstractBlock.Settings.create().strength(strength, resistance); }

    public BlockSettings() { settings = AbstractBlock.Settings.create(); }
    public BlockSettings(float strength) { this(); settings.strength(strength); }
    public BlockSettings(float strength, float resistance) { this(); strength(strength, resistance); }

    public BlockSettings strength(float strength) { settings.strength(strength); return this; }
    public BlockSettings strength(float strength, float resistance) { settings.strength(strength, resistance); return this; }

    public BlockSettings requires() { settings.requiresTool(); return this; }
    public BlockSettings requires(boolean tool) { if (tool) settings.requiresTool(); return this; }
    public BlockSettings requires(FeatureFlag... flags) { settings.requires(flags); return this; }

    public BlockSettings sound(NoteBlockInstrument instrument, BlockSoundGroup group) { settings.instrument(instrument).sounds(group); return this; }

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

    public BlockSettings noDrop() { settings.dropsNothing(); return this; }
    public BlockSettings dropLike(Block block) { settings.dropsLike(block); return this; }

    public BlockSettings burnable() { settings.burnable(); return this; }
    public BlockSettings spawning(AbstractBlock.TypedContextPredicate predicate) { settings.allowsSpawning(predicate); return this; }
}
