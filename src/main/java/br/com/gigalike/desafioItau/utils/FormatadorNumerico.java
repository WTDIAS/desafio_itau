package br.com.gigalike.desafioItau.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Esta classe é responsável por formatar as casas decimais mantendo sempre
 * e apenas duas.
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */

public class FormatadorNumerico {

    public static final Logger log = LoggerFactory.getLogger(FormatadorNumerico.class);

    public static double formatarParaDuasCasasDecimais(Double valor){
        if(valor == null){
            return 0.00;
        }

        BigDecimal bigDecimal = BigDecimal.valueOf(valor);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        log.info("Arredondamento de casas decimais de: {} para: {}",valor,bigDecimal);
        return  bigDecimal.doubleValue();
    }
}
