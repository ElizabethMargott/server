package com.example.demo.exceptions;
// Importar las clases necesarias
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Anotar la clase con el estado HTTP que debe devolver cuando se lanza la excepción
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException {

    // Constructor que acepta un mensaje de error
    public FileStorageException(String message) {
        super(message);
    }

    // Constructor que acepta un mensaje de error y una causa subyacente
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
