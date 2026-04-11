package com.lucas.ssvp_api.services;

import com.lucas.ssvp_api.dto.AssistidoDTO;
import com.lucas.ssvp_api.dto.CriarAssistidoDTO;
import com.lucas.ssvp_api.model.entities.Assistido;
import com.lucas.ssvp_api.model.entities.Conferencia;
import com.lucas.ssvp_api.repositories.AssistidoRepository;
import com.lucas.ssvp_api.repositories.ConferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AssistidoService {

    private final AssistidoRepository repository;
    private final ConferenciaRepository conferenciaRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // CADASTRAR ASSISTIDO
    public AssistidoDTO cadastrarAssistido(CriarAssistidoDTO dto) {
        Conferencia conferencia = conferenciaRepository.findById(dto.conferenciaId())
                .orElseThrow(() -> new RuntimeException("Conferência não encontrada"));

        Assistido assistido = repository.save(new Assistido(
                null,
                dto.nome(),
                dto.cpf(),
                passwordEncoder.encode(dto.pin()),
                BigDecimal.ZERO,
                conferencia));

        return new AssistidoDTO(
                assistido.getNome(),
                assistido.getCpf(),
                assistido.getSaldo(),
                assistido.getConferencia().getId());
    }

    // BUSCA ASSISTIDO PELO ID
    public AssistidoDTO buscarPorId (Long id) {
        Assistido assistido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assistido não encontrado"));

        return new AssistidoDTO(
                assistido.getNome(),
                assistido.getCpf(),
                assistido.getSaldo(),
                assistido.getConferencia().getId());
    }
}
