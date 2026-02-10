package com.example.demo.servicios;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Servicio de infraestructura encargado de la gestión de activos multimedia en la nube.
 * <p>
 * Esta clase actúa como un wrapper (envoltorio) sobre la librería cliente de Cloudinary,
 * permitiendo desacoplar la lógica de almacenamiento de la aplicación principal.
 * Se encarga de transformar los archivos recibidos en el controlador y enviarlos a los servidores de Cloudinary.
 *
 * @author Jonathan Ibáñez piñero
 * @see com.example.demo.config.CloudinaryConfig
 */
@Service
public class CloudinaryService {

    /** * Cliente de la API de Cloudinary configurado previamente en la clase de configuración.
     * Se inyecta automáticamente por Spring.
     */
    @Autowired
    private Cloudinary cloudinary;

    /**
     * Procesa y sube un archivo de imagen a la plataforma Cloudinary.
     * <p>
     * Este método realiza una validación previa para asegurar que el archivo no sea nulo o esté vacío.
     * Posteriormente, utiliza la API de Cloudinary para cargar el archivo y obtener sus metadatos.
     *
     * @param file El archivo {@link MultipartFile} recibido desde el formulario web.
     * Debe ser una imagen válida.
     * @return Una {@code String} que contiene la <b>URL segura (https)</b> de la imagen alojada.
     * Esta URL es la que se debe persistir en la base de datos.
     * @throws IOException Se lanza en dos situaciones principales:
     * <ul>
     * <li>Si el archivo proporcionado es {@code null} o está vacío (validación lógica).</li>
     * <li>Si ocurre un error de comunicación con la API de Cloudinary durante la subida (error de red o credenciales).</li>
     * </ul>
     */
    public String subirImagen(MultipartFile file) throws IOException {
        
        // Prevención de errores: Verificar si el archivo viene vacío o corrupto
        if (file == null || file.isEmpty()) {
            throw new IOException("Error de validación: El archivo proporcionado está vacío o es nulo.");
        }

        // Subir el archivo a Cloudinary usando parámetros por defecto (ObjectUtils.emptyMap())
        // El método .upload() devuelve un mapa con metadatos de la imagen (ID, URL, tamaño, etc.)
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        
        // Extraemos y retornamos únicamente la URL segura (HTTPS) para su almacenamiento
        return uploadResult.get("secure_url").toString();
    }
}