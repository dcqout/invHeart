package dcq.invplus.api;

import dcq.invplus.TextureLoaded;
import dcq.invplus.configuration.DcqConfigs;
import dcq.invplus.DcqMenu.DcqMenuScreens;
import dcq.invplus.extra.MenuHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;

import java.lang.reflect.InvocationTargetException;

public class DcqInv {

    public static boolean stw = true;
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final TextureLoaded ResourceManager = new TextureLoaded();

    public static <T extends AbstractContainerMenu> void addModdedContainer(String key,Class<?> screen) {
        ResourceManager.ModdedScreens.putIfAbsent(key, screen);
    }

    public static <T extends AbstractDcqSlot> void addModdedSlot(String key,Class<?> slot) {
        ResourceManager.ModdedSlots.putIfAbsent(key, slot);
    }

    public static void addConfigResource(String modId,String path) {
        ResourceManager.RegularJSONS.add(modId,path);
    }

    public static void addConditionResource(String modId,String path) {
        ResourceManager.ConditionsJSONS.add(modId,path);
    }

    public static void addInventoryResource(String modId,String path) {
        ResourceManager.InventoryJSONS.add(modId,path);
    }

    public static void addAllResources(String modId,String path) {
        addConditionResource(modId,path);
        addInventoryResource(modId,path);
        addConfigResource(modId,path);
    }

    public static MenuProvider open(String key, Level level, BlockPos blockPos, Component title) {
        DcqConfigs cfg_key = (DcqConfigs) TextureLoaded.ConfigList.get(key);
        if (cfg_key != null) {
            return new SimpleMenuProvider((i, inventory, player) -> {
                try {
                    dcq.invplus.Inventory.LOGGER.warn("opening "+key+" target: " + TextureLoaded.ModdedScreens.get(key).getConstructor(int.class,Inventory.class, ContainerLevelAccess.class));
                    AbstractContainerMenu result = (AbstractContainerMenu) TextureLoaded.ModdedScreens.get(key).getConstructor(int.class,Inventory.class, ContainerLevelAccess.class).newInstance(i, inventory, ContainerLevelAccess.create(level, blockPos));
                    dcq.invplus.Inventory.LOGGER.warn("result: "+result);
                    return result;
                } catch (InstantiationException e) {
                    throw new RuntimeException("I guess the constructor is not public?: " + e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("WOW, REPORT THIS TO THIS MOD ON GITHUB: " + e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("A ContainerMenu has crashed due to some logic! Review it! Try comparing with an existing one!" + e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("A ContainerMenu constructor was not found! Did you forget to properly register it?" + e);
                }
            }, title);
        } else {
            dcq.invplus.Inventory.LOGGER.warn(key+" Container was not registered");
        }
        return null;
    }

    public static AbstractContainerMenu openWithEntity(String key, Container blockEntityWithContainer, int i, Inventory inventory) {
        DcqConfigs cfg_key = (DcqConfigs) TextureLoaded.ConfigList.get(key);
        if (cfg_key != null) {
            try {
                dcq.invplus.Inventory.LOGGER.warn("opening "+key+" target: " + TextureLoaded.ModdedScreens.get(key).getConstructor(int.class,Inventory.class, Container.class));
                AbstractContainerMenu result = (AbstractContainerMenu) TextureLoaded.ModdedScreens.get(key).getConstructor(int.class,Inventory.class, Container.class).newInstance(i, inventory, blockEntityWithContainer);
                dcq.invplus.Inventory.LOGGER.warn("result: "+result);
                return result;
            } catch (InstantiationException e) {
                throw new RuntimeException("I guess the constructor is not public?: " + e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("WOW, REPORT THIS TO THIS MOD ON GITHUB: " + e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("A ContainerMenu has crashed due to some logic! Review it! Try comparing with an existing one!" + e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("A ContainerMenu constructor was not found! Did you forget to properly register it?" + e);
            }
        } else {
            dcq.invplus.Inventory.LOGGER.warn(key+" Container was not registered");
        }
        return null;
    }

    public static AbstractContainerMenu openWithEntity(String key, Container blockEntityWithContainer, int i, Inventory inventory, ContainerData dataAccess) {
        DcqConfigs cfg_key = (DcqConfigs) TextureLoaded.ConfigList.get(key);
        if (cfg_key != null) {
                try {
                    dcq.invplus.Inventory.LOGGER.warn("opening "+key+" target: " + TextureLoaded.ModdedScreens.get(key).getConstructor(int.class,Inventory.class, Container.class,ContainerData.class));
                    AbstractContainerMenu result = (AbstractContainerMenu) TextureLoaded.ModdedScreens.get(key).getConstructor(int.class,Inventory.class, Container.class,ContainerData.class).newInstance(i, inventory, blockEntityWithContainer, dataAccess);
                    dcq.invplus.Inventory.LOGGER.warn("result: "+result);
                    return result;
                } catch (InstantiationException e) {
                    throw new RuntimeException("I guess the constructor is not public?: " + e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("WOW, REPORT THIS TO THIS MOD ON GITHUB: " + e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("A ContainerMenu has crashed due to some logic! Review it! Try comparing with an existing one!" + e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("A ContainerMenu constructor was not found! Did you forget to properly register it?" + e);
                }
        } else {
            dcq.invplus.Inventory.LOGGER.warn(key+" Container was not registered");
        }
        return null;
    }

    public static AbstractContainerMenu openWithEntity(String key, int i, Inventory inventory, ContainerData dataAccess, ContainerLevelAccess levelAccess) {
        return null;
    }

    public static  <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> MenuType<? extends M> registerMenu(
            String key, MenuType<? extends M> containerMenuMenuType, DcqMenuScreens.ScreenConstructor<M,U> screenConstructor, String keyless) {
        MenuType<? extends M> register_type = Registry.register(BuiltInRegistries.MENU,key,containerMenuMenuType);
        ((MenuHandler)register_type).setCfg(keyless);
        DcqMenuScreens.register(register_type, screenConstructor);
        return register_type;
    }
}
