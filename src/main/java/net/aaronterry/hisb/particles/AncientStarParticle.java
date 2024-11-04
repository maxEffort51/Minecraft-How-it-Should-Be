package net.aaronterry.hisb.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;

public class AncientStarParticle extends Particle {
    private float scale = 0.1f;
    private final float maxScale;

    protected AncientStarParticle(ClientWorld world, double x, double y, double z, float maxScale) {
        super(world, x, y, z);
        this.maxScale = maxScale;
        this.setColor(1,1,1);
    }

    @Override
    public void tick() {
        this.scale *= 1.5f; // Change the scale
        if (this.scale > 15.0) {
            this.markDead(); // Remove particle after it expands enough
        }
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {

    }

    @Override
    public ParticleTextureSheet getType() {
        return null;
    }
}

//private float scale = 0.1f; // Initial scale of the particle
//    private final float maxScale;
//
//    protected ExpandingCircleParticle(ClientWorld world, double x, double y, double z, float maxScale) {
//        super(world, x, y, z);
//        this.maxScale = maxScale;
//        this.setColor(1.0F, 1.0F, 1.0F); // White color
//        this.setSprite(TextureAtlasSprite);
//    }
//
//    @Override
//    public void tick() {
//        this.scale += 0.1f; // Increase the scale
//        if (this.scale > maxScale) {
//            this.markDead(); // Remove particle after reaching max scale
//        }
//    }
//
//    @Override
//    public float getSize(float tickDelta) {
//        return this.scale; // Use the current scale
//    }
//
//    public static class Factory implements ParticleEffect.Factory<ExpandingCircleParticle> {
//        @Override
//        public ExpandingCircleParticle createParticle(ParticleEffect parameters, ClientWorld world, double x, double y, double z, double xd, double yd, double zd) {
//            return new ExpandingCircleParticle(world, x, y, z, 5.0f); // Set maximum scale to 5.0
//        }
//    }