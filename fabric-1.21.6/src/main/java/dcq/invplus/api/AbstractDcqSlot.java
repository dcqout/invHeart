package dcq.invplus.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import jdk.jshell.spi.ExecutionControl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDcqSlot extends Slot implements IDcqSlot {

    public ResourceLocation Texture;
    public int TextureIndex = 0;
    public List<ResourceLocation> TexturePool = new ArrayList<>();
    public final int sizeX;
    public final int sizeY;
    public final int marginX;
    public final int marginY;
    public final int textureSizeX;
    public final int textureSizeY;
    public final int textureOffX;
    public final int textureOffY;
    public final int offsetX;
    public final int offsetY;

    @Deprecated
    public AbstractDcqSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
        Texture = null;
        sizeX = 0;
        sizeY = 0;
        marginY = 0;
        marginX = 0;
        textureSizeX = 0;
        textureSizeY = 0;
        textureOffX = 0;
        textureOffY = 0;
        offsetX = 0;
        offsetY = 0;
    }

    public AbstractDcqSlot(Container container, int i,ResourceLocation location,int x, int y, int sX, int sY, int mX, int mY, int tX, int tY,
                           int oX, int oY,int offX, int offY) {
        super(container,i, x, y);
        Texture = location;
        sizeX = sX;
        sizeY = sY;
        marginX = mX;
        marginY = mY;
        textureSizeX = tX;
        textureSizeY = tY;
        textureOffX = oX;
        textureOffY = oY;
        offsetX = offX;
        offsetY = offY;
    }

    public AbstractDcqSlot(ResourceLocation location,int x, int y, int sX, int sY, int mX, int mY, int tX, int tY,
                           int oX, int oY,int offX, int offY) {
        super(null,0, x, y);
        Texture = location;
        sizeX = sX;
        sizeY = sY;
        marginX = mX;
        marginY = mY;
        textureSizeX = tX;
        textureSizeY = tY;
        textureOffX = oX;
        textureOffY = oY;
        offsetX = offX;
        offsetY = offY;
    }

    public ResourceLocation TextureNext() {
        TextureIndex = (TextureIndex+1 > TexturePool.size()-1) ? 0 : TextureIndex+1;
        return TexturePool.get(TextureIndex);
    }

    public ResourceLocation getTexture(int tst) {
        return TexturePool.get(tst);
    }

    public void setTexturePool(JsonArray array) {
        for (JsonElement element : array.asList()) {
            TexturePool.add(ResourceLocation.withDefaultNamespace("textures/gui_dcqformat/slots/"+element.getAsString().toLowerCase()+".png"));
        }
    }

    public Slot getMenuSlot(int slot, Container container, int fin_x, int fin_y, Object... args) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Did you forget to create a 'createMenuSlot' function inside your customSlot?");
    }

    public static AbstractDcqSlot createScreenSlot(ResourceLocation location, int X, int Y, int SizeX, int SizeY, int MarginX, int MarginY, int TextureSizeX, int TextureSizeY,
                               int TextureOffX, int TextureOffY, int OffSetX, int OffSetY) throws ExecutionControl.NotImplementedException {
        throw new RuntimeException("createScreenSlot is not implemented! Dear mod maker... Did you forget to create a 'createScreenSlot' function inside your customSlot?");
    }

    public Slot createMenuSlot(int fin_x, int fin_y, int slot, Container container, Object... args)  {
        try {
            return getMenuSlot(slot,container,fin_x,fin_y, args);
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException("getMenuSlot is not implemented! Dear mod maker... Did you forget to create a 'getMenuSlot' function inside your customSlot?");
        }
    }

    public static IDcqSlot makeNew(int sizeX, int sizeY, int MarginX, int MarginY, ResourceLocation location, int TextureSizeX, int TextureSizeY,
                            int TextureOffX, int TextureOffY, int OffSetX, int OffSetY)  {
        try {
            return createScreenSlot(location,0,0,sizeX,sizeY,MarginX,MarginY,TextureSizeX,TextureSizeY,TextureOffX,TextureOffY,OffSetX,OffSetY);
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException("createScreenSlot is not implemented! Dear mod maker... Did you forget to create a 'createScreenSlot' function inside your customSlot?");
        }
    }
}
