package top.ilov.mcmods.projectmh;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import top.ilov.mcmods.projectmh.integration.clothconfig.ClothConfig;
import top.ilov.mcmods.projectmh.utils.FMLUtils;

import java.util.function.Supplier;

@Mod(value = ProjectHudMod.MODID, dist = Dist.CLIENT)
public class ProjectHudClientMod {

    public ProjectHudClientMod(ModContainer container) {

        if (FMLUtils.isModLoaded("cloth_config")) {
            container.registerExtensionPoint(IConfigScreenFactory.class,
                    (Supplier<IConfigScreenFactory>) () ->
                            (mod, parent) -> ClothConfig.genConfigScreen(parent)
            );
        }

    }

}
