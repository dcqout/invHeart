package dcq.invplus.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class DcqSlot extends AbstractDcqSlot {

    public DcqSlot(ResourceLocation location, int x, int y, int sX, int sY, int mX, int mY, int tX, int tY, int oX, int oY, int offX, int offY) {
        super(location, x, y, sX, sY, mX, mY, tX, tY, oX, oY, offX, offY);
    }

    public static AbstractDcqSlot createScreenSlot(int sizeX, int sizeY, int MarginX, int MarginY, ResourceLocation location, int TextureSizeX, int TextureSizeY,
                                           int TextureOffX, int TextureOffY, int OffSetX, int OffSetY) {
        return new DcqSlot(location,0,0,sizeX,sizeY,MarginX,MarginY,TextureSizeX,TextureSizeY,TextureOffX,TextureOffY,OffSetX,OffSetY);
    }

    @Override
    public Slot getMenuSlot(int slot, Container container, int fin_x, int fin_y, Object... args) {
        return new Slot(container,slot,fin_x,fin_y);
        /*return new DcqSlot(container,slot,fin_x,fin_y, this.sizeX,this.sizeY,this.marginX,this.marginY,
                this.textureSizeX,this.textureSizeY,this.textureOffX,this.textureOffY,this.offsetX,this.offsetY,this.Texture, args);*/
    }
}
