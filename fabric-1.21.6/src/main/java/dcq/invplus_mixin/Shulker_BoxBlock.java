package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class Shulker_BoxBlock {
    @SuppressWarnings("UnreachableCode")
    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaShulkerBox",((ShulkerBoxBlockEntity)(Object)this),i,inventory);
        if (dcqinv == null) {
            return new ShulkerBoxMenu(i, inventory, ((ShulkerBoxBlockEntity)(Object)this)); // Handle original logic
        }
        return dcqinv;
    }
}
