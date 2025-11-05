package br.com.gigalike.desafioItau.service;
import br.com.gigalike.desafioItau.dto.EstatisticaDto;
import br.com.gigalike.desafioItau.dto.TransacaoDto;
import br.com.gigalike.desafioItau.exception.DesafioItauException422;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final List<TransacaoDto> transacoes = new ArrayList<>();

    public void adicionarTransacao(TransacaoDto transacaoDto) {
        if (
                transacaoDto.valor() == null ||
                transacaoDto.dataHora() == null ||
                transacaoDto.valor() < 0 ||
                transacaoDto.dataHora().isAfter(OffsetDateTime.now())
        )
        {
            throw new DesafioItauException422("");
        }
        transacoes.add(transacaoDto);
        System.out.println(transacoes);
    }

    public void limparTransacoes(){
        transacoes.clear();
        System.out.println(transacoes);
    }

    public EstatisticaDto calcularEstatistica(){
        if (transacoes.isEmpty()){
            return new EstatisticaDto(0,0,0,0,0);
        }

        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime intervaloDeCalculo = agora.minusSeconds(60);

        DoubleSummaryStatistics dss = transacoes.stream()
                .filter(transacao -> !transacao.dataHora().isBefore(intervaloDeCalculo))
                .collect(Collectors.summarizingDouble(TransacaoDto::valor));

        System.out.println("AGORA: "+agora);
        System.out.println(dss);

        return new EstatisticaDto(dss.getCount(),dss.getSum(),dss.getAverage(),dss.getMin(),dss.getMax());
    }
}
