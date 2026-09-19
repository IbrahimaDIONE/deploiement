package com.acl.ecom.exception;

import jakarta.el.MethodNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Erreur de validation");
        problem.setDetail("Les données envoyées sont invalides");
        problem.setType(URI.create("/errors/validation"));

        return problem;
    }

    @ExceptionHandler(MethodNotFoundException.class)
    public ProblemDetail handleValidation(MethodNotFoundException ex){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Ressources non trouvées");
        problem.setDetail("Impossible d'acceder a cette ressource");
        problem.setType(URI.create("/errors/validation"));

        return problem;
    }
}
