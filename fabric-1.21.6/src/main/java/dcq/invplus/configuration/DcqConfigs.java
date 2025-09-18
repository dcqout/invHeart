package dcq.invplus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dcq.invplus.TextureLoaded;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;

import java.util.function.Predicate;

public class DcqConfigs extends DcqConfigFile {
    public DcqInvConfigs inventory;

    public DcqConfigs(String key, JsonObject value) {
        super(key, value);
        inventory = value.has("PlayerInventory") ? (DcqInvConfigs) TextureLoaded.InvList.get(value.get("PlayerInventory").getAsString()) : null;
        inventory = inventory == null ? (DcqInvConfigs) TextureLoaded.InvList.get("default") : inventory;
    }

    public void merge(DcqConfigs dcqConfigs) {

    }

    public void merge(JsonElement value) {

    }

    public void drawSlots(GuiGraphics guiGraphics, int atX, int atY, int tst , Predicate<simpleSlot> bool) {
        for (simpleSlot slot : info_slots) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, bool.test(slot) ? slot.slot.getTexture(tst) : slot.slot.Texture, atX + (slot.posX + slot.slot.textureOffX), atY + (slot.posY + slot.slot.textureOffY), 0.0F, 0.0F
                    ,slot.slot.textureSizeX,slot.slot.textureSizeY, slot.slot.textureSizeX,slot.slot.textureSizeY);
        }
    }

    public void drawSlots(GuiGraphics guiGraphics,int atX,int atY) {
        for (simpleSlot slot : info_slots) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, slot.slot.Texture, atX + (slot.posX + slot.slot.textureOffX), atY + (slot.posY + slot.slot.textureOffY), 0.0F, 0.0F
                    ,slot.slot.textureSizeX,slot.slot.textureSizeY, slot.slot.textureSizeX,slot.slot.textureSizeY);
        }
    }
}
