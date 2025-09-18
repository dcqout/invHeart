package dcq.invplus.configuration;

import dcq.invplus.api.AbstractDcqSlot;

@SuppressWarnings("InnerClassMayBeStatic")
public class simpleSlot implements ISlotGetter {
    public AbstractDcqSlot slot;
    public String type;
    public boolean marginOnly = false;
    public boolean newLineOnly = false;
    public int info_index = -1;
    public int index = -1;
    public int mX = 0;
    public int mY = 0;
    public int posX = 0;
    public int posY = 0;

    public void set(AbstractDcqSlot slot) {
        this.slot = slot;
    }
}
