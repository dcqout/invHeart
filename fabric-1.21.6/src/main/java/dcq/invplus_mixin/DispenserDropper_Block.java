package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DispenserBlockEntity.class)
public abstract class DispenserDropper_Block {
    @SuppressWarnings("UnreachableCode")
    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaDispenser",((DispenserBlockEntity)(Object)this),i,inventory);
        if (dcqinv == null) {
           return new DispenserMenu(i, inventory,((DispenserBlockEntity)(Object)this)); // Handle original logic
        }
        return dcqinv;
    }
}
