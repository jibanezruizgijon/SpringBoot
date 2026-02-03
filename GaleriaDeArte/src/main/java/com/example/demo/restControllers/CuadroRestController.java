package com.example.demo.restControllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.clases.Cuadro;
import com.example.demo.repository.CuadroRepository;

@RestController
@RequestMapping("/api/cuadros")
public class CuadroRestController {

    @Autowired
    private CuadroRepository cuadroRepository;

    // Obtiene todos los cuadros
    @GetMapping
    public List<Cuadro> obtenerTodos() {
        return cuadroRepository.findAll();
    }

    // Recoge un cuadro según el id que se pase
    @GetMapping("/{id}")
    public ResponseEntity<Cuadro> obtenerPorId(@PathVariable Long id) {
        return cuadroRepository.findById(id)
        		// Si existe, devuelve 200 OK y el cuadro
                .map(cuadro -> ResponseEntity.ok(cuadro)) 
             // Si no, devuelve 404 Not Found
                .orElse(ResponseEntity.notFound().build()); 
    }

    // BUSCA un cuadro por autor
    @GetMapping("/buscar")
    public List<Cuadro> buscarPorAutor(@RequestParam String autor) {
        return cuadroRepository.findByAutorContainingIgnoreCase(autor);
    }

    // Crea un cuadro nuevo (Recibe JSON)
    @PostMapping
    public Cuadro crearCuadro(@RequestBody Cuadro nuevoCuadro) {
        //  Aquí la imagen debe venir como URL en texto, no como archivo
        return cuadroRepository.save(nuevoCuadro);
    }

    //  ACTUALIZAR un cuadro existente
    @PutMapping("/{id}")
    public ResponseEntity<Cuadro> actualizarCuadro(@PathVariable Long id, @RequestBody Cuadro cuadroDatos) {
        return cuadroRepository.findById(id)
                .map(cuadroExistente -> {
                    cuadroExistente.setNombre(cuadroDatos.getNombre());
                    cuadroExistente.setAutor(cuadroDatos.getAutor());
                    cuadroExistente.setEpocaPintura(cuadroDatos.getEpocaPintura());
                    cuadroExistente.setUrlImg(cuadroDatos.getUrlImg());
                    return ResponseEntity.ok(cuadroRepository.save(cuadroExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //  BORRA un cuadro
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> borrarCuadro(@PathVariable Long id) {
        return cuadroRepository.findById(id)
                .map(c -> {
                    cuadroRepository.delete(c);
                 // 204 No Content (Borrado exitoso)
                    return ResponseEntity.noContent().build(); 
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
