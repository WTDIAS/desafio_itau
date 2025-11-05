package br.com.gigalike.desafioItau.service;
import br.com.gigalike.desafioItau.dto.TransacaoDto;
import br.com.gigalike.desafioItau.exception.DesafioItauException422;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    private final List<TransacaoDto> transacoes = new ArrayList<>();

    public void adicionarTransacao(TransacaoDto transacaoDto) {
        if (transacaoDto.valor() == null ||transacaoDto.valor() < 0 || transacaoDto.dataHora() == null || transacaoDto.dataHora().isAfter(OffsetDateTime.now())){
            throw new DesafioItauException422("");
        }
        transacoes.add(transacaoDto);
        System.out.println(transacoes);
    }

}
