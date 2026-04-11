package com.lucas.ssvp_api.controllers;

import com.lucas.ssvp_api.dto.LoginDTO;
import com.lucas.ssvp_api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/assistido")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        String token = service.login(dto);
        return ResponseEntity.ok(token);
    }
}
