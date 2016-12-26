package com.xtagwgj.common.commonutils;

import java.text.DecimalFormat;

/**
 * des:金钱
 * Created by zy
 */
public class MoneyUtil {

    public static String WithTwoPointFormat(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    public static String WithCharFormat(double d) {
        DecimalFormat df = new DecimalFormat("¥, #.00");
        return df.format(d);
    }
}
