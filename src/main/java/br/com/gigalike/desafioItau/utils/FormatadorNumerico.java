package br.com.gigalike.desafioItau.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class FormatadorNumerico {

    public static final Logger log = LoggerFactory.getLogger(FormatadorNumerico.class);

    public static double formatarParaDuasCasasDecimais(Double valor){
        double retorno = 0.0;
        try {
            retorno = Double.parseDouble(String.format(Locale.US, "%.2f", valor));
        }catch (Exception e){
            log.error("Erro na formatação de casas decimais para o valor: {}",valor,e);
        }
        return retorno;
    }
}
