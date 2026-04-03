package com.lucas.ssvp_api.services;

import com.lucas.ssvp_api.dto.AssistidoDTO;
import com.lucas.ssvp_api.dto.CriarAssistidoDTO;
import com.lucas.ssvp_api.model.entities.Assistido;
import com.lucas.ssvp_api.model.entities.Conferencia;
import com.lucas.ssvp_api.repositories.AssistidoRepository;
import com.lucas.ssvp_api.repositories.ConferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AssistidoService {

    private final AssistidoRepository repository;
    private final ConferenciaRepository conferenciaRepository;

    // CADASTRAR ASSISTIDO
    public AssistidoDTO cadastrarAssistido(CriarAssistidoDTO dto) {
        Conferencia conferencia = conferenciaRepository.findById(dto.conferenciaId())
                .orElseThrow(() -> new RuntimeException("Conferência não encontrada"));

        Assistido assistido = repository.save(new Assistido(
                null,
                dto.nome(),
                dto.cpf(),
                dto.pin(),
                BigDecimal.ZERO,
                conferencia));

        return new AssistidoDTO(
                assistido.getNome(),
                assistido.getCpf(),
                assistido.getSaldo(),
                assistido.getConferencia().getId());
    }
}
