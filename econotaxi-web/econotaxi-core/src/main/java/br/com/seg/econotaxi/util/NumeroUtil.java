package br.com.seg.econotaxi.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumeroUtil {

    public static double doubleDuasDecimais(BigDecimal valor) {
        DecimalFormat df = new DecimalFormat(".##");
        return Double.valueOf(df.format(valor.doubleValue()).replace(",", "."));
    }

}
