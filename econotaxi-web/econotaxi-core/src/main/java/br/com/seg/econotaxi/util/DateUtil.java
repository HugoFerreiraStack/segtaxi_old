package br.com.seg.econotaxi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String formatarDuracao(Date dataInicio, Date dataFinal) {

        StringBuilder duracao = new StringBuilder();

        long diferencaSegundos = (dataFinal.getTime() - dataInicio.getTime()) / (1000);

        long horas = diferencaSegundos / 3600;
        long sobraSegundosHoras = diferencaSegundos % 3600;

        long minutos = sobraSegundosHoras / 60;
        long segundos = sobraSegundosHoras % 60;

        duracao.append(horas);
        duracao.append("hr ");
        duracao.append(minutos);
        duracao.append("min ");
        duracao.append(segundos);
        duracao.append("seg");

        return duracao.toString();
    }

    public static String formatarData(Date data, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(data);
    }

}
