package dcq.invplus.configuration;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DcqInvResource {

    private final String path;
    private final Map<String, ResourceLocation> resources = new HashMap<>();

    public Collection<ResourceLocation> getAll() {
        return resources.values();
    }

    public void add(String modId, String fromPath) {
        resources.putIfAbsent(modId,ResourceLocation.withDefaultNamespace(path+"."+fromPath.toLowerCase()+".json"));
    }

    public DcqInvResource(String fromPath) {
        this.path = fromPath;
    }
}
