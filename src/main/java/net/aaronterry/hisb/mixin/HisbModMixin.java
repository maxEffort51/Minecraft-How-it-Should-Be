package net.aaronterry.hisb.mixin;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.item.ModItems;
import net.aaronterry.hisb.item.custom.AncientStarItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class HisbModMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void ancientStarDamageCheck(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof PlayerEntity player)) return;
        ItemStack ancient = new ItemStack(ModItems.ANCIENT_STAR);
        HisbMod.debug("Star Damage Check: damage ", amount);
        HisbMod.debug("                -> future health ", entity.getHealth() - amount);
        HisbMod.debug("                -> has star ", player.getInventory().contains(ancient));
        if (player.getHealth() - amount <= 0.0F && player.getInventory().contains(ancient)) {
            HisbMod.debug("Trying to heal the player with an ancient star; damage inflicted was: ", amount);
            AncientStarItem ancientStar = (AncientStarItem) ancient.getItem();
            ancientStar.activate(player,amount, source.getAttacker());
            player.getInventory().removeOne(ancient);
            cir.setReturnValue(false); // Stop
        }
    }
}
