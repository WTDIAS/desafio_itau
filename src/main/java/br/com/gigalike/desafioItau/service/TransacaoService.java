package br.com.gigalike.desafioItau.service;
import br.com.gigalike.desafioItau.dto.EstatisticaDto;
import br.com.gigalike.desafioItau.dto.TransacaoDto;
import br.com.gigalike.desafioItau.exception.UnprocessableEntityException;
import br.com.gigalike.desafioItau.utils.FormatadorNumerico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final List<TransacaoDto> transacoes = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);
    private OffsetDateTime agora;

    public void adicionarTransacao(TransacaoDto transacaoDto) {
        agora = OffsetDateTime.now(ZoneOffset.of("-03:00"));
        if (
                transacaoDto.valor() == null ||
                transacaoDto.dataHora() == null ||
                transacaoDto.valor() < 0 ||
                !transacaoDto.dataHora().isBefore(agora)){
            log.warn("Não foi possível salvar a transação: {}",transacaoDto);
            throw new UnprocessableEntityException("");
        }
        transacoes.add(transacaoDto);
        log.info("Transação adicionada: {}",transacaoDto);
    }

    public void limparTransacoes(){
        transacoes.clear();
        log.info("Transações excluidas: {}",transacoes);
    }

    public EstatisticaDto calcularEstatistica(int segundos){
        EstatisticaDto estatisticaDtoZerado = new EstatisticaDto(0,0,0,0,0);
        if (transacoes.isEmpty()){
            log.info("Calcular estatística foi chamado para lista vazia.");
            return estatisticaDtoZerado;
        }

        agora = OffsetDateTime.now(ZoneOffset.of("-03:00"));
        OffsetDateTime intervaloDeCalculo = agora.minusSeconds(segundos);

        List<TransacaoDto> transacoesFiltradas = transacoes.stream()
                .filter(transacao -> !transacao.dataHora().isBefore(intervaloDeCalculo))
                .toList();

        if (transacoesFiltradas.isEmpty()){
            log.warn("Não encontrado transações para o período de: {}",segundos);
            return estatisticaDtoZerado;
        }

        DoubleSummaryStatistics dss = transacoesFiltradas.stream()
                .collect(Collectors.summarizingDouble(TransacaoDto::valor));

        log.info("Retorno do cálculo de estatística para {} segundos: {}. ",segundos,transacoesFiltradas);
        return new EstatisticaDto(
                dss.getCount(),
                FormatadorNumerico.formatarParaDuasCasasDecimais(dss.getSum()),
                FormatadorNumerico.formatarParaDuasCasasDecimais(dss.getAverage()),
                dss.getMin(),
                dss.getMax());
    }
}
