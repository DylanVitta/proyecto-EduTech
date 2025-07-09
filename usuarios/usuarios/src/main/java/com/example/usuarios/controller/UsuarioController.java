package com.example.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuarios.DTO.UsuarioDTO;
import com.example.usuarios.model.Login;
import com.example.usuarios.model.usuario;
import com.example.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService ;

    @GetMapping
    @Operation(summary="Permite listar todos los usuarios del sistema")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se muestran todos los usuarios del sistema",
        content=@Content(schema=@Schema(implementation=UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "posible problema en la solicitud"),
    })
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.getUsuariosDTO();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Permite actualizar los datos de un usuario")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="datos correctos, se actualizar los datos del usuario en la base de datos y el sistema",
        content=@Content(schema=@Schema(implementation=UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o mal formados"),
        @ApiResponse(responseCode = "404",description = "usuario no existe por id, error")
    })

    public ResponseEntity<usuario> actualizarUsuario(@PathVariable Long id,@RequestBody usuario datos) {
        usuario actualizado = usuarioService.actualizarUsuario(id, datos);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping ("/{id}")
    @Operation(summary = "Obtiene un usuario a traves de 1 id, solo un usuario en especifico")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se encuentra un usuario correspon diente, se muestran los datos",
        content=@Content(schema=@Schema(implementation=UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o mal formados"),
        @ApiResponse(responseCode = "404",description = "no existe ningun usuario por id, error")
    })
    public ResponseEntity<UsuarioDTO> obtenerusuario(@PathVariable Long id){
        try{
            UsuarioDTO usuario = usuarioService.getUsuario(id);
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody Login login) {
        try {
            return ResponseEntity.ok(usuarioService.login(login));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping
    @Operation(summary="Permite guardar o crear un nuevo usuario para el sistema")
    @ApiResponses( value = {
        @ApiResponse(responseCode="201", description="datos correctos, se agrega el usuario al sistema",
        content=@Content(schema=@Schema(implementation=UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o mal formados"),
    })
    @PostMapping
    public ResponseEntity<usuario> guardarusuario(@RequestBody usuario nuevo){
        return ResponseEntity.status(201).body(usuarioService.saveUsuario(nuevo));
    }
    @DeleteMapping("/{id}")
    @GetMapping
    @Operation(summary="Permite eliminar un usuario para el sistema")
    @ApiResponses( value = {
        @ApiResponse(responseCode="204", description="se encuentra un id correspondiente y se elimina del sistema",
        content=@Content(schema=@Schema(implementation=UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "no se encuentra cohincidencia con el id solicitado"),
    })
    public ResponseEntity<Void> eliminarusuario (@PathVariable Long id ){
        if(usuarioService.deleteUsuario(id)){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
        
    }

    

}
