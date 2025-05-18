package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("titulo", "Recurso no encontrado");
        model.addAttribute("mensaje", ex.getMessage());
        return "error/404"; // crea templates/error/404.html
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(BadRequestException ex, Model model) {
        model.addAttribute("titulo", "Solicitud inválida");
        model.addAttribute("mensaje", ex.getMessage());
        return "error/400"; // crea templates/error/400.html
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("titulo", "Error interno");
        model.addAttribute("mensaje", "Ocurrió un error inesperado.");
        model.addAttribute("detalle", ex.getMessage());
        return "error/500"; // crea templates/error/500.html
    }
}