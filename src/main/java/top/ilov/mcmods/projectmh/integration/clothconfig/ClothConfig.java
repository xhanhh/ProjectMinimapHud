package top.ilov.mcmods.projectmh.integration.clothconfig;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import top.ilov.mcmods.projectmh.ProjectConfig;
import top.ilov.mcmods.projectmh.ProjectHudMod;
import top.ilov.mcmods.projectmh.mixin.ProjectMixinPlugin;

public class ClothConfig {

    public static Screen genConfigScreen(Screen parent) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Component.translatable("config.projectminimaphud.title"))
                .setParentScreen(parent)
                .setSavingRunnable(() -> {
                    ProjectConfig.write(ProjectHudMod.CONFIG);
                    ProjectMixinPlugin.CONFIG = ProjectHudMod.CONFIG;
                });

        ConfigCategory client = builder.getOrCreateCategory(Component.translatable("config.projectminimaphud.client"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        client.addEntry(entryBuilder
                .startBooleanToggle(Component.translatable("config.projectminimaphud.enable_xaerominimap_emc_display"),
                        ProjectHudMod.CONFIG.isEnableXaeroMinimapEMCDisplay())
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.projectminimaphud.enable_xaerominimap_emc_display.tooltip"))
                .setSaveConsumer(newValue -> ProjectHudMod.CONFIG.setEnableXaeroMinimapEMCDisplay(newValue))
                .build()
        );

        client.addEntry(entryBuilder
                .startEnumSelector(
                        Component.translatable("config.projectminimaphud.xaerominimap_emc_display_mode"),
                        ProjectConfig.EMCDisplayMode.class,
                        ProjectHudMod.CONFIG.getXaeroMinimapEMCDisplayMode()
                )
                .setEnumNameProvider(value -> Component.translatable(
                        "config.projectminimaphud.xaerominimap_emc_display_mode." + value.name().toLowerCase()
                ))
                .setDefaultValue(ProjectConfig.EMCDisplayMode.SHORT)
                .setTooltip(Component.translatable("config.projectminimaphud.xaerominimap_emc_display_mode.tooltip"))
                .setSaveConsumer(newValue -> ProjectHudMod.CONFIG.setXaeroMinimapEMCDisplayMode(newValue))
                .build()
        );

        client.addEntry(entryBuilder
                .startBooleanToggle(Component.translatable("config.projectminimaphud.enable_xaerominimap_emc_display_shift_toggle"),
                        ProjectHudMod.CONFIG.isEnableXaeroMinimapEMCDisplayHoldShiftShowFull())
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.projectminimaphud.enable_xaerominimap_emc_display_shift_toggle.tooltip"))
                .setSaveConsumer(newValue ->
                        ProjectHudMod.CONFIG.setEnableXaeroMinimapEMCDisplayHoldShiftShowFull(newValue))
                .build()
        );

        client.addEntry(entryBuilder
                .startBooleanToggle(Component.translatable("config.projectminimaphud.enable_xaerominimap_emc_icon"),
                        ProjectHudMod.CONFIG.isEnableXaeroMinimapEMCIcon())
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.projectminimaphud.enable_xaerominimap_emc_icon.tooltip"))
                .setSaveConsumer(newValue -> ProjectHudMod.CONFIG.setEnableXaeroMinimapEMCIcon(newValue))
                .build()
        );

        client.addEntry(entryBuilder
                .startBooleanToggle(Component.translatable("config.projectminimaphud.enable_xaerominimap_emc_display_rate"),
                        ProjectHudMod.CONFIG.isEnableXaeroMinimapEMCDisplayRate())
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.projectminimaphud.enable_xaerominimap_emc_display_rate.tooltip"))
                .setSaveConsumer(newValue -> ProjectHudMod.CONFIG.setEnableXaeroMinimapEMCDisplayRate(newValue))
                .build()
        );

        return builder.build();

    }

}
