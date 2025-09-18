package dcq.invplus.DcqMenu;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class DcqMenuScreens {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Map<MenuType<?>, ScreenConstructor<?, ?>> SCREENS = Maps.<MenuType<?>, ScreenConstructor<?, ?>>newHashMap();

    public static <T extends AbstractContainerMenu> void create(MenuType<T> menuType, Minecraft minecraft, int i, Component component, String configs) {
        ScreenConstructor<T, ?> screenConstructor = getConstructor(menuType);
        if (screenConstructor == null) { LOGGER.warn("Failed to create screen for dcq menu type: {}", BuiltInRegistries.MENU.getKey(menuType));
        } else {
            screenConstructor.fromPacket(component, configs, menuType, minecraft, i);
        }
    }

    @Nullable
    private static <T extends AbstractContainerMenu> ScreenConstructor<T, ?> getConstructor(MenuType<T> menuType) {return (ScreenConstructor<T, ?>)SCREENS.get(menuType);}

    public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(
            MenuType<? extends M> menuType, ScreenConstructor<M, U> screenConstructor
    ) {
        ScreenConstructor<?, ?> screenConstructor2 = (ScreenConstructor<?, ?>)SCREENS.put(menuType, screenConstructor);
        if (screenConstructor2 != null) {
            throw new IllegalStateException("Duplicate registration for dcq " + BuiltInRegistries.MENU.getKey(menuType));
        }
    }

    public static boolean selfTest() {
        boolean bl = false;
        for (MenuType<?> menuType : BuiltInRegistries.MENU) {
            if (!SCREENS.containsKey(menuType)) {
                LOGGER.debug("Dcq Menu {} has no matching screen", BuiltInRegistries.MENU.getKey(menuType));
                bl = true;
            }
        }
        return bl;
    }

    @Environment(EnvType.CLIENT)
    public interface ScreenConstructor<T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> {
        default void fromPacket(Component component, String configs, MenuType<T> menuType, Minecraft minecraft, int i) {
            U screen = this.create(menuType.create(i, minecraft.player.getInventory()), minecraft.player.getInventory(), component, configs);
            minecraft.player.containerMenu = screen.getMenu();
            minecraft.setScreen(screen);
        }
        U create(T abstractContainerMenu, Inventory inventory, Component component, String configs);
    }
}
