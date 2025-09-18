package dcq.invplus_mixin;

import dcq.invplus.TextureLoaded;
import dcq.invplus.configuration.DcqConfigs;
import dcq.invplus.extra.MenuHandler;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MenuType.class)
public class Menu_Handler implements MenuHandler {

    private String dcqConfigs;

    @Override
    public void setCfg(String configs) {
        this.dcqConfigs = configs;
    }

    @Override
    public String getCfg() {
        return dcqConfigs;
    }
}
