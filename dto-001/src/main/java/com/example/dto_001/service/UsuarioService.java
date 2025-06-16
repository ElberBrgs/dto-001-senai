package com.example.dto_001.service;

import com.example.dto_001.dto.UsuarioDTO;
import com.example.dto_001.dto.UsuarioResponseDTO;
import com.example.dto_001.model.UsuarioEntity;
import com.example.dto_001.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioEntity salvarUsuario(UsuarioDTO dto){
        usuarioRepository.findByEmail(dto.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Usuário já cadastrado com o email: ");});

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::toDTO).toList();
    }

    public UsuarioResponseDTO toDTO(UsuarioEntity usuario) {
        return new UsuarioResponseDTO(usuario.getNome(), usuario.getEmail());
    }

    public UsuarioEntity atualizar(String email, UsuarioDTO dto){
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public void exluir(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        usuarioRepository.delete(usuario);
    }
}
