package top.ilov.mcmods.projectmh.display;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
public enum NumberName {

    THOUSAND(1e3, "K"),
    MILLION(1e6, "M"),
    BILLION(1e9, "B"),
    TRILLION(1e12, "T"),
    QUADRILLION(1e15, "Qa"),
    QUINTILLION(1e18, "Qi"),
    SEXTILLION(1e21, "Sx"),
    SEPTILLION(1e24, "Sp"),
    OCTILLION(1e27, "O"),
    NONILLION(1e30, "N"),
    DECILLION(1e33, "D"),
    UNDECILLION(1e36, "U"),
    DUODECILLION(1e39, "Du"),
    TREDECILLION(1e42, "Tr"),
    QUATTUORDECILLION(1e45, "Qt"),
    QUINDECILLION(1e48, "Qd"),
    SEXDECILLION(1e51, "Sd"),
    SEPTENDECILLION(1e54, "St"),
    OCTODECILLION(1e57, "Oc"),
    NOVEMDECILLION(1e60, "No");

    private static final NumberName[] VALUES = values();

    private final BigDecimal bigDecimalValue;
    private final String shortName;

    NumberName(double value, String shortName) {
        this.bigDecimalValue = new BigDecimal(String.valueOf(value));
        this.shortName = shortName;
    }

    public static NumberName findName(BigDecimal value) {
        return Arrays.stream(VALUES)
                .filter(v -> value.compareTo(v.getBigDecimalValue()) >= 0)
                .reduce((first, second) -> second)
                .orElse(null);
    }

}
