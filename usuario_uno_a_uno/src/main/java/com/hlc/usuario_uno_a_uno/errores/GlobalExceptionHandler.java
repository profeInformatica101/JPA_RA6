package com.hlc.usuario_uno_a_uno.errores;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import com.hlc.usuario_uno_a_uno.errores.excepcion.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    // Manejo de 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(BadRequestException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/400";
    }
}
