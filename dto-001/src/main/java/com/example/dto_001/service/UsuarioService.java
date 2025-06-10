package com.example.dto_001.service;

import com.example.dto_001.dto.UsuarioDTO;
import com.example.dto_001.model.UsuarioEntity;
import com.example.dto_001.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
        if(usuarioRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("Usuário já cadastrado com este email.");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setEmail(dto.getEmail());

        // Criptografando a senha usando BCrypt.
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public List<UsuarioEntity> listarUsuarios() {
        return usuarioRepository.findAll();
    }

}
