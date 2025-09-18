package dcq.invplus;

import dcq.invplus.configuration.DcqInvConfiguration;
import dcq.invplus.configuration.DcqInvResource;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TextureLoaded {

    public static Minecraft minecraft;

    public static final Map<String, Class<?>> ModdedScreens = new HashMap<>();
    public static final Map<String, Class<?>> ModdedSlots = new HashMap<>();
    public static final DcqInvResource RegularJSONS = new DcqInvResource("dcqinv");
    public static final DcqInvResource InventoryJSONS = new DcqInvResource("dcqinv.player.inventory");
    public static final DcqInvResource ConditionsJSONS = new DcqInvResource("dcqinv.conditions");
    public static final DcqInvConfiguration ConditionList = new DcqInvConfiguration();
    public static final DcqInvConfiguration ConfigList = new DcqInvConfiguration();
    public static final DcqInvConfiguration InvList = new DcqInvConfiguration();

    public static void LoadEnded() {
        Inventory.LOGGER.info("ReloadedTexture");
        ConditionList.clear();
        ConfigList.clear();
        InvList.clear();

        // For servers that is set when the first player joins the world but can be set with:
        // /invplus reset | /invplus set {new_inv_size} (doing so will delete all player data and is not recommended if the world is old and not vannila)

        // TO DO: When applying a resource pack in a vannila {inv_size} server, ask if the player meant to update the inventory size if so explain and
        // recommend /invplus reset | /invplus set {new_inv_size} | /invplus shouldWarnSizes false

        //Extra: for changing stuff like chests|modded chests sizes a separated mod is required as this needs to be the same for everyone.
        //Extra2: Warn the player when a smaller or bigger inventory pack is applied since it will only show slots within those limits.
        // also recommend /invplus reset again if enabled. recommend /invplus shouldWarnPacks false

        readJSON(minecraft.getResourceManager(), ConditionsJSONS.getAll(), ConditionList::createConditional);
        readJSON(minecraft.getResourceManager(), InventoryJSONS.getAll(), InvList::createInv);
        readJSON(minecraft.getResourceManager(), RegularJSONS.getAll(), ConfigList::createRegular);
        Inventory.LOGGER.info("ReloadedDcqInv");
    }

    public static void readJSON(ResourceManager resourceManager, Collection<ResourceLocation> locations, DcqInvConfiguration.DcqConfigsReader configsReader) {
        for (ResourceLocation resourceLocation : locations) {
            if (resourceManager.getResource(resourceLocation).isEmpty()) {
                continue;
            }
            try {
                Reader reader = resourceManager.openAsReader(resourceLocation);
                try {
                    configsReader.read(reader);
                } catch (Throwable var7) {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }
                    throw var7;
                }
                if (reader != null) {
                    Inventory.LOGGER.info("Loaded: "+resourceLocation.getPath());
                    reader.close();
                }
            } catch (Exception var8) {
                Inventory.LOGGER.error("Couldn't load DcqInv configuration from file {}", resourceLocation, var8);
            }
        }
    }

    public static void MinecraftStarted(Minecraft client) {
        Inventory.LOGGER.info("Minecraft has Loaded, loading DcqInv jsons.");
        //client.reloadResourcePacks();
        minecraft = client;
    }
}
