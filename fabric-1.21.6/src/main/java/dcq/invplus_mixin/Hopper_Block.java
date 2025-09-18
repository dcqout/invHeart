package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(HopperBlockEntity.class)
public abstract class Hopper_Block {
    @SuppressWarnings("UnreachableCode")
    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaHopper",((HopperBlockEntity)(Object)this),i,inventory);
        if (dcqinv == null) {
            return new HopperMenu(i, inventory, ((HopperBlockEntity)(Object)this)); // Handle original logic
        }
        return dcqinv;
    }
}
