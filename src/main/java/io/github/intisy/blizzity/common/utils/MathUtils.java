package io.github.intisy.blizzity.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Mathematical utility functions.
 * @author Finn Birich
 */

public class MathUtils {
    public static Number addNumbers(Number a, Number b) {
        BigDecimal bd1 = new BigDecimal(a.toString());
        BigDecimal bd2 = new BigDecimal(b.toString());
        return bd1.add(bd2);
    }

    public static Number multiplyNumbers(Number a, Number b) {
        BigDecimal bd1 = new BigDecimal(a.toString());
        BigDecimal bd2 = new BigDecimal(b.toString());
        return bd1.multiply(bd2);
    }

    public static Number divideNumbers(Number a, Number b) {
        BigDecimal bd1 = new BigDecimal(a.toString());
        BigDecimal bd2 = new BigDecimal(b.toString());
        return bd1.divide(bd2, 10, RoundingMode.HALF_UP);
    }

    public static Number subtractNumbers(Number a, Number b) {
        BigDecimal bd1 = new BigDecimal(a.toString());
        BigDecimal bd2 = new BigDecimal(b.toString());
        return bd1.subtract(bd2);
    }
}
