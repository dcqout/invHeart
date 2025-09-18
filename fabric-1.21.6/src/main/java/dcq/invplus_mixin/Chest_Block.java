package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ChestBlockEntity.class)
public abstract class Chest_Block {

    @SuppressWarnings("UnreachableCode")
    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaChest",((ChestBlockEntity)(Object)this),i,inventory);
        if (dcqinv == null) {
            return ChestMenu.threeRows(i, inventory, ((ChestBlockEntity)(Object)this)); // Handle original logic
        }
        return dcqinv;
    }
}
