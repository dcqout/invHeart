package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GrindstoneBlock.class)
public abstract class Grindstone_Block {
    private static final Component _TITLE = Component.translatable("container.grindstone_title");

    @Shadow protected MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {return null;}

    @Redirect(method = "useWithoutItem(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
            ,at = @At(value = "INVOKE", target =
            "Lnet/minecraft/world/level/block/state/BlockState;getMenuProvider(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/MenuProvider;"))
    public MenuProvider getprovider(BlockState block, Level level, BlockPos blockpos) {
        MenuProvider dcqinv = DcqInv.open("VanillaGrindstone",level,blockpos,_TITLE);
        if (dcqinv == null) {
            return getMenuProvider(block,level,blockpos); // Handle original logic
        }
        return dcqinv;

    }
}
