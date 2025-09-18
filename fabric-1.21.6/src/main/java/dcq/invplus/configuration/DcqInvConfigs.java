package dcq.invplus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class DcqInvConfigs extends DcqConfigFile {

    public final ResourceLocation BACKGROUND;
    public final int invSlots;
    public final int htSlots;
    public final int spSlots;
    public final int maxSlots;
    public final int maxInvSlots;
    public final int invSizeY;
    public final int invSizeX;

    public DcqInvConfigs(String key, JsonObject value) {
        super(key, value);
        BACKGROUND = value.has("Texture")
                ? ResourceLocation.withDefaultNamespace("textures/gui_dcqformat/container/"+value.get("Texture").getAsString()+".png")
                : ResourceLocation.withDefaultNamespace("textures/gui_dcqformat/container/empty_inv.png");
        invSizeY = value.has("TextureSize") ? value.getAsJsonObject("TextureSize").get("Y").getAsInt() : 176;
        invSizeX = value.has("TextureSize") ? value.getAsJsonObject("TextureSize").get("X").getAsInt() : 83;
        htSlots = value.has("HotbarSlots") ? value.get("HotbarSlots").getAsInt() : 9;
        invSlots = value.has("InventorySlots") ? value.get("InventorySlots").getAsInt(): 27;
        spSlots = value.has("SpecialSlots") ? value.get("SpecialSlots").getAsInt() : 9;
        maxInvSlots = htSlots + invSlots;
        maxSlots = spSlots + maxInvSlots;
        if (value.has("GlobalSize") && value.get("GlobalSize").getAsBoolean()) {
            setNewSize(); // set global inv size if true, also asks server about it (if check enabled, default)
        }
        if (value.has("NoCheck") && !value.get("NoCheck").getAsBoolean()) {
            checkServerSize(); // check if there is no underflow or overflow with invSize indexes
        }
    }

    public void drawInv(GuiGraphics guiGraphics, int atX, int atY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, atX, atY+invSizeY, 0.0F, 0.0F, invSizeX, invSizeY, 256, 256);
        for (simpleSlot slot : info_slots) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, slot.slot.Texture, atX + (slot.posX + slot.slot.textureOffX), atY + (slot.posY + slot.slot.textureOffY), 0.0F, 0.0F
                    ,slot.slot.textureSizeX,slot.slot.textureSizeY, slot.slot.textureSizeX,slot.slot.textureSizeY);
        }
    }

    public void makeInv(AbstractContainerMenu menu, Inventory container) {
        int indx_ = htSlots; int max_indx = maxInvSlots; boolean hotbar = false;
        for (simpleSlot slot : info_slots) {
            createInfoSlot(menu,slot,indx_,container);
            if (indx_ >= max_indx-1) { if (hotbar) {break;} max_indx = htSlots; indx_ = -1; hotbar = true;} indx_++;
        }
    }

    public void checkServerSize() {
    }

    public void setNewSize() {
    }

    public void merge(JsonElement value) {
    }
}
