package GenAi.exception;

import GenAi.dto.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {

        return ResponseEntity.status(500)
                .body(new ErrorResponse(ex.getMessage()));
    }
}