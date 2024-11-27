package net.aaronterry.hisb.pack.exploration.mixin;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
import net.aaronterry.hisb.pack.exploration.item.custom.AncientStarItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void ancientStarDamageCheck(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof PlayerEntity player)) return;
        ItemStack ancient = new ItemStack(ModItems.ANCIENT_STAR);
        HisbMod.debug("Star Damage Check: damage " + amount + ", future health " + (player.getHealth() - amount) + ", " + (player.getInventory().contains(ancient) ? "doesn't have star." : "has star!"));
        if (player.getHealth() - amount <= 0.0F && player.getInventory().contains(ancient)) {
            HisbMod.debug("Trying to heal the player with an ancient star; damage inflicted was: ", amount);
            AncientStarItem ancientStar = (AncientStarItem) ancient.getItem();
            ancientStar.activate(player,amount, source.getAttacker());
            player.playSound(SoundEvents.ITEM_TOTEM_USE, 0.9f, 0.5f);
            player.getInventory().removeStack(player.getInventory().getSlotWithStack(ancient));
            cir.setReturnValue(false); // Stop
        }
    }
}
