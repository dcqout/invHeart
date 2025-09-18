package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilBlock.class)
public abstract class Anvil_Block {
    private static final Component _TITLE = Component.translatable("container.repair");

    @Shadow
    protected MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {return null;}

    @Redirect(method = "useWithoutItem(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
            ,at = @At(value = "INVOKE", target =
            "Lnet/minecraft/world/level/block/state/BlockState;getMenuProvider(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/MenuProvider;"))
    public MenuProvider getprovider(BlockState block, Level level, BlockPos blockpos) {
        MenuProvider dcqinv = DcqInv.open("VanillaRepairing",level,blockpos,_TITLE);
        if (dcqinv == null) {
            return getMenuProvider(block,level,blockpos); // Handle original logic
        }
        return dcqinv;

    }
}
