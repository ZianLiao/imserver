package com.css.utils;

import java.text.DecimalFormat;

public abstract class FormatUtils {

    static DecimalFormat doubleFormat = new DecimalFormat("######0.00");

    public static double format2bit(Double value) {
        if (null == value) {
            return 0;
        }
        return Double.parseDouble(doubleFormat.format(value));
    }

    public static String format2Str(Double value) {
        if (null == value) {
            return "  0.00";
        }
        return doubleFormat.format(value);
    }

}
