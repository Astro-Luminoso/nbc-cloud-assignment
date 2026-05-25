package dev.nbcsparta.assignment.nbccloudassignment.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMemberNotFound(MemberNotFoundException exception) {
        // Log not-found events at WARN level so they are visible in logs
        log.warn("[API - WARN] {}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    // Global handler to ensure unexpected exceptions are logged with stacktraces
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException(Exception exception) {
        log.error("[API - ERROR] Unhandled exception caught by ControllerAdvice", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("An unexpected error occurred. Internal server error."));
    }

    public record ErrorResponse(String message) {
    }
}
