package com.lucas.ssvp_api.services;

import com.lucas.ssvp_api.dto.LoginDTO;
import com.lucas.ssvp_api.model.entities.Assistido;
import com.lucas.ssvp_api.repositories.AssistidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AssistidoRepository assistidoRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Map<String, Assistido> sessoes = new HashMap<>();

    // AUTENTICAÇÃO DE ASSISTIDOS
    public String login(LoginDTO dto) {
        Assistido assistido = assistidoRepository.findByCpf(dto.cpf())
                .orElseThrow(() -> new RuntimeException("Assistido não encontrado"));

        // valida PIN
        if (!passwordEncoder.matches(dto.pin(), assistido.getPin())) {
            throw new RuntimeException("CPF ou PIN incorretos");
        }

        // gera token fake
        String token = UUID.randomUUID().toString();

        // salva sessao
        sessoes.put(token, assistido);

        return token;
    }

    // BUSCAR ASSISTIDO PELO TOKEN
    public Assistido getByToken(String token) {
        Assistido assistido = sessoes.get(token);

        if (assistido == null) {
            throw new RuntimeException("Token inválido");
        }

        return assistido;
    }
}
