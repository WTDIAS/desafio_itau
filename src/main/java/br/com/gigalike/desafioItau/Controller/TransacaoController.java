package br.com.gigalike.desafioItau.Controller;
import br.com.gigalike.desafioItau.dto.EstatisticaDto;
import br.com.gigalike.desafioItau.dto.TransacaoDto;
import br.com.gigalike.desafioItau.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TransacaoController {

    @Autowired
    TransacaoService transacaoService;

    @PostMapping("/transacao")
    public ResponseEntity<Void> cadastrarTransacao(@RequestBody TransacaoDto transacaoDto){
        transacaoService.adicionarTransacao(transacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> limparTransacoes(){
        transacaoService.limparTransacoes();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticaDto> calcularEstatistica(@RequestParam(name = "segundos", defaultValue = "60") int segundos){
        EstatisticaDto estatisticaDto = transacaoService.calcularEstatistica(segundos);
        return ResponseEntity.ok().body(estatisticaDto);
    }

}
