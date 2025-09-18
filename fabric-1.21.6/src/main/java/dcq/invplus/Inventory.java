package dcq.invplus;

import dcq.invplus.api.DcqInv;
import dcq.invplus.api.DcqSlot;
import dcq.invplus.DcqMenu.DcqMenuScreens;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Inventory implements ModInitializer {
	public static final String MOD_ID = "invplus";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@SuppressWarnings("UnreachableCode")
    @Override
	public void onInitialize() {
		DcqMenuScreens.LOGGER.info("MenuScreens: Loading...");
		ClientLifecycleEvents.CLIENT_STARTED.register(TextureLoaded::MinecraftStarted);
		//InvalidateRenderStateCallback.EVENT.register(TextureLoaded::LoadEnded);
		DcqInv.addModdedSlot("SimpleInvSlot", DcqSlot.class);
		//DcqInv.addAllResources("test","test");
	}
}