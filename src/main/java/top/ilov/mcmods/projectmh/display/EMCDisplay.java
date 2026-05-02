package top.ilov.mcmods.projectmh.display;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import moze_intel.projecte.api.capabilities.IKnowledgeProvider;
import moze_intel.projecte.api.capabilities.PECapabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import top.ilov.mcmods.projectmh.ProjectHudMod;
import top.ilov.mcmods.projectmh.mixin.ProjectMixinPlugin;
import xaero.hud.minimap.info.InfoDisplay;
import xaero.hud.minimap.info.render.compile.InfoDisplayCompiler;
import xaero.hud.minimap.module.MinimapSession;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EMCDisplay {

    private static final String PE_ICON_CHAR = "\uE000";
    private static final Style PE_ICON_STYLE = Style.EMPTY.withFont(
            ResourceLocation.fromNamespaceAndPath(ProjectHudMod.MODID, "pe_icons")
    );

    private static final BigInteger THOUSAND = BigInteger.valueOf(1000);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger RATE_HIGH_THRESHOLD = BigInteger.valueOf(1_000_000);

    private static BigInteger lastEmc = null;
    private static long lastSampleMs = 0;
    private static final BigInteger[] rateHistory = new BigInteger[]{BigInteger.ZERO, BigInteger.ZERO};

    public static void compile(
            InfoDisplay<Boolean> displayInfo,
            InfoDisplayCompiler compiler,
            MinimapSession session,
            int availableWidth,
            net.minecraft.core.BlockPos playerPos
    ) {

        if (!Boolean.TRUE.equals(displayInfo.getEffectiveState())) {
            return;
        }
        if (ProjectMixinPlugin.CONFIG == null || !ProjectMixinPlugin.CONFIG.isEnableXaeroMinimapEMCDisplay()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }

        IKnowledgeProvider provider = mc.player.getCapability(PECapabilities.KNOWLEDGE_CAPABILITY);
        if (provider == null) {
            return;
        }

        BigInteger emc = provider.getEmc();
        boolean showFull = ProjectMixinPlugin.CONFIG.isEnableXaeroMinimapEMCDisplayHoldShiftShowFull() && Screen.hasShiftDown();
        String emcText = showFull ? formatFull(emc) : formatShort(emc);

        MutableComponent line = Component.empty();
        if (ProjectMixinPlugin.CONFIG.isEnableXaeroMinimapEMCIcon()) {
            line.append(Component.literal(PE_ICON_CHAR).withStyle(PE_ICON_STYLE))
                    .append(Component.literal(" "));
        }

        line.append(Component.literal("EMC: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(emcText).withStyle(ChatFormatting.LIGHT_PURPLE)));

        if (ProjectMixinPlugin.CONFIG.isEnableXaeroMinimapEMCDisplayRate()) {
            BigInteger rate = getAverageRatePerSecond(emc);
            if (rate.signum() != 0) {
                ChatFormatting rateColor = getRateColor(rate);
                String rateValue = showFull ? formatFull(rate.abs()) : formatShort(rate.abs());
                String rateText = (rate.signum() > 0 ? "+" : "-") + rateValue + "/s";
                line.append(Component.literal(" "))
                        .append(Component.literal(rateText).withStyle(rateColor));
            }
        }

        compiler.addLine(line);
    }

    private static BigInteger getAverageRatePerSecond(BigInteger currentEmc) {

        long now = System.currentTimeMillis();
        if (lastEmc == null) {
            lastEmc = currentEmc;
            lastSampleMs = now;
            rateHistory[0] = BigInteger.ZERO;
            rateHistory[1] = BigInteger.ZERO;
            return BigInteger.ZERO;
        }

        long dtMs = now - lastSampleMs;
        if (dtMs < 1000) {
            return rateHistory[0].add(rateHistory[1]).divide(TWO);
        }

        BigInteger dt = BigInteger.valueOf(dtMs);
        BigInteger delta = currentEmc.subtract(lastEmc);
        BigInteger rate = delta.multiply(THOUSAND).divide(dt);

        rateHistory[1] = rateHistory[0];
        rateHistory[0] = rate;
        lastEmc = currentEmc;
        lastSampleMs = now;

        return rateHistory[0].add(rateHistory[1]).divide(TWO);

    }

    private static ChatFormatting getRateColor(BigInteger rate) {

        int sign = rate.signum();
        BigInteger abs = rate.abs();
        if (sign > 0) {
            return abs.compareTo(RATE_HIGH_THRESHOLD) >= 0 ? ChatFormatting.GREEN : ChatFormatting.BLUE;
        } else if (sign < 0) {
            return abs.compareTo(RATE_HIGH_THRESHOLD) >= 0 ? ChatFormatting.RED : ChatFormatting.YELLOW;
        }
        return ChatFormatting.WHITE;

    }

    private static String formatFull(BigInteger value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }

    private static String formatShort(BigInteger value) {
        if (value.signum() < 0) {
            return "-" + formatShort(value.negate());
        }
        if (value.compareTo(BigInteger.valueOf(1000)) < 0) {
            return value.toString();
        }

        BigDecimal decimal = new BigDecimal(value);
        NumberName name = NumberName.findName(decimal);

        BigDecimal scaled = decimal.divide(name.getBigDecimalValue(), 2, RoundingMode.DOWN);
        String s = scaled.stripTrailingZeros().toPlainString();
        return s + name.getShortName();
    }


}