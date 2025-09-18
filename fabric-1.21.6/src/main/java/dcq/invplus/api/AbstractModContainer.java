package dcq.invplus.api;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractModContainer {

    public abstract class ContainerMenu extends AbstractContainerMenu {
        protected ContainerMenu(@Nullable MenuType<?> menuType, int i) {
            super(menuType, i);
        }
        public IDcqSlot addSlot(IDcqSlot dcqSlot) {
            return (IDcqSlot) this.addSlot((Slot) dcqSlot);
        }
    }

    public abstract class ContainerScreen<M extends ContainerMenu> extends AbstractContainerScreen {
        public ContainerScreen(AbstractContainerMenu abstractContainerMenu, Inventory inventory, Component component) {
            super(abstractContainerMenu, inventory, component);
        }
    }
}
