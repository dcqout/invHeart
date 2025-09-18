package dcq.invplus_mixin;

import dcq.invplus.api.DcqInv;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.LockCode;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BeaconBlockEntity.class)
public abstract class Beacon_Block extends BlockEntity {

    @Shadow private LockCode lockKey;
    @Shadow private final ContainerData dataAccess;
    @Shadow public Component getDisplayName() {return null;}

    public Beacon_Block(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        dataAccess = null;
    }

    @Overwrite
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        if (!BaseContainerBlockEntity.canUnlock(player,lockKey,getDisplayName())) {
            return null; // some original logic from the beacon
        }
        AbstractContainerMenu dcqinv = DcqInv.openWithEntity("VanillaBeacon",i,inventory,this.dataAccess, ContainerLevelAccess.create(this.level, this.getBlockPos()));
        if (dcqinv == null) {
            return new BeaconMenu(i, inventory, this.dataAccess, ContainerLevelAccess.create(this.level, this.getBlockPos())); // Handle original logic
        }
        return dcqinv;
    }
}
