package net.aaronterry.helper.customtable;

import com.mojang.serialization.MapCodec;
import net.aaronterry.helper.KeyGroup;
import net.aaronterry.helper.LargeMap;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.entity.PurifierTableBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomTableBlock extends BlockWithEntity {
    private CustomTableBlockEntity blockEntity;
    public String id;
    public String name;
    public String location;
    public Builder builder;
    public static BlockEntityType<CustomTableBlockEntity> ENTITY;

    protected CustomTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
         ENTITY = Registry.register(
                 Registries.BLOCK_ENTITY_TYPE,
                 Identifier.of(HisbMod.MOD_ID, this.id),
                 BlockEntityType.Builder.create(CustomTableBlockEntity::new, this).build());
        return new CustomTableBlockEntity(builder, this, pos, state);
    }

    private CustomTableBlock setTexture(String location) {
        this.location = location;
        return this;
    }

    private CustomTableBlock setName(String id, String name) {
        this.id = id;
        this.name = name;
        return this;
    }

    private CustomTableBlock makeEntity(Builder builder) {
        this.builder = builder;
        return this;
    }

    public static class Builder {
        private final KeyGroup[] inputs;
        private KeyGroup output;
        private final String id;
        private String name;
        private final KeyGroup progress;
        private int maxProgress;
        private float blockStrength;
        private TickSettings settings;
        private String textureLocation;
        private KeyGroup[] positions;
        private int numPositions;

        public Builder(int inputs, int outputs, String identifier) {
            this.settings = new TickSettings();
            this.inputs = new KeyGroup[inputs];
            this.output = new KeyGroup(new Object[] {});
            this.id = identifier;
            this.positions = new KeyGroup[inputs + outputs];
            this.numPositions = 0;
            this.progress = new KeyGroup(new Object[] {});
            this.textureLocation = "textures/block/" + this.id + ".png";
        }

        // GET FUNCTIONS
        public Object getVariable(String identifier) {
            return switch(identifier) {
                case "inputs" -> inputs;
                case "output" -> output;
                case "id" -> id;
                case "name" -> name;
                case "pos" -> positions;
                case "progress" -> progress;
                case "maxProgress" -> maxProgress;
                case "blockStrength" -> blockStrength;
                case "settings" -> settings;
                case "textureLocation" -> textureLocation;
                default -> null;
            };
        }

        // SET FUNCTIONS
        public Builder input(int index, KeyGroup items) {
            if (index >= 0 && index < inputs.length) inputs[index] = items;
            return this;
        }

        public Builder position(int x, int y) {
            if (numPositions >= positions.length) return this;
            positions[numPositions] = new KeyGroup(new Object[] {x}, y);
            numPositions++;
            return this;
        }

        public Builder textureLocation(String loc) {
            this.textureLocation = loc;
            return this;
        }

        public Builder output(int index) {
            if (inputs[index].hasValue()) this.output = inputs[index].getValues();
            return this;
        }

//        public Builder dropOnBreak() { this.settings.dropInventory = true; return this; }
//        public Builder fullBlock() { this.settings.fullBlock = true; return this; }

        public Builder displayName(String name) {
            this.name = name;
            return this;
        }

        // Deplete one slot at the index if the progress is correct
        public Builder progressPoint(int index, int progress) {
            this.progress.setAtIndex(index, progress);
            return this;
        }

        public Builder craftWhen(int maxProgress) {
            this.maxProgress = maxProgress;
            return this;
        }

        //public Builder staticCrafting() {}
        //public Builder customTick() {}

        public Builder activeCrafting() {
            this.settings.crafting = true;
            return this;
        }

        public Builder strength(float strength) {
            blockStrength = strength;
            return this;
        }

        public CustomTableBlock build() {
            return new CustomTableBlock(AbstractBlock.Settings.create().strength(this.blockStrength)
                    .requiresTool()).setTexture(this.textureLocation).setName(this.id,this.name).makeEntity(this);
        }

        public static class TickSettings {
            public boolean crafting;

            public TickSettings() {
                this.crafting = true;
            }
        }
    }

    // public static final Block PURIFIER_TABLE = registerBlock("purifier_table",
    //            new CustomTableBlock.Builder(3,1,"purifier_table")
    //                  .input(0,new KeyGroup() {}).input(1, new LargeMap.Keys() {}).input(2, new LargeMap.Keys() {})
    //                  .textureLocation("textures/block/purifier_table.png") // custom location
    //                  .output(0) // the value of recorded input 0 if it's a LargeMap
    //                  .dropOnBreak().fullBlock()
    //                  .displayName("Purifier Table")
    //                      /*.progressPoint(0,64) // use up input slot 0 when progress is 64
    //                      .progressPoint(1,96) // use up input slot 1 when progress is 96
    //                      .craftAt(96) // craft when progress is 96 */
    //                  .setProgressPoints([[0,64],[1,96]],96)
    //                  .tickRequirementsBuilder().crafter().buildTick() // default tick action of a crafter table
    //                  .strength(3f).build() // RETURNS CustomTableBlock which extends Block
    // );

}
