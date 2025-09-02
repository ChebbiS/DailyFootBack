package com.dailyfoot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ProblemDetail handlePlayerAlreadyExists(PlayerAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Le joueur existe déjà !");
        return problemDetail;
    }
    @ExceptionHandler(PlayerNotFoundException.class)
    public ProblemDetail handlePlayerNotFound(PlayerNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Le joueur n'existe pas !");
        return problemDetail;
    }
    @ExceptionHandler(CannotDeleteStrangerPlayerException.class)
    public ProblemDetail handleCannotDeleteStrangerPlayer(CannotDeleteStrangerPlayerException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("Vous ne pouvez pas supprimer un joueur qui ne vous appartient pas !");
        return problemDetail;
    }
}
