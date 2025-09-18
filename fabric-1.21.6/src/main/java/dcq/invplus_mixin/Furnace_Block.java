package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FurnaceBlockEntity.class)
public abstract class Furnace_Block extends AbstractFurnaceBlockEntity {

    protected Furnace_Block(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntityType, blockPos, blockState, recipeType);
    }

    @SuppressWarnings("UnreachableCode")
    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaSmelting",((FurnaceBlockEntity)(Object)this),i,inventory,this.dataAccess);
        if (dcqinv == null) {
            return new FurnaceMenu(i, inventory,((FurnaceBlockEntity)(Object)this), this.dataAccess); // Handle original logic
        }
        return dcqinv;
    }
}
