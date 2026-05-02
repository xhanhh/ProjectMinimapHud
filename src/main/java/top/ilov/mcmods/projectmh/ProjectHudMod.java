package top.ilov.mcmods.projectmh;

import net.neoforged.bus.api.IEventBus;
import org.slf4j.Logger;

import net.neoforged.fml.common.Mod;
import org.slf4j.LoggerFactory;
import top.ilov.mcmods.projectmh.mixin.ProjectMixinPlugin;

@Mod(ProjectHudMod.MODID)
public class ProjectHudMod {

    public static final String MODID = "projectminimaphud";

    public static final Logger LOGGER = LoggerFactory.getLogger("Project Minimap Hud");

    public static ProjectConfig CONFIG = new ProjectConfig();

    public ProjectHudMod(IEventBus eventBus) {
        CONFIG = ProjectConfig.loadConfig();
        ProjectMixinPlugin.CONFIG = CONFIG;

        LOGGER.info("[Project Minimap Hud] Mod loaded!");
    }

}
