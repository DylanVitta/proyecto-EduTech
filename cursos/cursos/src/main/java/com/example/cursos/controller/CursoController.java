package com.example.cursos.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursos.model.Curso;
import com.example.cursos.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;


    @PostMapping
    @Operation(summary="Permite guardar o crear un nuevo curso")
    @ApiResponses( value = {
        @ApiResponse(responseCode="201", description="datos correctos, se agrega el registro al sistema",
        content=@Content(schema=@Schema(implementation=Curso.class))),
        @ApiResponse(responseCode="400", description="datos incorrectos, no se guarda nada, revissar bien la solicitud",
        content=@Content(schema=@Schema(implementation=Curso.class)))
    })
    public ResponseEntity<Curso> nuevocurso(@RequestBody Curso nuevo){
        return ResponseEntity.status(201).body(cursoService.saveCurso(nuevo));
    }

    @GetMapping
    @Operation(summary="Permite obtener una lista de todos los cursos registrados sin ningun filtro")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó la lista con todas los cursos",
        content=@Content(schema=@Schema(implementation=Curso.class)))
    })
    public ResponseEntity<List<Curso>> obtenercursos(){
        List<Curso> cursos = cursoService.getCursos();
        if(cursos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(cursos);
    }

    @GetMapping ("/{id}")
    @Operation(summary="Permite obtener un curso en especifico a través de su id de curso")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="se muestra el curso solicitado",
        content=@Content(schema=@Schema(implementation=Curso.class))),
        @ApiResponse(responseCode="404", description="no se encontro el curso",
        content=@Content)
    })  
    public ResponseEntity<Curso> obtenercurso(@Parameter(description = "IDcurso",example = "/1")@PathVariable Long id){
        try{
            Curso curso = cursoService.getCurso(id);
            return ResponseEntity.ok(curso);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @GetMapping("/profesor/{id}")
    @Operation(summary="muestra todos los cursos correspondientes a un profesor, a traves de su id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="muestra todos los cursos que estan a cargo de un profesor ",
        content=@Content(schema=@Schema(implementation=Curso.class))),
        @ApiResponse(responseCode="404", description="no hya ningun curso asociado al profesor",
        content=@Content)
    })  
    public ResponseEntity<List<Curso>> obtenerCursosPorProfesor(@Parameter(description = "IDusuarioprofesor",example = "/profesor/1")@PathVariable Long id) {
        List<Curso> cursos = cursoService.getbyidprofesor(id);
        if (cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cursos);
    }
    @PutMapping("/{id}")
    @Operation(summary="permite modificar un curso seleccionandolo a traves de id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="curso actualizado",
        content=@Content(schema=@Schema(implementation=Curso.class))),
        @ApiResponse(responseCode="404", description="no se encontro un curso que correspondiera con el id, no se actualiza nada",
        content=@Content)
    })
    public ResponseEntity<Curso> actualizarcurso(@Parameter(description = "IDcurso",example = "/1+body")@PathVariable Long id,@ RequestBody Curso curso){
        try{
            return ResponseEntity.ok(cursoService.actualizaCurso(id, curso));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary="Permite eliminar un curso seleccionandolo con el id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="204", description="curso eliminado",
        content=@Content(schema=@Schema(implementation=Curso.class))),
        @ApiResponse(responseCode="404", description="no se encontro un curso que coincidiera",
        content=@Content)
    })
    public ResponseEntity<Void> eliminarcurso (@Parameter(description = "IDcurso",example = "/1")@PathVariable Long id ){
        if(cursoService.deletecurso(id)){
            
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
        
    }
    


}
