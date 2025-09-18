package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BrewingStandBlockEntity.class)
public abstract class Brewing_StandBlock {
    @Shadow protected final ContainerData dataAccess;

    protected Brewing_StandBlock() {dataAccess = null;}

    @SuppressWarnings("UnreachableCode")
    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaBrewing",((BrewingStandBlockEntity)(Object)this),i,inventory,this.dataAccess);
        if (dcqinv == null) {
            return new BrewingStandMenu(i, inventory,((BrewingStandBlockEntity)(Object)this), this.dataAccess); // Handle original logic
        }
        return dcqinv;
    }
}
