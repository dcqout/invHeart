package dcq.invplus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dcq.invplus.Inventory;
import dcq.invplus.TextureLoaded;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class DcqInvConfiguration<T extends DcqConfigFile> {

    private final Map<String, T> configsMap = new HashMap<>();

    public T get(String key) {
        return configsMap.get(key);
    }

    public void clear() {
        configsMap.clear();
    }

    public void createConditional(Reader reader) {
        for (JsonElement jsonElement : GsonHelper.parseArray(reader)) {
            prepare_conditional(jsonElement);
        }
    }

    public void createInv(Reader reader) {
        for (JsonElement jsonElement : GsonHelper.parseArray(reader)) {
            prepare_inventory(jsonElement);
        }
    }

    public void createRegular(Reader reader) {
        for (JsonElement jsonElement : GsonHelper.parseArray(reader)) {
            prepare_regular(jsonElement);
        }
    }

    private void prepare_regular(JsonElement jsonElement) {
        boolean conditional = false;
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        conditional = jsonObject.has("Conditional");
        if (conditional) {
            boolean conditionMet = true;
            for (Map.Entry<String, JsonElement> jsonElementEntry : jsonObject.getAsJsonObject("Conditional").entrySet()) {
                if (StringUtils.equals(jsonElementEntry.getKey(),"Skip") && jsonElementEntry.getValue().getAsBoolean()) {
                    break;
                }
                if (StringUtils.equals(jsonElementEntry.getKey(),"ApplyFromList")) {
                    DcqConfigs conditions = (DcqConfigs) TextureLoaded.ConditionList.get(jsonElementEntry.getValue().getAsString());
                    if (resultcheck((T) conditions)) {
                        copyFrom((T) conditions);
                    }
                    continue;
                }
                if (!resultcheck(jsonElementEntry)) { conditionMet = false; break; }
            }
            if (!conditionMet) {
                return;
            }
        }
        for (Map.Entry<String, JsonElement> jsonElementEntry : jsonObject.entrySet()) {
            if (StringUtils.equals(jsonElementEntry.getKey(),"Conditional")) { continue; }
            if (configsMap.containsKey(jsonElementEntry.getKey())) {
                ((DcqConfigs)configsMap.get(jsonElementEntry.getKey())).merge(jsonElementEntry.getValue());
            } else {
                Inventory.LOGGER.info(jsonElementEntry.getKey());
                configsMap.put(jsonElementEntry.getKey(), (T) new DcqConfigs(jsonElementEntry.getKey(),jsonElementEntry.getValue().getAsJsonObject()));
            }
        }
    }

    private void prepare_inventory(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> jsonElementEntry : jsonObject.entrySet()) {
            if (configsMap.containsKey(jsonElementEntry.getKey())) {
                ((DcqInvConfigs)configsMap.get(jsonElementEntry.getKey())).merge(jsonElementEntry.getValue());
            } else {
                Inventory.LOGGER.info(jsonElementEntry.getKey());
                configsMap.put(jsonElementEntry.getKey(), (T) new DcqInvConfigs(jsonElementEntry.getKey(),jsonElementEntry.getValue().getAsJsonObject()));
            }
        }
    }

    private void prepare_conditional(JsonElement jsonElement) {
        for (Map.Entry<String, JsonElement> jsonElementEntry : jsonElement.getAsJsonObject().entrySet()) {
            JsonObject target = jsonElementEntry.getValue().getAsJsonObject();
            String targetKey = target.get("Target").getAsString();
            configsMap.putIfAbsent(targetKey, (T) new DcqConfigs(targetKey,target.getAsJsonObject()));
        }
    }

    private boolean resultcheck(T dcqConfigs) {
        return false;
    }

    private boolean resultcheck(Map.Entry<String, JsonElement> mapEntry) {
        return false;
    }

    private void copyFrom(T configs) {
        if (configsMap.containsKey(configs.getKey())) {
            ((DcqConfigs)configsMap.get(configs.getKey())).merge((DcqConfigs) configs);
        } else {
            configsMap.put(configs.getKey(), (T) configs);
        }
    }

    @FunctionalInterface
    public interface DcqConfigsReader {
        void read(Reader reader) throws IOException;
    }
}
