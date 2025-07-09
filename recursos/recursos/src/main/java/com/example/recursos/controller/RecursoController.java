package com.example.recursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recursos.model.Recurso;
import com.example.recursos.service.RecursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/recursos")
public class RecursoController {
    @Autowired
    private RecursoService recursoService;

    @GetMapping
    @Operation(summary="Permite obtener una lista de todos los recursos de los cursos del sistema sin ningun filtro")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó la lista con todas los recursos subidos",
        content=@Content(schema=@Schema(implementation=Recurso.class))),
         @ApiResponse(responseCode="404", description="no se encontraron recursos",content=@Content)
    })
    public ResponseEntity<List<Recurso>> obtenerRecursos(){
        List<Recurso> recursos= recursoService.getRecursos();
        if(recursos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recursos);
    }

    @GetMapping("/curso/{id}")
    @Operation(summary="permite buscar llos recursos que estan ligados a un curso a traves del id del curso")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se listan todos los recursos correspondientes",
        content=@Content(schema=@Schema(implementation=Recurso.class))),
    })
    public ResponseEntity<List<Recurso>> obtenerPorCurso(@PathVariable Long id) {
        List<Recurso> recursos = recursoService.obtenerPorCurso(id);
        if (recursos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(recursos);
    }

    @PutMapping("/{id}")
    @Operation(summary="permite modificar un recurso seleccionandolo a traves de su  id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="recurso actualizado",
        content=@Content(schema=@Schema(implementation=(Recurso.class))))
    })
    public ResponseEntity<Recurso> modificarRecurso(@PathVariable Long id, @RequestBody Recurso nuevo) {
        return ResponseEntity.ok(recursoService.postRecurso(id, nuevo));
    }
    @GetMapping ("/{id}")
    @Operation(summary="muestra un recurso seleccionado a traves de id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se muestra el recurso solicitado",
        content=@Content(schema=@Schema(implementation=Recurso.class))),
        @ApiResponse(responseCode="404", description="no se encontro un recurso que corresponda con el id indicado",
        content=@Content)
    })
    public ResponseEntity<Recurso> obtenerrecurso(@PathVariable Long id){
        try{
            Recurso recurso= recursoService.getRecurso(id);
            return ResponseEntity.ok(recurso);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    } 

    @PostMapping
    @Operation(summary="Permite guardar o crear un nuevo recurso ")
    @ApiResponses( value = {
        @ApiResponse(responseCode="201", description="datos correctos, se agrega un nuevo recurso al sistema",
        content=@Content(schema=@Schema(implementation=Recurso.class)))
    })
    public ResponseEntity<Recurso> nuevoRecurso(@RequestBody Recurso recurso){
        return ResponseEntity.status(201).body(recursoService.postRecurso(recurso));
    }
    
    @Operation(summary="Permite eliminar un recurso de un curso seleccionando el recurso con el id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="204", description="se elimina recurso",
        content=@Content(schema=@Schema(implementation=Recurso.class))),
        @ApiResponse(responseCode = "404",description = "no se encontro un curso correspondiente al id")
    })
    
    @DeleteMapping("/{idrecurso}")
    public ResponseEntity<Void> eliminarRecurso(@PathVariable Long idrecurso) {
        boolean eliminado = recursoService.deleterecurso(idrecurso);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
