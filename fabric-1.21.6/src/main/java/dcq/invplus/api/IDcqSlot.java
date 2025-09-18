package dcq.invplus.api;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;

public interface IDcqSlot {
    default boolean skipDraw() {return false;}
    default boolean skipAdd() {return false;}

    default void create(IDcqMenu menu) {menu.create(this);}

    /*ResourceLocation getTexture();
    int[] getNumbers();

    default void draw(GuiGraphics guiGraphics) {
        int[] n = this.getNumbers();
        guiGraphics.blit(this.getTexture(),n[0],n[1],n[2],n[3],n[4],n[5],n[6],n[7]);
    }*/
}
