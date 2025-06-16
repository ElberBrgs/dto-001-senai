package com.example.dto_001.controller;

import com.example.dto_001.dto.UsuarioDTO;
import com.example.dto_001.dto.UsuarioResponseDTO;
import com.example.dto_001.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PostMapping
    public ResponseEntity<Map<Object,String>> salvar(@Valid @RequestBody UsuarioDTO dto){
        usuarioService.salvarUsuario(dto);
        return ResponseEntity.ok(Map.of("message", "Usuário cadastrado com sucesso!"));
    }

    @PutMapping("/{email}")
    public ResponseEntity<Map<Object,String>> atualizar(@PathVariable String email, @Valid @RequestBody UsuarioDTO dto){
        usuarioService.atualizar(email, dto);
        return ResponseEntity.ok(Map.of("message", "Usuário atualizado com sucesso!"));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Map<Object,String>> excluir(@PathVariable String email){
        usuarioService.exluir(email);
        return ResponseEntity.ok(Map.of("message", "Usuário excluído com sucesso!"));
    }

}
