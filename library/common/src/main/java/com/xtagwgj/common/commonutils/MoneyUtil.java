package com.xtagwgj.common.commonutils;

import java.text.DecimalFormat;

/**
 * des:金钱
 * Created by zy
 */
public class MoneyUtil {
    public static String MoneyFomatWithTwoPoint(double d){
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }
}
