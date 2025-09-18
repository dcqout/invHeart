package dcq.invplus.api;

public interface IDcqMenu {
    default IDcqSlot create(IDcqSlot slot) {
        return ((AbstractModContainer.ContainerMenu)this).addSlot(slot);
    }
}
