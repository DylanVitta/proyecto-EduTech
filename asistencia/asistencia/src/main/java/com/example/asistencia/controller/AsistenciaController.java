package com.example.asistencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.asistencia.model.Asistencia;
import com.example.asistencia.service.AsistenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/asistencia")
public class AsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;
    @Operation(summary="Permite guardar o crear un registro de asistencia nuevo")
    @ApiResponses( value = {
        @ApiResponse(responseCode="201", description="datos correctos, se agrega el registro al sistema",
        content=@Content(schema=@Schema(implementation=Asistencia.class)))
    })
    @PostMapping
    public ResponseEntity<Asistencia> registrar(@RequestBody Asistencia asistencia) {
        return ResponseEntity.ok(asistenciaService.saveAsistencia(asistencia));
    }
    @Operation(summary="Permite obtener una lista con todas las asistencias, sin ningun filtro")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó la lista con todas las asisetncias registradas",
        content=@Content(schema=@Schema(implementation=Asistencia.class)))
    })
    @GetMapping
    public List<Asistencia> listar() {
        return asistenciaService.getasistencias();
    }
    @Operation(summary="Permite obtener una asistencia en especifico a través de su id de asistencia")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó el recurso de asistencia solicitado",
        content=@Content(schema=@Schema(implementation=Asistencia.class))),
        @ApiResponse(responseCode="404", description="no se encontro la asistencia esperada",
        content=@Content)
    })    
    @GetMapping ("/{id}")
    public ResponseEntity<Asistencia> obtenerasistenciaId(@PathVariable Long id){
        try{
            Asistencia asistencia = asistenciaService.getAsistencia(id);
            return ResponseEntity.ok(asistencia);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @PatchMapping("/{id}/presente")
    @Operation(summary="Permite modificar la presencia o ausencia de la asistencia")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se cambio el estado del campo 'presente'",
        content=@Content(schema=@Schema(implementation=Asistencia.class)))
    })
    public ResponseEntity<Asistencia> actualizarPresente(@Parameter(description = "estado presente",example = "/api/v1/asistencia/1/presente?presente=true")@PathVariable Long id,@RequestParam boolean presente) {
        Asistencia asistencia = asistenciaService.patchPresente(id, presente);
        return ResponseEntity.ok(asistencia);
    }
    @Operation(summary="Permite eliminar un registro de asistencia, seleccionandolo con el id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="204", description="registro de asistencia eliminado",
        content=@Content(schema=@Schema(implementation=Asistencia.class))),
        @ApiResponse(responseCode="404", description="no se encontro la asistencia esperada",
        content=@Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarasistencia (@Parameter(description = "elimina una asistencia",example = "/2")@PathVariable Long id ){
        if(asistenciaService.deleteAsistencia(id)){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
