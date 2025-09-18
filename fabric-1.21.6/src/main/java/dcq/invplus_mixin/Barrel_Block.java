package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BarrelBlockEntity.class)
public abstract class Barrel_Block {
    @SuppressWarnings("UnreachableCode")
    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaBarrel",((BarrelBlockEntity)(Object)this),i,inventory);
        if (dcqinv == null) {
            return ChestMenu.threeRows(i, inventory, ((BarrelBlockEntity)(Object)this)); // Handle original logic
        }
        return dcqinv;
    }
}
