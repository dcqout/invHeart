package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.CrafterMenu;
import net.minecraft.world.level.block.entity.CrafterBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CrafterBlockEntity.class)
public abstract class Crafter_Block {

    @Shadow protected final ContainerData containerData;

    protected Crafter_Block() {
        containerData = null;
    }

    @SuppressWarnings("UnreachableCode")
    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaCrafter",((CrafterBlockEntity)(Object)this),i,inventory,this.containerData);
        if (dcqinv == null) {
            return new CrafterMenu(i, inventory,((CrafterBlockEntity)(Object)this),this.containerData); // Handle original logic
        }
        return dcqinv;
    }
}
