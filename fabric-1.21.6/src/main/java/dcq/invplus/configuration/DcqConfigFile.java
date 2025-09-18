package dcq.invplus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dcq.invplus.api.AbstractDcqSlot;
import dcq.invplus.api.DcqInv;
import dcq.invplus.extra.DcqAC;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class DcqConfigFile {
    private final String key_name;
    public final List<simpleSlot> info_slots = new ArrayList<>();
    public final Map<Character,simpleSlot> created_slots = new HashMap<>();

    public String getKey() {
        return key_name;
    }

    public DcqConfigFile(String keyName, JsonObject value) {
        this.key_name = keyName;
        if (value.has("Slots")) {
            if (value.getAsJsonObject("Slots").has("Dictionary")) {
                simpleSlot aspace = new simpleSlot(); aspace.newLineOnly = true; created_slots.putIfAbsent(' ',aspace);
                for (Map.Entry<String, JsonElement> elementEntry : value.getAsJsonObject("Slots").getAsJsonObject("Dictionary").entrySet()) {
                    simpleSlot prepare = new simpleSlot();
                    var obj = elementEntry.getValue().getAsJsonObject();
                    if (obj.has("MarginOnly") && obj.get("MarginOnly").getAsBoolean()) {
                        prepare.mX = (obj.has("Margin")?obj.getAsJsonObject("Margin").get("X").getAsInt():0);
                        prepare.mY = (obj.has("Margin")?obj.getAsJsonObject("Margin").get("Y").getAsInt():0);
                        prepare.marginOnly = true;
                        created_slots.putIfAbsent(elementEntry.getKey().charAt(0),prepare);
                        continue;
                    }
                    if (!obj.has("Type") || !obj.has("Size")) { continue; }
                    String slotType = obj.get("Type").getAsString();
                    prepare.type = slotType;
                    try {
                         var slot1 = (AbstractDcqSlot) DcqInv.ResourceManager.ModdedSlots.get(slotType).getMethod("createScreenSlot", int.class, int.class, int.class, int.class, ResourceLocation.class, int.class, int.class, int.class, int.class, int.class, int.class)
                                .invoke(null,
                                        (obj.has("Size")?obj.getAsJsonObject("Size").get("X").getAsInt():0),
                                        (obj.has("Size")?obj.getAsJsonObject("Size").get("Y").getAsInt():0),
                                        (obj.has("Margin")?obj.getAsJsonObject("Margin").get("X").getAsInt():0),
                                        (obj.has("Margin")?obj.getAsJsonObject("Margin").get("Y").getAsInt():0),
                                        ResourceLocation.withDefaultNamespace("textures/gui_dcqformat/slots/"+(obj.has("Texture")?obj.get("Texture").getAsString().toLowerCase():(
                                                obj.has("TexturePool")?obj.get("TexturePool").getAsJsonArray().asList().getFirst().getAsString().toLowerCase():"slot"))+".png"),
                                        (obj.has("TextureSize")?obj.getAsJsonObject("TextureSize").get("X").getAsInt():(obj.has("Size")?obj.getAsJsonObject("Size").get("X").getAsInt():0)),
                                        (obj.has("TextureSize")?obj.getAsJsonObject("TextureSize").get("Y").getAsInt():(obj.has("Size")?obj.getAsJsonObject("Size").get("Y").getAsInt():0)),
                                        (obj.has("TextureOffset")?obj.getAsJsonObject("TextureOffset").get("X").getAsInt():0),
                                        (obj.has("TextureOffset")?obj.getAsJsonObject("TextureOffset").get("Y").getAsInt():0),
                                        (obj.has("Offset")?obj.getAsJsonObject("Offset").get("X").getAsInt():0),
                                        (obj.has("Offset")?obj.getAsJsonObject("Offset").get("Y").getAsInt():0)
                                );
                        if (obj.has("TexturePool")) {
                            slot1.setTexturePool(obj.getAsJsonArray("TexturePool"));
                        }
                        prepare.set(slot1);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("WOW, REPORT THIS TO THIS MOD ON GITHUB: " + e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException("createScreenSlot was not found! Dear mod maker... Did you forget to implement 'createScreenSlot' from the abstractclass inside your customSlot? " + e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("createScreenSlot does not exist! Did you forget to extend AbstractDcqSlot and implement 'createScreenSlot'? " + e);
                    }
                    created_slots.putIfAbsent(elementEntry.getKey().charAt(0),prepare);
                }
            }
            int cordsX = 0; int cordsY = 0; int lX = cordsX; int lY = cordsY; int nl = 0; int nlY = 0; int index = 0; int info_index = 0;
            for (Map.Entry<String, JsonElement> elementEntry : value.getAsJsonObject("Slots").entrySet()) {
                if (StringUtils.equals(elementEntry.getKey(),"Dictionary")) { continue; }
                if (StringUtils.contains(elementEntry.getKey(),"AddFromShape")) {
                    if (elementEntry.getValue().getAsJsonObject().has("DrawAt")) {
                        var cords = elementEntry.getValue().getAsJsonObject().getAsJsonObject("DrawAt");
                        cordsX = cords.get("X").getAsInt(); cordsY = cords.get("Y").getAsInt();
                        lX = cordsX; lY = cordsY; nl = 0; nlY = 0; index = 0;
                    }
                    for (char shape : elementEntry.getValue().getAsJsonObject().get("Shape").getAsString().toCharArray()) {
                        if (created_slots.containsKey(shape)) { index++;
                            var created_slot = created_slots.get(shape);
                            if (created_slot.marginOnly) {
                                lX += created_slot.mX; lY += created_slot.mY; nlY += created_slot.mY;
                            } else if (created_slot.newLineOnly) {
                                lX = cordsX; lY += nlY + nl;
                            } else {
                                simpleSlot slot = new simpleSlot();
                                var obj = elementEntry.getValue().getAsJsonObject();
                                String slottype = obj.has("Type")?obj.get("Type").getAsString():created_slot.type;
                                try {
                                    var slot1 = obj.has("Type") || obj.has("Size") || obj.has("Margin") || obj.has("Texture") || obj.has("TextureSize") || obj.has("TextureOffset") || obj.has("Offset") ?
                                    (AbstractDcqSlot) DcqInv.ResourceManager.ModdedSlots.get((obj.has("Type")?obj.get("Type").getAsString():created_slot.type)).getMethod("createScreenSlot", int.class, int.class, int.class, int.class, ResourceLocation.class, int.class, int.class, int.class, int.class, int.class, int.class)
                                            .invoke(null,
                                                    (obj.has("Size")?obj.getAsJsonObject("Size").get("X").getAsInt():created_slot.slot.sizeX),
                                                    (obj.has("Size")?obj.getAsJsonObject("Size").get("Y").getAsInt():created_slot.slot.sizeY),
                                                    (obj.has("Margin")?obj.getAsJsonObject("Margin").get("X").getAsInt():created_slot.slot.marginX),
                                                    (obj.has("Margin")?obj.getAsJsonObject("Margin").get("Y").getAsInt():created_slot.slot.marginY),
                                                    (obj.has("Texture")?ResourceLocation.withDefaultNamespace("textures/gui_dcqformat/slots/"+obj.get("Texture").getAsString().toLowerCase()+".png"):(
                                                            obj.has("TexturePool")?ResourceLocation.withDefaultNamespace("textures/gui_dcqformat/slots/"+obj.get("TexturePool").getAsJsonArray().asList().getFirst().getAsString().toLowerCase()+".png"):created_slot.slot.Texture)),
                                                    (obj.has("TextureSize")?obj.getAsJsonObject("TextureSize").get("X").getAsInt():created_slot.slot.textureSizeX),
                                                    (obj.has("TextureSize")?obj.getAsJsonObject("TextureSize").get("Y").getAsInt():created_slot.slot.textureSizeY),
                                                    (obj.has("TextureOffset")?obj.getAsJsonObject("TextureOffset").get("X").getAsInt():created_slot.slot.textureOffX),
                                                    (obj.has("TextureOffset")?obj.getAsJsonObject("TextureOffset").get("Y").getAsInt():created_slot.slot.textureOffY),
                                                    (obj.has("Offset")?obj.getAsJsonObject("Offset").get("X").getAsInt():created_slot.slot.offsetX),
                                                    (obj.has("Offset")?obj.getAsJsonObject("Offset").get("Y").getAsInt():created_slot.slot.offsetY)
                                            ):created_slot.slot;
                                    if (obj.has("TexturePool")) {
                                        slot1.setTexturePool(obj.getAsJsonArray("TexturePool"));
                                    }
                                    slot.slot = slot1;
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException("WOW, REPORT THIS TO THIS MOD ON GITHUB: " + e);
                                } catch (InvocationTargetException e) {
                                    throw new RuntimeException("createScreenSlot was not found! Dear mod maker... Did you forget to implement 'createScreenSlot' from the abstractclass inside your customSlot? " + e);
                                } catch (NoSuchMethodException e) {
                                    throw new RuntimeException("createScreenSlot does not exist! Did you forget to extend AbstractDcqSlot and implement 'createScreenSlot'? " + e);
                                }
                                nl = Math.max(nl, slot.slot.textureSizeY);
                                slot.type = slottype;
                                slot.posX = lX + slot.slot.offsetX;
                                slot.posY = lY + slot.slot.offsetY;
                                slot.index = index;
                                lX += slot.slot.textureSizeX;
                                slot.info_index = info_index; info_index++;
                                info_slots.add(slot);
                                // TODO: update it
                            }
                        }
                    }
                }
                if (StringUtils.equals(elementEntry.getKey(),"Add")) {
                    throw new NotImplementedException("Please use 'AddFromShape' instead of 'Add'!");
                }
            }
        }
    }

    public Slot createInfoSlot(AbstractContainerMenu menu, simpleSlot slot, int indx, Container container, Object... args) {
        return ((DcqAC)menu).addSlotAccess(slot.slot.createMenuSlot(slot.posX+slot.slot.marginX,slot.posY+slot.slot.marginY,indx,container,args));
    }

}
