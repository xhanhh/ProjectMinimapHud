package top.ilov.mcmods.projectmh.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import top.ilov.mcmods.projectmh.ProjectConfig;
import top.ilov.mcmods.projectmh.utils.FMLUtils;

import java.util.List;
import java.util.Set;

public class ProjectMixinPlugin implements IMixinConfigPlugin {

    public static ProjectConfig CONFIG = new ProjectConfig();

    @Override
    public void onLoad(String mixinPackage) {
        try {
            CONFIG = ProjectConfig.loadConfig();
        } catch (Exception e) {
            CONFIG = new ProjectConfig();
        }
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName) {

            case "top.ilov.mcmods.projectmh.mixin.xaero.XaeroDisplayMixin" ->
                    FMLUtils.isClassPresent("xaero.hud.minimap.info.BuiltInInfoDisplays")
                            && CONFIG.isEnableXaeroMinimapEMCDisplay();

            default -> false;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

}
