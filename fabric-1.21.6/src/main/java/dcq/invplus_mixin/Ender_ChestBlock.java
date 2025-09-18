package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.EnderChestBlock;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnderChestBlock.class)
public abstract class Ender_ChestBlock {
    @ModifyArg(method = "useWithoutItem(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
            ,at = @At(value = "INVOKE", target =
            "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;"))
    public MenuProvider getprovider(@Nullable MenuProvider menuProvider) {
        return new SimpleMenuProvider((i, inventory, playerx) -> {
            var dcqinv = DcqInv.openWithEntity("VanillaEnderChest",playerx.getEnderChestInventory(),i,inventory);
            if (dcqinv == null) {
                return ChestMenu.threeRows(i, inventory, playerx.getEnderChestInventory());
            }
            return (ChestMenu) dcqinv;
        }, menuProvider.getDisplayName());
    }
}
