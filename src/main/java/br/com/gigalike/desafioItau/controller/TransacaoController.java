package br.com.gigalike.desafioItau.controller;
import br.com.gigalike.desafioItau.dto.EstatisticaDto;
import br.com.gigalike.desafioItau.dto.TransacaoDto;
import br.com.gigalike.desafioItau.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Transação", description = "Endpoints de gestão de transações.")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Operation(summary = "Cadastro de transação", description = "Faz a inclusão da transação na memória.")
    @PostMapping("/transacao")
    public ResponseEntity<Void> cadastrarTransacao(@RequestBody TransacaoDto transacaoDto){
        transacaoService.adicionarTransacao(transacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Limpa todas as transações", description = "Realiza a exclusão de todas transações incluidas na memória.")
    @DeleteMapping("/transacao")
    public ResponseEntity<Void> limparTransacoes(){
        transacaoService.limparTransacoes();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Exibe as estatísticas", description = "Retorna a estatística das transações contendo: contagem, soma, média, minimo e máximo ")
    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticaDto> calcularEstatistica(@RequestParam(name = "segundos", defaultValue = "60") int segundos){
        EstatisticaDto estatisticaDto = transacaoService.calcularEstatistica(segundos);
        return ResponseEntity.ok().body(estatisticaDto);
    }

}
