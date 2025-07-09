package com.example.soporte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.soporte.model.Soporte;
import com.example.soporte.service.SoporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/soporte")
public class SoporteController {
    @Autowired
    private SoporteService soporteService;

    @PostMapping
    @Operation(summary="Permite guardar o crear un nuevo ticket de soporte ")
    @ApiResponses( value = {
        @ApiResponse(responseCode="201", description="datos correctos, se agrega una nueva consulta al sistema",
        content=@Content(schema=@Schema(implementation=Soporte.class)))
    })
    public ResponseEntity<Soporte> crear (@RequestBody Soporte soporte){
        return ResponseEntity.ok(soporteService.saveticket(soporte));
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary="permite listar los distintos tickets que haya hecho un usuario, seleccionandolo a traves de id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se listan todos los tickets correspondientes",
        content=@Content(schema=@Schema(implementation=Soporte.class))),
        @ApiResponse(responseCode="404", description="el usuario seleccionado no realizo ninguna consulta",
        content=@Content)
    })
    public ResponseEntity<List<Soporte>> obtenerPorUsuario(@PathVariable Long id) {
        List<Soporte> tickets = soporteService.getbyidusuario(id);
        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }


    @GetMapping
    @Operation(summary="Permite obtener una lista de todos los ticket de soporte del sistema sin ningun filtro")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó la lista con todos los tickets",
        content=@Content(schema=@Schema(implementation=Soporte.class))),
    })
    public List<Soporte> listar(){
        return soporteService.getsoporte();
    }

    @GetMapping ("/{id}")
    @Operation(summary="permite buscar los tickets a traves de su id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se muestra el ticket que coincide con el id",
        content=@Content(schema=@Schema(implementation=Soporte.class))),
        @ApiResponse(responseCode="404", description="no se encontro un ticket que coincida con el id",content=@Content)
    })
    public ResponseEntity<Soporte> obtenerticketid(@PathVariable Long id){
        try{
            Soporte ticket = soporteService.getTicket(id);
            return ResponseEntity.ok(ticket);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @PatchMapping("/{id}/estado")
    @Operation(summary="Permite modificar el estado de la solicitud, para indformar si se encuentra en espera o resuelta")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se cambio el estado de la solicitud",
        content=@Content(schema=@Schema(implementation=Soporte.class)))
    })
    public ResponseEntity<Soporte> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(soporteService.actualizarEstado(id, estado));
    }

}
