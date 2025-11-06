package br.com.gigalike.desafioItau.utils;
import java.util.Locale;

public class FormatadorNumerico {

    public static double formatarParaDuasCasasDecimais(Double valor){
            return Double.parseDouble(String.format(Locale.US,"%.2f",valor));
    }
}
