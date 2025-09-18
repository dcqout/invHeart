package dcq.invplus_mixin;

import dcq.invplus.DcqMenu.DcqMenuScreens;
import dcq.invplus.extra.MenuHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MenuScreens.class)
public class Menu_Screens {

    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private static <T extends AbstractContainerMenu> void create(MenuType<T> menuType, Minecraft minecraft, int i, Component component, CallbackInfo info) {
        DcqMenuScreens.ScreenConstructor<?, ?> screen = DcqMenuScreens.SCREENS.get(menuType);
        if (screen != null) {
            DcqMenuScreens.create(menuType,minecraft,i,component,((MenuHandler)menuType).getCfg());
            info.cancel();
        }
    }
}
