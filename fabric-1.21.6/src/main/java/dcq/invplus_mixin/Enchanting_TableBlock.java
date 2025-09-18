package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantingTableBlock.class)
public abstract class Enchanting_TableBlock {
    @Shadow protected MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {return null;}

    @Redirect(method = "useWithoutItem(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
            ,at = @At(value = "INVOKE", target =
            "Lnet/minecraft/world/level/block/state/BlockState;getMenuProvider(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/MenuProvider;"))
    public MenuProvider getprovider(BlockState block, Level level, BlockPos blockpos) {
        BlockEntity blockEntity = level.getBlockEntity(blockpos);
        if (!(blockEntity instanceof EnchantingTableBlockEntity)) {
            return null; // some original getMenuProvider logic for the enchanting table
        }
        MenuProvider dcqinv = DcqInv.open("VanillaEnchanting",level,blockpos,((Nameable)blockEntity).getDisplayName());
        if (dcqinv == null) {
            return getMenuProvider(block,level,blockpos); // Handle original logic
        }
        return dcqinv;

    }
}
