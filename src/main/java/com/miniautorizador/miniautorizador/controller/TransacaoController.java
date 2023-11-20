package com.miniautorizador.miniautorizador.controller;

import com.miniautorizador.miniautorizador.request.CriarCartaoRequest;
import com.miniautorizador.miniautorizador.request.TransacaoRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.miniautorizador.miniautorizador.service.TransacaoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transacoes")
@Slf4j
public class TransacaoController {


    @Autowired
    private TransacaoService transacaoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>  criarTransacao(
            @Valid @RequestBody final TransacaoRequest transacaoRequest) {
        return transacaoService.validarTransacao(transacaoRequest);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
