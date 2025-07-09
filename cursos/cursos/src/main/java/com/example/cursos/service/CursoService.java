package com.example.cursos.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursos.WebClient.UsuarioClient;
import com.example.cursos.model.Curso;
import com.example.cursos.repository.CursoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired 
    private UsuarioClient usuarioClient;
    public Curso saveCurso(Curso curso){
        Map<String, Object> datosUsuario = usuarioClient.obtenerUsuarioPorId(curso.getIdprofesor());
        Map<String, Object> rol = (Map<String, Object>) datosUsuario.get("rol");
        String nombreRol = (String) rol.get("nombre");
        if (!"PROFESOR".equalsIgnoreCase(nombreRol)) {
            throw new RuntimeException("El usuario no tiene rol de PROFESOR");
        }

    return cursoRepository.save(curso);
    }
    public List<Curso> getbyidprofesor(Long id_profesor){
        return cursoRepository.findByIdprofesor(id_profesor);
    }

    public List<Curso> getCursos (){
        return cursoRepository.findAll();
    }

    public Curso getCurso (Long id){
        return cursoRepository.findById(id).orElseThrow
        (()-> new RuntimeException("Curso no encontrado"));
    }
    public Curso actualizaCurso( Long id , Curso nuevo ){
        Curso curso = cursoRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Curso no encontrado") );

        curso.setNombre_curso(nuevo.getNombre_curso());
        curso.setDescripcion(nuevo.getDescripcion());
        curso.setCategoria(nuevo.getCategoria());
        curso.setDuracion_horas(nuevo.getDuracion_horas());
        return cursoRepository.save(curso);
    }
    public boolean deletecurso(Long id){
        if( cursoRepository.existsById(id)){
        cursoRepository.deleteById(id);
        return true;
       }else{
        return false;
       }
            
    }
        
    
    

}
