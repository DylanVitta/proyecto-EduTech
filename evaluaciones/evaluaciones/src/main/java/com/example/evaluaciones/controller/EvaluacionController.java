package com.example.evaluaciones.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.example.evaluaciones.model.Evaluacion;
import com.example.evaluaciones.service.EvaluacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {
    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping
    @Operation(summary="Permite guardar o crear una nueva evaluacion")
    @ApiResponses( value = {
        @ApiResponse(responseCode="201", description="datos correctos, se agrega la evaluacion al sistema",
        content=@Content(schema=@Schema(implementation=Evaluacion.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o mal formados"),
        @ApiResponse(responseCode = "404", description = "Curso o usuario no encontrado")
    })
    public ResponseEntity<Evaluacion> registrar(@RequestBody Evaluacion evaluacion) {
        return ResponseEntity.ok(evaluacionService.saveEvaluacion(evaluacion));
    }  

    @GetMapping
    @Operation(summary="Permite obtener una lista de todas las evaluaciones del sistema sin ningun filtro")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó la lista con todas las evaluaciones registradas",
        content=@Content(schema=@Schema(implementation=Evaluacion.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o mal formados")
    })
    public List<Evaluacion> listar() {
        return evaluacionService.getEvaluaciones();
    }

    @PatchMapping("/{id}/modificar")
    @Operation(summary="permite modificar una evaluacion(nota o agregar fecha limite), seleccionandolo a traves de id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="evaluacion actualizado",
        content=@Content(schema=@Schema(implementation=Evaluacion.class))),
        @ApiResponse(responseCode="404", description="no se encontro un evaluacion que correspondiera con el id, no se actualiza nada",
        content=@Content)
    })
    public ResponseEntity<Evaluacion> actualizarEvaluacion(@Parameter(description = "datos",example = "/api/v1/evaluaciones/1/modificar?nota=6.0&fechaLimite=2025-08-23")@PathVariable Long id,@RequestParam(required = false) Double nota,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLimite) {   
        Evaluacion actualizada = evaluacionService.patchEvaluacion(id, nota, fechaLimite);
        return ResponseEntity.ok(actualizada);}

    @GetMapping("/alumno/{id}")
    @Operation(summary="permite listar las evaluaciones correspondientes a un alumno, seleccionandolo a traves de id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se listan todas las evalauciones correspondientes",
        content=@Content(schema=@Schema(implementation=Evaluacion.class))),
        @ApiResponse(responseCode="404", description="no hay evaluaciones correspondientes a ese alumno",
        content=@Content)
    })
    public ResponseEntity<List<Evaluacion>> listarPorAlumno(@Parameter(description = "IDalumno",example = "/alumno/1")@PathVariable Long id) {
    List<Evaluacion> evaluaciones = evaluacionService.getbyidalumno(id);
    if (evaluaciones.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(evaluaciones);}

    @GetMapping("/curso/{id}")
    @Operation(summary="permite listar las evaluaciones correspondientes a un curso, seleccionandolo a traves de id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se listan todas las evalauciones correspondientes",
        content=@Content(schema=@Schema(implementation=Evaluacion.class))),
        @ApiResponse(responseCode="404", description="no hay evaluaciones correspondientes a ese curso",
        content=@Content)
    })
    
    public ResponseEntity<List<Evaluacion>> obtenerPorCurso(@Parameter(description = "IDcurso",example = "/curso/1")@PathVariable Long id) {
    List<Evaluacion> evaluaciones = evaluacionService.getbyidcurso(id);
    if (evaluaciones.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(evaluaciones);}

    @GetMapping ("/{id}")
    @Operation(summary="Permite obtener una evaluacion en especifico a través de su id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se muestra la evaluacion solicitado",
        content=@Content(schema=@Schema(implementation=Evaluacion.class))),
        @ApiResponse(responseCode="404", description="no se encontro la evaluacion",
        content=@Content)
    })
    public ResponseEntity<Evaluacion> obtenerevaluacionid(@Parameter(description = "ID_Evaluacion",example = "/api/v1/evaluaciones/1")@PathVariable Long id){
        try{
            Evaluacion evaluacion = evaluacionService.getEvaluacion(id);
            return ResponseEntity.ok(evaluacion);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary="Permite eliminar una evaluacion seleccionandola con el id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="204", description="evaluacion eliminada",
        content=@Content(schema=@Schema(implementation=Evaluacion.class))),
        @ApiResponse(responseCode="404", description="no se encontro una evaluacion que coincidiera",
        content=@Content)
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID_evaluacion",example = "/api/v1/evaluaciones/1")@PathVariable Long id) {
        if(evaluacionService.eliminar(id)){
            
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
