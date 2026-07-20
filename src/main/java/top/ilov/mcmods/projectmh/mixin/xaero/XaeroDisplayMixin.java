package top.ilov.mcmods.projectmh.mixin.xaero;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ilov.mcmods.projectmh.display.MinimapDisplays;
import xaero.hud.minimap.info.BuiltInInfoDisplays;
import xaero.hud.minimap.info.InfoDisplay;

import java.util.List;
import java.util.Objects;

@Mixin(value = BuiltInInfoDisplays.class, remap = false)
public class XaeroDisplayMixin {

    @Shadow(remap = false)
    private static List<InfoDisplay<?>> ALL;

    @Inject(method = "<clinit>", at = @At("TAIL"), remap = false)
    private static void xaeroInject(CallbackInfo ci) {
        Objects.requireNonNull(ALL);
        MinimapDisplays.PE_EMC = MinimapDisplays.PE_EMC_INFO_BUILDER.setDestination(ALL::add).build();
    }

}
