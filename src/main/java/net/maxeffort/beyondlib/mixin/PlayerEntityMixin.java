package net.maxeffort.helper.mixin;

import net.maxeffort.helper.item.HelperItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract void startFallFlying();

    @Inject(method = "checkFallFlying", at = @At("HEAD"), cancellable = true)
    private void checkFallFlying(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.isOnGround() && !player.isFallFlying() && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION)) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.CHEST);
            if (isOfItems(itemStack, HelperItems.elytras.toArray(new Item[0])) && ElytraItem.isUsable(itemStack)) {
                this.startFallFlying();
                cir.setReturnValue(true);
            }
        }
        cir.setReturnValue(false);
    }

    @Unique
    private boolean isOfItems(ItemStack itemStack, Item[] items) {
        boolean prove = false;
        for (Item item : items) prove = itemStack.isOf(item);
        return prove;
    }
}
