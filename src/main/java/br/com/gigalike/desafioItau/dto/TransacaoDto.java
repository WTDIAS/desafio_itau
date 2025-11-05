package br.com.gigalike.desafioItau.dto;
import java.time.OffsetDateTime;

public record TransacaoDto(Double valor, OffsetDateTime dataHora) {
}


