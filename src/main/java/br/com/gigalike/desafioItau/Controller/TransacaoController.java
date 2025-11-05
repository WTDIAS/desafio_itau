package br.com.gigalike.desafioItau.Controller;
import br.com.gigalike.desafioItau.dto.TransacaoDto;
import br.com.gigalike.desafioItau.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Void> cadastrarTransacao(@RequestBody TransacaoDto transacaoDto){
        transacaoService.adicionarTransacao(transacaoDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> limparTransacoes(){
        transacaoService.limparTransacoes();
        return ResponseEntity.ok().build();
    }
}
