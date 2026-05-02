package top.ilov.mcmods.projectmh;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.SneakyThrows;
import top.ilov.mcmods.projectmh.utils.FMLUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Data
public final class ProjectConfig {

    public enum EMCDisplayMode {
        SHORT,
        FULL
    }

    private boolean isEnableXaeroMinimapEMCDisplay;

    private boolean isEnableXaeroMinimapEMCDisplayHoldShiftShowFull;

    private boolean isEnableXaeroMinimapEMCDisplayRate;

    private boolean isEnableXaeroMinimapEMCIcon;

    private EMCDisplayMode xaeroMinimapEMCDisplayMode;

    private static final boolean DEFAULT_ENABLE_XAERO_MINIMAP_EMC_DISPLAY = true;
    private static final boolean DEFAULT_XAERO_MINIMAP_EMC_SHIFT_FULL = true;
    private static final boolean DEFAULT_XAERO_MINIMAP_EMC_RATE = false;
    private static final boolean DEFAULT_XAERO_MINIMAP_EMC_ICON = true;
    private static final EMCDisplayMode DEFAULT_XAERO_MINIMAP_EMC_DISPLAY_MODE = EMCDisplayMode.SHORT;

    static File config = new File(FMLUtils.getConfigDir().toFile(), "projectminimaphud-client.json");

    @SneakyThrows
    public static ProjectConfig loadConfig() {

        ProjectConfig defaultConfig = new ProjectConfig();
        defaultConfig.isEnableXaeroMinimapEMCDisplay = DEFAULT_ENABLE_XAERO_MINIMAP_EMC_DISPLAY;
        defaultConfig.isEnableXaeroMinimapEMCDisplayHoldShiftShowFull = DEFAULT_XAERO_MINIMAP_EMC_SHIFT_FULL;
        defaultConfig.isEnableXaeroMinimapEMCDisplayRate = DEFAULT_XAERO_MINIMAP_EMC_RATE;
        defaultConfig.isEnableXaeroMinimapEMCIcon = DEFAULT_XAERO_MINIMAP_EMC_ICON;
        defaultConfig.xaeroMinimapEMCDisplayMode = DEFAULT_XAERO_MINIMAP_EMC_DISPLAY_MODE;

        if (!config.exists()) {
            write(defaultConfig);
            return defaultConfig;
        }

        Gson gson = new Gson();
        JsonObject json;
        try (BufferedReader reader = Files.newBufferedReader(config.toPath(), StandardCharsets.UTF_8)) {
            json = JsonParser.parseReader(reader).getAsJsonObject();
        }

        ProjectConfig loaded = gson.fromJson(json, ProjectConfig.class);
        if (loaded == null) {
            loaded = defaultConfig;
        }

        boolean changed = false;

        if (!json.has("isEnableXaeroMinimapEMCDisplay")) {
            loaded.isEnableXaeroMinimapEMCDisplay = DEFAULT_ENABLE_XAERO_MINIMAP_EMC_DISPLAY;
            changed = true;
        }
        if (!json.has("isEnableXaeroMinimapEMCDisplayHoldShiftShowFull")) {
            loaded.isEnableXaeroMinimapEMCDisplayHoldShiftShowFull = DEFAULT_XAERO_MINIMAP_EMC_SHIFT_FULL;
            changed = true;
        }
        if (!json.has("isEnableXaeroMinimapEMCDisplayRate")) {
            loaded.isEnableXaeroMinimapEMCDisplayRate = DEFAULT_XAERO_MINIMAP_EMC_RATE;
            changed = true;
        }
        if (!json.has("isEnableXaeroMinimapEMCIcon")) {
            loaded.isEnableXaeroMinimapEMCIcon = DEFAULT_XAERO_MINIMAP_EMC_ICON;
            changed = true;
        }

        if (!json.has("xaeroMinimapEMCDisplayMode")) {
            loaded.xaeroMinimapEMCDisplayMode = DEFAULT_XAERO_MINIMAP_EMC_DISPLAY_MODE;
            changed = true;
        } else if (loaded.xaeroMinimapEMCDisplayMode == null) {
            loaded.xaeroMinimapEMCDisplayMode = DEFAULT_XAERO_MINIMAP_EMC_DISPLAY_MODE;
            changed = true;
        }

        if (changed) {
            write(loaded);
        }

        return loaded;

    }

    @SneakyThrows
    public static void write(ProjectConfig projectConfig) {

        Writer writer = Files.newBufferedWriter(
                config.toPath(),
                StandardCharsets.UTF_8
        );
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
        writer.write(gson.toJson(projectConfig));
        writer.close();

    }

}
