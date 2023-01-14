package Backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //Maneja todas las excepciones de los controller que estén mapeadas con el ExceptionHandler, pero se prioriza el try-catch si existiera.
public class GlobalControllerAdvice {

    @ExceptionHandler(RequestBodyIsNullException.class)
    public ResponseEntity<Exception> handleRequestBodyIsNullException() {
        RequestBodyIsNullException error = new RequestBodyIsNullException("Request body is missing");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

