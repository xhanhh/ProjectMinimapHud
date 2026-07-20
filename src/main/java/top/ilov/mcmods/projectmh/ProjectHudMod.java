package top.ilov.mcmods.projectmh;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ilov.mcmods.projectmh.integration.clothconfig.ClothConfig;
import top.ilov.mcmods.projectmh.utils.FMLUtils;

@Mod(ProjectHudMod.MODID)
public class ProjectHudMod {

    public static final String MODID = "projectminimaphud";

    public static final Logger LOGGER = LoggerFactory.getLogger("Project Minimap Hud");

    public static ProjectConfig CONFIG = new ProjectConfig();

    @SuppressWarnings("removal")
    public ProjectHudMod() {
        CONFIG = ProjectConfig.loadConfig();

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::clientSetup);

        LOGGER.info("[Project Minimap Hud] Mod loaded!");
    }

    @SuppressWarnings("removal")
    private void clientSetup(final FMLClientSetupEvent event) {
        if (FMLUtils.isModLoaded("cloth_config")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) ->
                            ClothConfig.genConfigScreen(screen)));
        }
    }

}
