package com.example.demo.servicios;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Servicio encargado de la gestión de imágenes en la nube mediante Cloudinary.
 * Permite subir archivos y obtener su URL segura.
 */
@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Sube un archivo de imagen a Cloudinary y devuelve la URL de acceso pública.
     *
     * @param file El archivo multipart (imagen) recibido desde el formulario.
     * @return La URL segura (https) de la imagen subida.
     * @throws IOException Si ocurre un error de entrada/salida durante la lectura o subida del archivo.
     */
    public String subirImagen(MultipartFile file) throws IOException {
        
        // Prevención de errores: Verificar si el archivo viene vacío
        if (file == null || file.isEmpty()) {
            throw new IOException("El archivo proporcionado está vacío o es nulo.");
        }

        // Subir el archivo a Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        
        // Obtener la URL segura (https)
        return uploadResult.get("secure_url").toString();
    }
}