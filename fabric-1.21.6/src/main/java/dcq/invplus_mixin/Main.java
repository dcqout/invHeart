package dcq.invplus_mixin;

import dcq.invplus.TextureLoaded;
import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class Main {

    @Inject(method = "onResourceLoadFinished", at = @At("HEAD"))
    public void ResourceLoadFinished(CallbackInfo info) {
        TextureLoaded.LoadEnded();
    }
}
