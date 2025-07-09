package com.example.inscripcion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inscripcion.model.Inscripcion;
import com.example.inscripcion.service.InscripcionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/inscripcion")
public class InscripcionController {
    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping
    @Operation(summary="Permite guardar o crear una nueva inscripcion")
    @ApiResponses( value = {
        @ApiResponse(responseCode="201", description="datos correctos, se agrega la inscripccion al sistema",
        content=@Content(schema=@Schema(implementation=Inscripcion.class)))
    })
    public ResponseEntity<Inscripcion> inscribir(@RequestParam Long idusuario,@RequestParam Long idcurso){
        return ResponseEntity.ok(inscripcionService.saveInscripcion(idusuario, idcurso)); 
    }

    @GetMapping
    @Operation(summary="Permite obtener una lista de todas las inscripciones del sistema sin ningun filtro")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó la lista con todas las inscripciones registradas",
        content=@Content(schema=@Schema(implementation=Inscripcion.class))),
         @ApiResponse(responseCode="404", description="no se encontraron inscripciones",content=@Content)
    })
    public ResponseEntity<List<Inscripcion>>  listarinscripciones (){
        List<Inscripcion> inscripcion = inscripcionService.getinscripciones();
        if(inscripcion.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(inscripcion);
    }        
    
    @Operation(summary="muestra una inscripcion seleccionada a traves de id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se muestra la inscripcion",
        content=@Content(schema=@Schema(implementation=Inscripcion.class))),
        @ApiResponse(responseCode="404", description="no se encontro una inscripcion que corresponda con el id indicado",
        content=@Content)
    })
    @GetMapping ("/{id}")
    public ResponseEntity<Inscripcion> obtenerusuario(@Parameter(description = "ID_Inscripcion",example = "1")@PathVariable Long id){
        try{
            Inscripcion inscripcion = inscripcionService.getisncripcion(id);
            return ResponseEntity.ok(inscripcion);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }    

    @GetMapping("/{idcurso}/cursos")
    @Operation(summary="permite buscar las inscripciones que estan ligadas a un curso a traves del id del curso")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se listan todas las evalauciones correspondientes",
        content=@Content(schema=@Schema(implementation=Inscripcion.class))),
    })
    public List<Inscripcion> obtenercursos(@Parameter(description = "IDcurso",example = "1")@PathVariable Long idcurso){
        return inscripcionService.getcurso(idcurso);
    }

    @PatchMapping("/{id}/estado") //--?activo=estado---/
    @Operation(summary="Permite modificar el campo activo dentro de la inscripcion, para mostrar si es que es una inscripcion actual o una ya vencida")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se cambio el estado de campo 'activo'",
        content=@Content(schema=@Schema(implementation=Inscripcion.class)))
    })
    public ResponseEntity<Inscripcion> actualizarestado(@Parameter(description = "ID_inscripcion",example = "1")@PathVariable Long id, @Parameter(description = "indicador activo",example = "estado?activo=true")@RequestParam boolean activo){
        return ResponseEntity.ok(inscripcionService.putEstado(id, activo));
    }




}