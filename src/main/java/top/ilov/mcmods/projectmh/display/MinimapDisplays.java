package top.ilov.mcmods.projectmh.display;

import net.minecraft.network.chat.Component;
import xaero.hud.minimap.info.InfoDisplay;
import xaero.hud.minimap.info.InfoDisplay.Builder;
import xaero.hud.minimap.info.codec.InfoDisplayCommonStateCodecs;
import xaero.hud.minimap.info.widget.InfoDisplayCommonWidgetFactories;

public final class MinimapDisplays {

    public static final String PE_EMC_INFO_ID = "projecthud_pe_emc";
    public static final Builder<Boolean> PE_EMC_INFO_BUILDER;
    public static InfoDisplay<Boolean> PE_EMC;

    static {
        PE_EMC_INFO_BUILDER = Builder.<Boolean>begin()
                .setId(PE_EMC_INFO_ID)
                .setName(Component.translatable("xaerominimap.projectminimaphud.infodisplay.pe_emc"))
                .setDefaultState(true)
                .setCodec(InfoDisplayCommonStateCodecs.BOOLEAN)
                .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
                .setCompiler(EMCDisplay::compile);
    }

    private MinimapDisplays() {
    }
}
