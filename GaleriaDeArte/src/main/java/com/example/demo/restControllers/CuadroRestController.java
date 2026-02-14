package com.example.demo.restControllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.clases.Cuadro;
import com.example.demo.repository.CuadroRepository;
import io.swagger.v3.oas.annotations.Operation; 
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST que expone la API para la gestión de Cuadros.
 * <p>
 * Esta clase habilita los endpoints CRUD accesibles vía HTTP/JSON.
 * Es utilizada principalmente para la integración con clientes externos o documentación mediante Swagger/OpenAPI.
 * <p>Base URL: {@code /api/cuadros}
 *
 * @author Jonathan Ibáñez Piñero
 */
@RestController
@RequestMapping("/api/cuadros")
@Tag(name = "API de Cuadros", description = "Endpoints para gestionar la galería de arte")
public class CuadroRestController {

	/** Repositorio para acceder a la base de datos de cuadros. */
    @Autowired
    private CuadroRepository cuadroRepository;

    /**
     * Obtiene el listado completo de cuadros registrados en el sistema.
     * <p>Endpoint: {@code GET /api/cuadros}
     *
     * @return Una lista ({@link List}) de objetos {@link Cuadro} en formato JSON.
     */
    @Operation(summary = "Obtener todos los cuadros", description = "Devuelve una lista completa de las obras disponibles en la galería.")
    @GetMapping
    public List<Cuadro> obtenerTodos() {
        return cuadroRepository.findAll();
    }

    /**
     * Busca y recupera un cuadro específico por su identificador único.
     * <p>Endpoint: {@code GET /api/cuadros/{id}}
     *
     * @param id El identificador (Primary Key) del cuadro a buscar.
     * @return Un {@link ResponseEntity} que contiene:
     * <ul>
     * <li><b>200 OK</b> y el objeto {@code Cuadro} si se encuentra.</li>
     * <li><b>404 Not Found</b> si no existe ningún cuadro con ese ID.</li>
     * </ul>
     */
    @Operation(summary = "Obtener un cuadro por ID", description = "Busca un cuadro específico. Si no existe, devuelve 404.")
    @GetMapping("/{id}")
    public ResponseEntity<Cuadro> obtenerPorId(@PathVariable Long id) {
        return cuadroRepository.findById(id)
                // Si existe, devuelve 200 OK y el cuadro
                .map(cuadro -> ResponseEntity.ok(cuadro)) 
                // Si no, devuelve 404 Not Found
                .orElse(ResponseEntity.notFound().build()); 
    }

    /**
     * Realiza una búsqueda de cuadros filtrando por el nombre del autor.
     * <p>Endpoint: {@code GET /api/cuadros/buscar?autor=...}
     * <p>La búsqueda es insensible a mayúsculas/minúsculas (case-insensitive) y parcial.
     *
     * @param autor El nombre (o fragmento del nombre) del autor a buscar.
     * @return Una lista de cuadros que coinciden con el criterio de búsqueda.
     */
    @Operation(summary = "Buscar cuadros por autor", description = "Filtra la lista por nombre del autor. La búsqueda es parcial y no distingue mayúsculas.")
    @GetMapping("/buscar")
    public List<Cuadro> buscarPorAutor(@RequestParam String autor) {
        return cuadroRepository.findByAutorContainingIgnoreCase(autor);
    }

    /**
     * Crea un nuevo registro de cuadro en la base de datos.
     * <p>Endpoint: {@code POST /api/cuadros}
     * <p><b>Nota:</b> Este endpoint espera un JSON puro. La imagen no se sube aquí como archivo,
     * sino que se debe proporcionar la URL de la imagen (String) previamente subida a Cloudinary.
     *
     * @param nuevoCuadro El objeto {@link Cuadro} deserializado desde el cuerpo (body) de la petición JSON.
     * @return El objeto {@code Cuadro} persistido, incluyendo su ID generado.
     */
    @Operation(summary = "Crear un nuevo cuadro", description = "Guarda un cuadro en la BBDD. Requiere pasar la URL de la imagen en el JSON.")
    @PostMapping
    public Cuadro crearCuadro(@RequestBody Cuadro nuevoCuadro) {
        return cuadroRepository.save(nuevoCuadro);
    }

    /**
     * Actualiza los datos de un cuadro existente.
     * <p>Endpoint: {@code PUT /api/cuadros/{id}}
     *
     * @param id          El identificador del cuadro a modificar.
     * @param cuadroDatos El objeto con los nuevos datos a aplicar (recibido en el body).
     * @return Un {@link ResponseEntity} con:
     * <ul>
     * <li><b>200 OK</b> y el cuadro actualizado si la operación fue exitosa.</li>
     * <li><b>404 Not Found</b> si el ID proporcionado no existe.</li>
     * </ul>
     */
    @Operation(summary = "Modificar un cuadro", description = "Actualiza los datos de un cuadro existente dado su ID.")
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

    /**
     * Elimina un cuadro del sistema.
     * <p>Endpoint: {@code DELETE /api/cuadros/{id}}
     *
     * @param id El identificador del cuadro a eliminar.
     * @return Un {@link ResponseEntity} con:
     * <ul>
     * <li><b>204 No Content</b> si el borrado fue exitoso.</li>
     * <li><b>404 Not Found</b> si el cuadro no existía previamente.</li>
     * </ul>
     */
    @Operation(summary = "Eliminar un cuadro", description = "Borra permanentemente una obra de la base de datos.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> borrarCuadro(@PathVariable Long id) {
        return cuadroRepository.findById(id)
                .map(c -> {
                    cuadroRepository.delete(c);
                    // 204 No Content (Borrado exitoso, sin cuerpo de respuesta)
                    return ResponseEntity.noContent().build(); 
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
